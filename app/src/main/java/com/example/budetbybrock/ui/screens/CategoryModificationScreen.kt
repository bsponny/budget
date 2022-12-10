package com.example.budetbybrock.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import com.example.budetbybrock.ui.models.Transaction
import com.example.budetbybrock.ui.viewmodels.CategoryModificationViewModel
import com.example.budetbybrock.ui.viewmodels.CategoryViewModel
import com.example.budetbybrock.uitl.typeText
import kotlinx.coroutines.launch

@Composable
fun CategoryModificationScreen(navHostController: NavHostController, id: String?) {
    val viewModel: CategoryModificationViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()

    LaunchedEffect(state.saveSuccess){
        if (state.saveSuccess){
            navHostController.popBackStack()
        }
    }
    LaunchedEffect(true){
        viewModel.setUpInitialState(id)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Box(Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = typeText(state.type),
                modifier = Modifier
                    .clickable {
                        state.typeDropdownExpanded = true
                    }
                    .fillMaxWidth(),
                trailingIcon = {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                },
                enabled = false,
                label = { Text(text = "Priority") },
                onValueChange = {}
            )
            DropdownMenu(
                expanded = state.typeDropdownExpanded,
                onDismissRequest = { state.typeDropdownExpanded = false }) {
                listOf(
                    Transaction.INCOME,
                    Transaction.EXPENSE
                ).forEach {
                    DropdownMenuItem(onClick = {
                        state.type = it
                        state.typeDropdownExpanded = false
                    }) {
                        Text(text = typeText(it))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            FormField(
                value = state.name,
                onValueChange = { state.name = it },
                placeholder = { Text(text = "Name") },
                error = state.nameError
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            TextButton(onClick = { navHostController.popBackStack()} ) {
                Text(text = "Cancel")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { scope.launch { viewModel.saveCategory() } }) {
                Text(text = "Save")
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