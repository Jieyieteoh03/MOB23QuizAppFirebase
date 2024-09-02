package com.jy.mob23quizappfirebase.ui.dashboard.teacher

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jy.mob23quizappfirebase.R
import com.jy.mob23quizappfirebase.core.services.AuthService
import com.jy.mob23quizappfirebase.data.model.Quiz
import com.jy.mob23quizappfirebase.data.repo.QuizRepo
import com.jy.mob23quizappfirebase.databinding.FragmentTeacherDashboardBinding
import com.jy.mob23quizappfirebase.ui.adapter.QuizAdapter
import com.jy.mob23quizappfirebase.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TeacherDashboardFragment : BaseFragment<FragmentTeacherDashboardBinding>() {
    private lateinit var adapter: QuizAdapter
    override val viewModel: TeacherDashboardViewModel by viewModels()

    @Inject
    lateinit var authService: AuthService
    @Inject
    lateinit var quizRepo: QuizRepo

    override fun getLayoutResource(): Int = R.layout.fragment_teacher_dashboard

    override fun onBindView(view: View) {
        super.onBindView(view)
        setupAdapter()

        binding?.run {
            btnLogout.setOnClickListener {
                authService.logOut()
                findNavController().navigate(
                    TeacherDashboardFragmentDirections.actionTeacherDashboardToLogin()
                )
            }

            fabAdd.setOnClickListener {
                findNavController().navigate(
                    TeacherDashboardFragmentDirections.actionTeacherDashboardToAddQuiz()
                )
            }
        }

    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        lifecycleScope.launch {
            viewModel.getAllTeacherQuiz().collect {
                adapter.setQuiz(it)
            }
        }
    }

    private fun setupAdapter() {
        adapter = QuizAdapter(emptyList())
        adapter.listener = object : QuizAdapter.Listener {
            override fun onClickItem(quiz: Quiz) {
                findNavController().navigate(
                    TeacherDashboardFragmentDirections.actionTeacherDashboardToViewQuiz(quiz.id!!)
                )
            }

            override fun onDeleteItem(quiz: Quiz) {
                lifecycleScope.launch {
                    viewModel.deleteQuiz(quiz.id!!)
                }
            }

        }

        binding?.rvQuizList?.adapter = adapter
        binding?.rvQuizList?.layoutManager = LinearLayoutManager(requireContext())
    }


}