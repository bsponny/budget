package com.example.budetbybrock.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.budetbybrock.ui.components.BudgetItem
import com.example.budetbybrock.ui.components.Loader
import com.example.budetbybrock.ui.models.Transaction
import com.example.budetbybrock.ui.viewmodels.BudgetViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BudgetScreen(navHostController: NavHostController) {
    val viewModel: BudgetViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        val loadBudget = async {
            viewModel.getIncomes()
            viewModel.getExpenses()
            viewModel.updateDifference()
        }
        delay(1000)
        loadBudget.await()
        state.loading = false
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        if (state.loading) {
            Spacer(modifier = Modifier.height(16.dp))
            Loader()
        }
        else {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                BudgetItem(
                    type = 3,
                    amount = state.difference
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                BudgetItem(
                    type = Transaction.INCOME,
                    amount = state.incomeTotal,
                    onClick = { scope.launch { navHostController.navigate("transactions?type=${Transaction.INCOME}") } },
                    clickable = true
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                BudgetItem(
                    type = Transaction.EXPENSE,
                    amount = state.expenseTotal,
                    onClick = { scope.launch { navHostController.navigate("transactions?type=${Transaction.EXPENSE}") } },
                    clickable = true
                )
            }
        }
    }
}