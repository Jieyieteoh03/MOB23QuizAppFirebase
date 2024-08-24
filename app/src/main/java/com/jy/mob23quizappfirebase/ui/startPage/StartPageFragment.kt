package com.jy.mob23quizappfirebase.ui.startPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jy.mob23quizappfirebase.databinding.FragmentStartpageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartPageFragment : Fragment() {
    private lateinit var binding: FragmentStartpageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentStartpageBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(
                StartPageFragmentDirections.actionStartPageToLogin()
            )

        }

        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(
                StartPageFragmentDirections.actionStartPageToSignUp()
            )
        }
    }

}