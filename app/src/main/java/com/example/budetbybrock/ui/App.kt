package com.example.budetbybrock.ui

import androidx.compose.runtime.Composable
import com.example.budetbybrock.ui.navigation.RootNavigation
import com.example.budetbybrock.ui.theme.BudetByBrockTheme

@Composable
fun App() {
    BudetByBrockTheme {
        RootNavigation()
    }
}