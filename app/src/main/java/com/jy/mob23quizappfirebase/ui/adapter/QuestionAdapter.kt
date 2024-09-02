package com.jy.mob23quizappfirebase.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jy.mob23quizappfirebase.data.model.Question
import com.jy.mob23quizappfirebase.data.model.Role
import com.jy.mob23quizappfirebase.databinding.ItemQuizQuestionsBinding

class QuestionAdapter(
    private var questions: List<Question>,
):RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {
    private var answerList = mutableListOf<String>()
    private var role: Role? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = ItemQuizQuestionsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuestionViewHolder(binding)
    }
    override fun getItemCount(): Int = questions.size
    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val item = questions[position]
        holder.bind(item, position)


    }
    fun setRole(role: Role) {
        this.role = role
    }
    fun setQuestions(questions: List<Question>) {
        this.questions = questions
        answerList = MutableList(questions.size) { "" }
        notifyDataSetChanged()
    }
    fun getAnswerList(): List<String> = answerList.toList()
    inner class QuestionViewHolder(
        private val binding: ItemQuizQuestionsBinding
    ):ViewHolder(binding.root) {
        fun bind(question: Question, position: Int) {
            binding.run {
                if(role != null && role == Role.STUDENT) {
                    tvQuesAns.visibility = View.GONE
                }
                val buttons = setOf(rbAns1, rbAns2, rbAns3, rbAns4)
                tvTitleQuiz.text = question.title
                buttons.forEachIndexed { index, button ->
                    button.apply {
                        text = question.options[index]
                        isChecked = answerList[position] == question.options[index]
                        setOnClickListener { view ->
                            buttons.map {
                                if(it.id != view.id) it.isChecked = false
                            }
                            answerList[position] = (view as RadioButton).text.toString()
                        }
                    }
                }
                tvQuesAns.text = question.answers
            }
        }
    }

}