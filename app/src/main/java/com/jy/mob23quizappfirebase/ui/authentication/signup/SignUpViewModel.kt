package com.jy.mob23quizappfirebase.ui.authentication.signup

import androidx.lifecycle.viewModelScope
import com.jy.mob23quizappfirebase.core.data.model.User
import com.jy.mob23quizappfirebase.core.data.model.ValidationField
import com.jy.mob23quizappfirebase.core.data.repo.UserRepo
import com.jy.mob23quizappfirebase.core.services.AuthService
import com.jy.mob23quizappfirebase.core.utils.ValidationUtils
import com.jy.mob23quizappfirebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authService: AuthService,
    private val userRepo: UserRepo
): BaseViewModel() {

    fun signUp(user: User, password:String, password2:String) {
        viewModelScope.launch {
            errorHandler {
                val error = validateError(user, password)
                if(error != null) throw Exception(error) // If validation fails, throw error and stop here
                if (password != password2) throw Exception("Enter valid confirm password") // If passwords dont match, throw error and stop here
                authService.createUserWithEmailAndPassword(user.email, password)
                userRepo.createUser(user)
            }?.let {
                _success.emit("Sign Up Successful")
            }
        }
    }

    private fun validateError(user: User, password:String): String? =
        ValidationUtils.validate(
            ValidationField(user.name,"[a-zA-Z ]{2,20}", "Enter a valid name"),
            ValidationField(user.email,"[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+", "Enter a valid email"),
            ValidationField(password,"[a-zA-Z0-9#$%]{3,20}", "Enter a valid password")
        )
}