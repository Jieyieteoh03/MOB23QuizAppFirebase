package com.jy.mob23quizappfirebase.ui.participantQuiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.jy.mob23quizappfirebase.data.model.Quiz
import com.jy.mob23quizappfirebase.data.model.Role
import com.jy.mob23quizappfirebase.data.model.Student
import com.jy.mob23quizappfirebase.data.repo.QuizRepo
import com.jy.mob23quizappfirebase.data.repo.UserRepo
import com.jy.mob23quizappfirebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParticipantQuizViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val quizRepo: QuizRepo,
    private val userRepo: UserRepo
): BaseViewModel() {
    private val _quiz = MutableSharedFlow<Quiz>()
    val quiz: SharedFlow<Quiz> = _quiz
    private var currentQuiz: Quiz? = null
    private val _role1 = MutableSharedFlow<Role>()
    val role1: SharedFlow<Role> = _role1
    init {
        viewModelScope.launch(Dispatchers.IO) {
            val id = savedStateHandle.get<String>("quizId").toString()
            showQuiz(id)
        }
    }
    private fun showQuiz(id: String) {
        viewModelScope.launch (Dispatchers.IO){
            errorHandler {
                if(id.isEmpty()) throw Exception("Failed to get quiz, please check again.")
                val quiz = quizRepo.getQuizById(id) ?: throw Exception("Failed to get quiz, please check again.")
                currentQuiz = quiz
                _quiz.emit(quiz)

            }
        }
    }
    fun updateQuiz(score: Int, timeTaken: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = errorHandler { userRepo.getUser() }
            if(user != null) {
                val student = Student(user.email, score, timeTaken)
                val studentList = currentQuiz!!.studentList.toMutableList()
                val idx = studentList.indexOfFirst { it.studentEmail == student.studentEmail }
                if (idx != -1) {
                    studentList[idx] = student
                } else {
                    studentList.add(student)
                }
                errorHandler {
                    quizRepo.updateQuiz(currentQuiz!!.copy(studentList = studentList))
                }
            }
        }
    }
    fun getUserRole() {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                val role = userRepo.getUser()?.role
                if (role != null) {
                    _role1.emit(role)
                }
            }
        }
    }

    fun getSeconds(time: String): Int {
        return when(time) {
            "30 seconds" -> 30
            "1 minute" -> 60
            "5 minutes" -> 5 * 60
            "10 minutes" -> 10 * 60
            "20 minutes" -> 20 * 60
            "30 minutes" -> 30 * 60
            "1 hour" -> 60 * 60
            else -> 10 * 60
        }
    }

    fun getScore(list: List<String>): Int {
        val results = mutableListOf<Boolean>()
        val questions = currentQuiz!!.questions
        questions.forEachIndexed { index, _ ->
            results.add(questions[index].answers == list[index])
        }
        return results.count { it }
    }

    fun getCurrentQuiz(): Quiz? = currentQuiz
}