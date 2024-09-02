package com.jy.mob23quizappfirebase.data.repo

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jy.mob23quizappfirebase.data.model.User
import com.jy.mob23quizappfirebase.core.services.AuthService
import kotlinx.coroutines.tasks.await

class UserRepo(
    private val authService: AuthService
) {
    fun getUid(): String {
        return authService.getUid() ?: throw Exception("User doesn't exist")
    }

    private fun getCollection(): CollectionReference {
        return Firebase.firestore.collection("users")
    }

    suspend fun createUser(user: User) {
        getCollection().document(getUid()).set(user).await()
    }

    suspend fun getUser(): User? {
        val res = getCollection().document(getUid()).get().await()
        return res.data?.let {
            User.fromMap(it)
        }
    }
}