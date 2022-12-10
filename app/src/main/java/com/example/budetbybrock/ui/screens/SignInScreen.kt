package com.example.budetbybrock.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.budetbybrock.ui.viewmodels.SignInViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budetbybrock.ui.components.FormField
import com.example.budetbybrock.ui.navigation.Routes
import kotlinx.coroutines.launch

@Composable
fun SignInScreen (navHostController: NavHostController){
    val viewModel: SignInViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState

    LaunchedEffect(state.loginSuccess){
        if (state.loginSuccess){
            navHostController.navigate(Routes.budgetNavigation.route){
                popUpTo(0)
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Surface(elevation = 2.dp){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Sign In", style = MaterialTheme.typography.h5)
                FormField(
                    value = state.email.trim(),
                    onValueChange = {state.email = it.trim()},
                    placeholder = { Text(text = "Email") },
                    error = state.emailError
                )
                FormField(
                    value = state.password,
                    onValueChange = {state.password = it},
                    placeholder = { Text(text = "Password") },
                    error = state.passwordError,
                    password = true
                )
                Row (
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ){
                    TextButton(onClick = { navHostController.popBackStack() }) {
                        Text(text = "Cancel")
                    }
                    Button(onClick = { scope.launch { viewModel.signIn() } }, elevation = null) {
                        Text(text = "Sign In")
                    }
                }
                Text(
                    text = state.errorMessage,
                    style = TextStyle(color = MaterialTheme.colors.error),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Right
                )
            }
        }
    }
}