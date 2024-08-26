package com.jy.mob23quizappfirebase.core.data.model

data class Questions(
    val title: String,
    val questions: List<String>,
    val answers: String
) {
    companion object {
        fun fromMap(map: Map<*,*>?): Questions? {
            return map?.let {
                Questions(
                    title = it["title"].toString(),
                    questions = (it["questions"] as? List<*>)?.map { question -> question.toString() } ?: emptyList(),
                    answers = it["answers"].toString()
                )
            }
        }
    }
}
