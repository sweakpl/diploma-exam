package com.example.egzamindyplomowy.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.egzamindyplomowy.presentation.introduction.login.LoginScreen
import com.example.egzamindyplomowy.presentation.introduction.welcome.WelcomeScreen
import com.example.egzamindyplomowy.presentation.ui.theme.EgzaminDyplomowyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EgzaminDyplomowyTheme {
                LoginScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EgzaminDyplomowyTheme {
        LoginScreen()
    }
}