package com.jy.mob23quizappfirebase.ui.authentication.login


import android.view.View
import androidx.fragment.app.viewModels
import com.jy.mob23quizappfirebase.R
import com.jy.mob23quizappfirebase.databinding.FragmentLoginBinding
import com.jy.mob23quizappfirebase.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val viewModel: LoginViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_login

    override fun onBindView(view: View) {
        super.onBindView(view)

        binding?.run {
            btnLogin.setOnClickListener {
                viewModel.login(
                    etEmail.text.toString(),
                    etPassword.text.toString(),
                )
            }
        }
    }
}