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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.budetbybrock.ui.components.FormField
import com.example.budetbybrock.ui.navigation.Routes
import com.example.budetbybrock.ui.viewmodels.SignUpViewModel
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(navHostController: NavHostController) {
    val viewModel: SignUpViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState

    LaunchedEffect(state.signUpSuccess){
        if (state.signUpSuccess){
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
        Surface(elevation = 2.dp) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Create Account", style = MaterialTheme.typography.h5)
                FormField(
                    value = state.email.trim(),
                    onValueChange = {state.email = it.trim()},
                    placeholder = {Text("Email")},
                    error = state.emailError
                )
                FormField(
                    value = state.emailConfirmation.trim(),
                    onValueChange = {state.emailConfirmation = it.trim()},
                    placeholder = {Text("Email Confirmation")},
                    error = state.emailConfirmationError
                )
                FormField(
                    value = state.password,
                    onValueChange = {state.password = it},
                    placeholder = {Text("Password")},
                    error = state.passwordError,
                    password = true
                )
                FormField(
                    value = state.passwordConfirmation,
                    onValueChange = {state.passwordConfirmation = it},
                    placeholder = {Text("Password Confirmation")},
                    error = state.passwordConfirmationError,
                    password = true
                )
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { navHostController.popBackStack() }) {
                        Text(text = "Cancel")
                    }
                    Button(onClick = { scope.launch{viewModel.signUp()} }, elevation = null) {
                        Text(text = "Create Account")
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