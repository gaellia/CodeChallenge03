package com.launchpad.codechallenge03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.rememberNavController
import com.launchpad.codechallenge03.navigation.AppNavigation
import com.launchpad.codechallenge03.ui.theme.CodeChallenge03Theme

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun App() {
    val navController = rememberNavController()
    CodeChallenge03Theme {
        Surface(color = MaterialTheme.colors.background) {
            AppNavigation(navController)
        }
    }
}