package com.sweak.diplomaexam.presentation

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object LobbyScreen : Screen("lobby_screen")

    fun withArguments(vararg arguments: String): String {
        return buildString {
            append(route)
            arguments.forEach { argument ->
                append("/$argument")
            }
        }
    }
}
