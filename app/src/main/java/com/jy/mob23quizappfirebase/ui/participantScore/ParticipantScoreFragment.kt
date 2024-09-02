package com.jy.mob23quizappfirebase.ui.participantScore


import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jy.mob23quizappfirebase.R
import com.jy.mob23quizappfirebase.databinding.FragmentParticipantScoreBinding
import com.jy.mob23quizappfirebase.ui.base.BaseFragment


class ParticipantScoreFragment : BaseFragment<FragmentParticipantScoreBinding>() {

    override val viewModel: ParticipantScoreViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_participant_score
    private val args: ParticipantScoreFragmentArgs by navArgs()

    override fun onBindView(view: View) {
        super.onBindView(view)
        binding?.run {
            tvScore.text = getString(R.string.score_text, args.scoreId)
            tvTimeTaken.text = getString(R.string.time_taken_text, args.timeTakenId)

            btnBack.setOnClickListener {
                findNavController().navigate(
                    R.id.studentDashboardFragment,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.participantScoreFragment,true)
                        .build()
                )
            }
        }
    }
}