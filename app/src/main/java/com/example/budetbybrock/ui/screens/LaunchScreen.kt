package com.example.budetbybrock.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.budetbybrock.ui.navigation.Routes

@Composable
fun LaunchScreen(navHostController: NavHostController) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Welcome!",
                style = MaterialTheme.typography.h1,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "This is the best budgeting app out there",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Before you continue you will need to sign into your account",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { navHostController.navigate(Routes.signIn.route) }) {
                Text(text = "Sign In")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Don't have an account?")
                TextButton(onClick = { navHostController.navigate(Routes.signUp.route) }) {
                    Text(text = "Create Account")
                }
            }
        }
    }
}