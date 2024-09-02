package com.jy.mob23quizappfirebase.ui.addQuiz

import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.jy.mob23quizappfirebase.R
import com.jy.mob23quizappfirebase.data.model.Quiz
import com.jy.mob23quizappfirebase.databinding.FragmentAddQuizBinding
import com.jy.mob23quizappfirebase.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddQuizFragment : BaseFragment<FragmentAddQuizBinding>() {
    override val viewModel: AddQuizViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_add_quiz
    private lateinit var resultLauncher: ActivityResultLauncher<Array<String>>
    override fun onBindView(view: View) {
        super.onBindView(view)
        resultLauncher = setupResultLauncher()
        binding?.run {
            btnImportCSV.setOnClickListener {
                resultLauncher.launch(arrayOf("text/comma-separated-values"))
            }
            btnSubmitQuiz.setOnClickListener {
                val quiz = Quiz(
                    title = etQuizTitle.text.toString(),
                    desc = etDesc.text.toString(),
                    timer = spTimer.selectedItem.toString()
                )
                viewModel.addQuiz(quiz)
            }
        }
    }

    private fun setupResultLauncher(): ActivityResultLauncher<Array<String>> {
        return registerForActivityResult(
            ActivityResultContracts.OpenDocument()
        ) { uri ->
            uri?.let {
                viewModel.getQuestionsFromCSV(it)
            }
        }
    }
}