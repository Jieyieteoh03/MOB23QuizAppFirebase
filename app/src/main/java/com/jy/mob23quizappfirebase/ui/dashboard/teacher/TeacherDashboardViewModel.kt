package com.jy.mob23quizappfirebase.ui.dashboard.teacher

import androidx.lifecycle.viewModelScope
import com.jy.mob23quizappfirebase.data.model.Quiz
import com.jy.mob23quizappfirebase.data.repo.QuizRepo
import com.jy.mob23quizappfirebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherDashboardViewModel @Inject constructor(
    private val quizRepo: QuizRepo
    ): BaseViewModel() {
    fun getAllTeacherQuiz(): Flow<List<Quiz>> = quizRepo.getAllQuizzes()

    fun deleteQuiz(id:String) {
        viewModelScope.launch (Dispatchers.IO){
            errorHandler {
                quizRepo.deleteQuiz(id)
            }
            _success.emit("Deleted successfully")
        }
    }
}