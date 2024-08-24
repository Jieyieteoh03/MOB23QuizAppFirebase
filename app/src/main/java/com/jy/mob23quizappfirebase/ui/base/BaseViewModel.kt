package com.jy.mob23quizappfirebase.ui.base

import androidx.lifecycle.ViewModel
import com.jy.mob23quizappfirebase.core.data.model.Role
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseViewModel:ViewModel() {
    protected val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error
    protected val _finish = MutableSharedFlow<String>()
    val finish: SharedFlow<String> = _finish
    protected val _success = MutableSharedFlow<String>()
    val success: SharedFlow<String> = _success
    protected val _role = MutableSharedFlow<Role>()
    val role: SharedFlow<Role> = _role

    suspend fun <T>errorHandler(func: suspend () -> T?): T? {
        return try{
            func()
        } catch (e: Exception) {
            _error.emit(e.message.toString())
            e.printStackTrace()
            null
        }
    }
}