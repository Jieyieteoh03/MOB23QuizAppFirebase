package com.jy.mob23quizappfirebase.ui.viewQuiz

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jy.mob23quizappfirebase.R
import com.jy.mob23quizappfirebase.data.model.Quiz
import com.jy.mob23quizappfirebase.databinding.FragmentViewQuizBinding
import com.jy.mob23quizappfirebase.ui.adapter.QuestionAdapter
import com.jy.mob23quizappfirebase.ui.adapter.StudentAdapter
import com.jy.mob23quizappfirebase.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ViewQuizFragment() : BaseFragment<FragmentViewQuizBinding>() {
    private lateinit var quesAdapter: QuestionAdapter
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var clipboardManager: ClipboardManager
    override val viewModel: ViewQuizViewModel by viewModels()
    private var currentView: String = "Questions"

    override fun getLayoutResource(): Int = R.layout.fragment_view_quiz

    override fun onBindView(view: View) {
        super.onBindView(view)
        setupAdapters()
        clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

         binding?.btnSwitch?.setOnClickListener {
            currentView = if(currentView == "Questions") "Students" else "Questions"
            switchView()
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        lifecycleScope.launch {
            viewModel.quiz.collect {
                quesAdapter.setQuestions(it.questions)
                studentAdapter.setStudents(it.studentList)
                setupQuiz(it)
            }
        }
    }

    private fun copyQuizId(id: String) {
        val clipData = ClipData.newPlainText("CLIP_ID", id)
        clipboardManager.setPrimaryClip(clipData)
    }
    private fun setupQuiz(quiz: Quiz) {
        binding?.run {
            tvViewTitle.text = quiz.title
            tvViewDesc.text = quiz.desc
            tvViewTimer.text = quiz.timer
            quesAdapter.setQuestions(quiz.questions)
            btnCopyID.setOnClickListener {
                copyQuizId(quiz.id!!)
            }
        }
    }
    private fun setupAdapters() {
        quesAdapter = QuestionAdapter(emptyList())
        studentAdapter = StudentAdapter(emptyList())
        binding?.run {
            rvQuesList.adapter = quesAdapter
            rvQuesList.layoutManager = LinearLayoutManager(requireContext())
            rvStudent.adapter = studentAdapter
            rvStudent.layoutManager = LinearLayoutManager(requireContext())
        }
    }
    private fun switchView() {
        binding?.run {
            when(currentView) {
                "Students" -> {
                    rvQuesList.visibility = View.GONE
                    rvStudent.visibility = View.VISIBLE
                }
                else -> {
                    rvQuesList.visibility = View.VISIBLE
                    rvStudent.visibility = View.GONE
                }
            }
        }
    }
}