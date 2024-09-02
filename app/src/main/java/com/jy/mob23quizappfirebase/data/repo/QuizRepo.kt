package com.jy.mob23quizappfirebase.data.repo

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jy.mob23quizappfirebase.data.model.Quiz
import com.jy.mob23quizappfirebase.core.services.AuthService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class QuizRepo(
    private val authService: AuthService
) {

    private fun getCollection(): CollectionReference {
        return Firebase.firestore.collection("quizzes")
    }
    fun getAllQuizzes() = callbackFlow<List<Quiz>> {
        val listener = getCollection().addSnapshotListener{ value, error ->
            if (error != null) { throw error }

            val quizzes = mutableListOf<Quiz>()

            value?.documents?.map { item ->
                item.data?.let { quizMap ->
                    val quiz = Quiz.fromMap(quizMap) // from Map type to Quiz type
                    if(quiz.teacherId == authService.getUid())
                        quizzes.add(quiz.copy(id = item.id))
                }
            }
            trySend(quizzes)
        }
        awaitClose{ listener.remove() }
    }

    suspend fun addQuiz(quiz: Quiz): String? {
        val data = quiz.copy(teacherId = authService.getUid())
        val res = getCollection().add(data.toMap()).await()
        return res?.id
    }

    suspend fun deleteQuiz(id: String) {
        getCollection().document(id).delete().await()
    }

    suspend fun updateQuiz(quiz: Quiz) {
        getCollection().document(quiz.id!!).set(quiz.toMap()).await()
    }

    suspend fun getQuizById(id: String): Quiz? {
        val res = getCollection().document(id).get().await()
        return res.data?.let { Quiz.fromMap(it).copy(id = res.id) }
    }
}