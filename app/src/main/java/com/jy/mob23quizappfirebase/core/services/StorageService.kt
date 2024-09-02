package com.jy.mob23quizappfirebase.core.services

import android.content.Context
import android.net.Uri
import com.jy.mob23quizappfirebase.data.model.Question
import java.io.BufferedReader
import java.io.InputStreamReader

class StorageService(
    private val context: Context
) {
    fun getQuestionsFromCSV(uri: Uri): List<Question> =
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            extractContent(bufferedReader)
        } ?: emptyList()

    private fun extractContent(bufferedReader: BufferedReader): List<Question> {
        val questions: MutableList<Question> = mutableListOf()
        var isFirstLine = true
        bufferedReader.forEachLine { line ->
            if(isFirstLine) isFirstLine = false
            else {
                val values = line.split(",")
                questions.add(
                    Question(title = values[0], options = values.subList(1, 5), answers = values[5])
                )
            }
        }
        return questions.toList()
    }
}