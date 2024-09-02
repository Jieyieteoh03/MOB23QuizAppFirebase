package com.jy.mob23quizappfirebase.ui.addQuiz

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.jy.mob23quizappfirebase.data.model.Question
import com.jy.mob23quizappfirebase.data.model.Quiz
import com.jy.mob23quizappfirebase.data.repo.QuizRepo
import com.jy.mob23quizappfirebase.core.services.AuthService
import com.jy.mob23quizappfirebase.core.services.StorageService
import com.jy.mob23quizappfirebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddQuizViewModel @Inject constructor(
    private val repo: QuizRepo,
    private val authService: AuthService,
    private val storageService: StorageService
):BaseViewModel() {
    private var questions: List<Question> = emptyList()
    fun addQuiz(quiz: Quiz) {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                val id = authService.getUid() ?: throw Exception("User not found")
                val newQuiz = quiz.copy(questions = questions, teacherId = id)
                if (newQuiz.questions.isEmpty()){
                   throw Exception("Please put CSV file.")
                }
                 repo.addQuiz(newQuiz) ?: throw Exception("Unexpected error occurred.")
            }?.let {
                _finish.emit(
                    "Added successfully"
                )
            }
        }
    }
    fun getQuestionsFromCSV(uri: Uri) {
        questions = storageService.getQuestionsFromCSV(uri)
    }
}