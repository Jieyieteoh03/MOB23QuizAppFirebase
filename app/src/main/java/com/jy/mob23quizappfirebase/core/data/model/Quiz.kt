package com.jy.mob23quizappfirebase.core.data.model

data class Quiz(
    val title: String,
    val desc: String,
    val timer: String,
    val quizQues: Questions? = null
) {

    fun toMap(): Map<String,Any?> {
        return mapOf(
            "title" to title,
            "desc" to desc,
            "timer" to timer,
            "quizQues" to quizQues
        )
    }
    fun fromMap(map: Map<*,*>?): Quiz? {
        return map?.let {
            Quiz(
                title = it["title"].toString(),
                desc = it["desc"].toString(),
                timer = it["timer"].toString(),
                quizQues = Questions.fromMap(map["quizQues"] as Map<*, *>? )

            )
        }
    }
}
