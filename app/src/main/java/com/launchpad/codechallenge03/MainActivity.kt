package com.launchpad.codechallenge03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.launchpad.codechallenge03.navigation.AppNavigation
import com.launchpad.codechallenge03.ui.theme.CodeChallenge03Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    CodeChallenge03Theme {
        Surface(color = MaterialTheme.colors.background) {
            AppNavigation(navController)
        }
    }
}