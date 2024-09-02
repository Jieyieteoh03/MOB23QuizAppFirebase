package com.jy.mob23quizappfirebase.ui.startPage


import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jy.mob23quizappfirebase.R
import com.jy.mob23quizappfirebase.databinding.FragmentStartPageBinding
import com.jy.mob23quizappfirebase.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartPageFragment : BaseFragment<FragmentStartPageBinding>() {
    override val viewModel: StartPageViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_start_page
    override fun onBindView(view: View) {
        super.onBindView(view)
        binding?.run {
            btnLogin.setOnClickListener {
                findNavController().navigate(
                    StartPageFragmentDirections.actionStartPageToLogin()
                )
            }
            btnSignUp.setOnClickListener {
                findNavController().navigate(
                    StartPageFragmentDirections.actionStartPageToSignUp()
                )
            }
        }
    }
}

