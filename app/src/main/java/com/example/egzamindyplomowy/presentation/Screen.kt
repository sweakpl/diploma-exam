package com.example.egzamindyplomowy.presentation

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")

    fun withArguments(vararg arguments: String): String {
        return buildString {
            append(route)
            arguments.forEach { argument ->
                append("/$argument")
            }
        }
    }
}
