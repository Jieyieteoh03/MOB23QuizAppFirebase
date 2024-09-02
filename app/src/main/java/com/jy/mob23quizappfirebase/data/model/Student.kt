package com.jy.mob23quizappfirebase.data.model

data class Student(
    val studentEmail: String,
    val score: Int,
    val timeTaken: Int
) {
    companion object {
        fun fromMap(map: Map<*,*>): Student {
            return map.let {
                Student(
                    studentEmail = it["studentEmail"].toString(),
                    score = it["score"].toString().toInt(),
                    timeTaken = it["timeTaken"].toString().toInt()
                )
            }
        }
    }

}
