package com.example.egzamindyplomowy.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.egzamindyplomowy.presentation.login.LoginScreen
import com.example.egzamindyplomowy.presentation.ui.theme.EgzaminDyplomowyTheme

class MainActivity : ComponentActivity() {

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EgzaminDyplomowyTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.LoginScreen.route
                ) {
                    composable(route = Screen.LoginScreen.route) {
                        LoginScreen(loginViewModel = viewModel())
                    }
                }
            }
        }
    }
}