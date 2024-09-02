package com.jy.mob23quizappfirebase.ui.viewQuiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.jy.mob23quizappfirebase.data.model.Quiz
import com.jy.mob23quizappfirebase.data.repo.QuizRepo
import com.jy.mob23quizappfirebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewQuizViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val quizRepo: QuizRepo,
): BaseViewModel(){
    private val _quiz = MutableSharedFlow<Quiz>()
    val quiz: SharedFlow<Quiz> = _quiz

    init {
        savedStateHandle.get<String>("id")?.let { getQuizById(it) }
    }
    private fun getQuizById(id:String) {
        viewModelScope.launch (Dispatchers.IO){
            errorHandler {
                val quiz = quizRepo.getQuizById(id) ?: throw Exception("Quiz doesn't exist")
                _quiz.emit(quiz)
            }
        }
    }
}