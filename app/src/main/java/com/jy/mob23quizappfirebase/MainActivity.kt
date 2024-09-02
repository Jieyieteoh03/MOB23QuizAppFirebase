package com.jy.mob23quizappfirebase

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.jy.mob23quizappfirebase.core.services.AuthService
import com.jy.mob23quizappfirebase.data.model.Role
import com.jy.mob23quizappfirebase.data.repo.UserRepo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var authService: AuthService
    @Inject
    lateinit var userRepo: UserRepo
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupNavigation()
        checkLogin()
        setSplashScreenDuration()
    }

    private fun setSplashScreenDuration() {
        var keepOnScreen = true
        installSplashScreen().setKeepOnScreenCondition { keepOnScreen }
        lifecycleScope.launch(Dispatchers.IO) {
            delay(2500)
            keepOnScreen = false
        }
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()
    }

    private fun checkLogin() {
        authService.getUid() ?: return
        lifecycleScope.launch {
            val user = userRepo.getUser() ?: return@launch
            val destination = when(user.role) {
                Role.TEACHER -> R.id.teacherDashboardFragment
                else -> R.id.studentDashboardFragment
            }
            navController.navigate(
                destination, null, NavOptions.Builder().setPopUpTo(
                    navController.graph.startDestinationId, true
                ).setLaunchSingleTop(true).build()
            )
        }
    }
}