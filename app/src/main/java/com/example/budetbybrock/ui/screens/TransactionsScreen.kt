package com.example.budetbybrock.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.budetbybrock.ui.components.Loader
import com.example.budetbybrock.ui.components.TransactionItem
import com.example.budetbybrock.ui.viewmodels.TransactionViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TransactionsScreen(navHostController: NavHostController, type: Int) {
    val viewModel: TransactionViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()

    LaunchedEffect(true){
        val loadTransactions = async { viewModel.getTransactions(type) }
        delay(1000)
        loadTransactions.await()
        state.loading = false
    }

    Column {
        if (state.loading){
            Spacer(modifier = Modifier.height(16.dp))
            Loader()
        }
        else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                items(state.transactions, key = { it.id!!}) { transaction ->
                    TransactionItem(
                        transaction = transaction,
                        onEditPressed = { navHostController.navigate("edittransaction?id=${transaction.id}") },
                        onDeletePressed = { scope.launch { viewModel.deleteTransaction(transaction) }}
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}