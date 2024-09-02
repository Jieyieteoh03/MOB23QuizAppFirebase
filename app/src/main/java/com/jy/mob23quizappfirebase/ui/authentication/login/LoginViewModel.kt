package com.jy.mob23quizappfirebase.ui.authentication.login

import androidx.lifecycle.viewModelScope
import com.jy.mob23quizappfirebase.data.repo.UserRepo
import com.jy.mob23quizappfirebase.core.services.AuthService
import com.jy.mob23quizappfirebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService:AuthService,
    private val userRepo: UserRepo
): BaseViewModel() {
    fun login(email:String, password:String) {
        viewModelScope.launch (Dispatchers.IO){
            errorHandler {
                validate(email, password)
                authService.loginEmailWithPassword(email, password)
                val user = userRepo.getUser() ?: throw Exception("User not found")
                _role.emit(user.role!!)
            }?.let {
                _success.emit("Login Successful")
            }
        }
    }

    private fun validate(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            throw Exception("Please enter required field")
        }
    }
}