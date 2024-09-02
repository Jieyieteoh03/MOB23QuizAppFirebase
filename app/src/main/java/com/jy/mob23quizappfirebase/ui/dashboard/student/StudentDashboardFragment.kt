package com.jy.mob23quizappfirebase.ui.dashboard.student

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jy.mob23quizappfirebase.R
import com.jy.mob23quizappfirebase.core.services.AuthService
import com.jy.mob23quizappfirebase.databinding.FragmentStudentDashboardBinding
import com.jy.mob23quizappfirebase.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StudentDashboardFragment : BaseFragment<FragmentStudentDashboardBinding>() {
    @Inject
    lateinit var authService: AuthService

    override val viewModel: StudentDashboardViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_student_dashboard

    override fun onBindView(view: View) {
        super.onBindView(view)
        binding?.run {
            btnLogout.setOnClickListener {
                authService.logOut()
                findNavController().navigate(
                    StudentDashboardFragmentDirections.actionStudentDashboardToLogin()
                )
            }

            btnStartQuiz.setOnClickListener {
                findNavController().navigate(
                    StudentDashboardFragmentDirections.actionStudentDashboardToParticipantQuiz(etQuizID.text.toString())
                )
            }
        }
    }
}