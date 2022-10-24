package com.sweak.diplomaexam.presentation

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object SessionSelectionScreen : Screen("session_selection_screen")
    object LobbyScreen : Screen("lobby_screen")
    object QuestionsDrawScreen : Screen("questions_draw_screen")
    object QuestionsAnsweringScreen : Screen("questions_answering_screen")
    object ExamScoreScreen : Screen("exam_score_screen")
}
