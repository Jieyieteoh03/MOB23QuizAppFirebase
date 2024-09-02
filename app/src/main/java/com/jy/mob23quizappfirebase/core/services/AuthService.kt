package com.jy.mob23quizappfirebase.core.services

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthService (
    private val context: Context
) {
    private val auth = FirebaseAuth.getInstance()

    suspend fun createUserWithEmailAndPassword(email: String, password:String): Boolean {
        val res = auth.createUserWithEmailAndPassword(
            email,password
        ).await()
        return res.user != null
    }

    suspend fun loginEmailWithPassword(email: String, password: String): FirebaseUser? {
        val res = auth.signInWithEmailAndPassword(email,password).await()
        return res.user
    }

    fun logOut() { auth.signOut() }

    fun getUid(): String? {
        return auth.currentUser?.uid
    }
}