package com.hawkins.library

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hawkins.library.ui.navigation.AppNavigation
import com.hawkins.library.ui.theme.HawkinsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HawkinsTheme {
                AppNavigation()
            }
        }
    }
}
