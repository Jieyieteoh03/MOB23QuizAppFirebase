package com.jy.mob23quizappfirebase.ui.participantQuiz

import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jy.mob23quizappfirebase.R
import com.jy.mob23quizappfirebase.data.model.Quiz
import com.jy.mob23quizappfirebase.databinding.FragmentParticipantQuizBinding
import com.jy.mob23quizappfirebase.ui.adapter.QuestionAdapter
import com.jy.mob23quizappfirebase.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ParticipantQuizFragment : BaseFragment<FragmentParticipantQuizBinding>() {
    private lateinit var quesAdapter: QuestionAdapter
    override val viewModel: ParticipantQuizViewModel by viewModels()
    private var timer: CountDownTimer? = null
    private var timeInMillis: Long? = null
    private var totalSeconds: Int? = null
    private val answers = mutableListOf<String>()
    override fun getLayoutResource(): Int = R.layout.fragment_participant_quiz

    override fun onBindView(view: View) {
        super.onBindView(view)
        setupQuesAdapter()
        viewModel.getUserRole()
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        lifecycleScope.launch {
            viewModel.quiz.collect{
                quesAdapter.setQuestions(it.questions)
                setupQuiz(it)
                startTimer()
            }
        }
        lifecycleScope.launch {
            viewModel.role1.collect {
                quesAdapter.setRole(it)
            }
        }
    }
    private fun startTimer() {
        timer = object: CountDownTimer(timeInMillis!!, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeInMillis = millisUntilFinished
                updateTimer()
            }
            override fun onFinish() {
                endQuiz()
            }
        }
        timer?.start()
    }
    private fun updateTimer() {
        timeInMillis?.let {
            val seconds = (it / 1000L).toInt()
            binding?.tvViewTimer?.text = requireContext().getString(
                R.string.timer_display, seconds.toString()
            )
        }
    }
    private fun endQuiz() {
        timer?.cancel()
        viewModel.getCurrentQuiz()?.let {
            val score = viewModel.getScore(quesAdapter.getAnswerList())
            val timeTaken = viewModel.getSeconds(it.timer) - (timeInMillis!! / 1000L).toInt()
            viewModel.updateQuiz(score, timeTaken)
            lifecycleScope.launch {
                delay(500)
                findNavController().navigate(
                    ParticipantQuizFragmentDirections.actionParticipantQuizToParticipantScore(scoreId = score, timeTakenId = timeTaken)
                )
            }
        }
    }


    private fun setupQuiz(quiz: Quiz) {
        binding?.run {
            tvViewTitle.text = quiz.title
            tvViewDesc.text = quiz.desc
            totalSeconds = viewModel.getSeconds(quiz.timer)
            timeInMillis = totalSeconds!! * 1000L
            binding?.tvViewTimer?.text = totalSeconds.toString()
            quiz.questions.forEach { answers.add(it.answers) }
            btnEndQuiz.setOnClickListener { endQuiz() }
        }
    }
    private fun setupQuesAdapter() {
        quesAdapter = QuestionAdapter(emptyList())
        binding?.rvQuesList?.adapter = quesAdapter
        binding?.rvQuesList?.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        timer?.cancel()
        super.onDestroyView()
    }
}