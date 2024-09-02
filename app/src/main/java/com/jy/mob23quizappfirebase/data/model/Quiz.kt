package com.jy.mob23quizappfirebase.data.model

data class Quiz(
    val id: String? = null,
    val teacherId: String? = null,
    val title: String,
    val desc: String,
    val timer: String,
    val questions: List<Question> = emptyList(),
    val studentList: List<Student> = emptyList()
) {

    fun toMap(): Map<String,Any?> {
        return mapOf(
            "title" to title,
            "desc" to desc,
            "teacherId" to teacherId,
            "timer" to timer,
            "questions" to questions,
            "studentList" to studentList
        )
    }

    companion object {
        fun fromMap(map: Map<*,*>): Quiz =
            Quiz(
                title = map["title"].toString(),
                desc = map["desc"].toString(),
                teacherId = map["teacherId"].toString(),
                timer = map["timer"].toString(),
                questions = (map["questions"] as? ArrayList<*>)?.let { questions ->
                    questions.map { Question.fromMap(it as Map<*, *>) }
                } ?: emptyList(),
                studentList = (map["studentList"] as? ArrayList<*>)?.let { students ->
                    students.map { Student.fromMap(it as Map<*, *>) }
                } ?: emptyList()
            )
    }

}
