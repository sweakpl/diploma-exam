package com.sweak.diplomaexam.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.sweak.diplomaexam.presentation.exam_score.ExamScoreScreen
import com.sweak.diplomaexam.presentation.lobby.LobbyScreen
import com.sweak.diplomaexam.presentation.login.LoginScreen
import com.sweak.diplomaexam.presentation.questions_answering.QuestionsAnsweringScreen
import com.sweak.diplomaexam.presentation.questions_draw.QuestionsDrawScreen
import com.sweak.diplomaexam.presentation.ui.theme.DiplomaExamTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DiplomaExamTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.LoginScreen.route
                ) {
                    composable(route = Screen.LoginScreen.route) {
                        LoginScreen(navController = navController)
                    }

                    composable(route = Screen.LobbyScreen.route) {
                        LobbyScreen(navController = navController)
                    }

                    composable(route = Screen.QuestionsDrawScreen.route) {
                        QuestionsDrawScreen(navController = navController)
                    }

                    composable(route = Screen.QuestionsAnsweringScreen.route) {
                        QuestionsAnsweringScreen(navController = navController)
                    }

                    composable(route = Screen.ExamScoreScreen.route) {
                        ExamScoreScreen(navController = navController)
                    }
                }
            }
        }
    }
}