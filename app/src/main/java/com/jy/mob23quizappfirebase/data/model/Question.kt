package com.jy.mob23quizappfirebase.data.model

data class Question(
    val title: String,
    val options: List<String>,
    val answers: String
) {
    companion object {
        fun fromMap(map: Map<*,*>): Question {
            return map.let {
                Question(
                    title = it["title"].toString(),
                    options = (it["options"] as? List<*>)?.map { option -> option.toString() } ?: emptyList(),
                    answers = it["answers"].toString()
                )
            }
        }
    }
}
