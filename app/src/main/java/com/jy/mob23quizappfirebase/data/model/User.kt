package com.jy.mob23quizappfirebase.data.model

enum class Role{ TEACHER, STUDENT }

data class User(
    val name: String,
    val email: String,
    val role: Role? = null
) {
    companion object {
        fun fromMap(map: Map<*, *>): User {
            return User(
                name = map["name"].toString(),
                email = map["email"].toString(),
                role = Role.valueOf(map["role"].toString())
            )
        }
    }
}
