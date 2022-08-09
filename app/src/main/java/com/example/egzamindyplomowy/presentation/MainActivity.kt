package com.example.egzamindyplomowy.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.egzamindyplomowy.common.LOGIN_MODE
import com.example.egzamindyplomowy.presentation.introduction.login.LoginScreen
import com.example.egzamindyplomowy.presentation.introduction.welcome.WelcomeScreen
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
                    startDestination = Screen.WelcomeScreen.route
                ) {
                    composable(route = Screen.WelcomeScreen.route) {
                        WelcomeScreen(navController = navController)
                    }
                    composable(
                        route = Screen.LoginScreen.route + "/{$LOGIN_MODE}",
                        arguments = listOf(
                            navArgument(LOGIN_MODE) {
                                type = NavType.StringType
                                nullable = false
                            }
                        )
                    ) {
                        LoginScreen(
                            loginMode = it.arguments!!.getString(LOGIN_MODE)!!,
                            loginViewModel = viewModel()
                        )
                    }
                }
            }
        }
    }
}