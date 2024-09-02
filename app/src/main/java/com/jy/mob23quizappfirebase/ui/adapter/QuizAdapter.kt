package com.jy.mob23quizappfirebase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jy.mob23quizappfirebase.data.model.Quiz
import com.jy.mob23quizappfirebase.databinding.ItemQuizBinding

class QuizAdapter (
    private var quizzes: List<Quiz>
): RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {
    var listener: Listener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuizViewHolder {
        val binding = ItemQuizBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuizViewHolder(binding)
    }

    override fun getItemCount(): Int = quizzes.size
    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val item = quizzes[position]
        holder.bind(item)
    }

    fun setQuiz(quiz:List<Quiz>) {
        this.quizzes = quiz
        notifyDataSetChanged()
    }

    inner class QuizViewHolder(
        private val binding: ItemQuizBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(quiz: Quiz) {
            binding.tvTitleQuiz.text = quiz.title
            binding.tvDescQuiz.text = quiz.desc
            binding.tvTimerQuiz.text = quiz.timer
            binding.cvQuiz.setOnClickListener {
                listener?.onClickItem(quiz)
            }
            binding.ivDelete.setOnClickListener {
                listener?.onDeleteItem(quiz)
            }
        }
    }
    interface Listener {
        fun onClickItem(quiz: Quiz)
        fun onDeleteItem(quiz: Quiz)
    }
}