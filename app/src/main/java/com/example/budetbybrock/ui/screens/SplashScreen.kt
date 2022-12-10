package com.example.budetbybrock.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.budetbybrock.ui.navigation.Routes
import com.example.budetbybrock.ui.viewmodels.SplashScreenViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navHostController: NavHostController) {
    val viewModel: SplashScreenViewModel = viewModel()
    LaunchedEffect(true) {
        // wait for 3 seconds or until the login check is
        // done before navigating
        delay(1000)
        navHostController.navigate(
            if (viewModel.isUserLoggedIn()){
                Routes.budgetNavigation.route
            }
            else {
                Routes.launchNavigation.route
            }
        ) {
            // makes it so that we can't get back to the
            // splash screen by pushing the back button
            popUpTo(navHostController.graph.findStartDestination().id) {
                inclusive = true
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = "Budget By Brock",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center
        )
        Text(
            text = "bsponny@2022",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
        )
    }
}