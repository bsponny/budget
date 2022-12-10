package com.example.budetbybrock.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.budetbybrock.ui.models.Transaction
import com.example.budetbybrock.ui.repositories.BudgetRepository

class BudgetScreenState{
    var incomeTotal by mutableStateOf(0f.toDouble())
    var expenseTotal by mutableStateOf(0f.toDouble())
    var difference by mutableStateOf(0f.toDouble())
    var loading by mutableStateOf(true)
}

class BudgetViewModel(application: Application): AndroidViewModel(application) {
    val uiState = BudgetScreenState()

    suspend fun getIncomes(){
        val incomes = BudgetRepository.getIncomes()
        var incomeTotal = 0f.toDouble()
        for (income in incomes){
            println("incomeTotal: $incomeTotal")
            incomeTotal += income.amount ?: 0f.toDouble()
        }
        uiState.incomeTotal = incomeTotal
    }

    suspend fun getExpenses(){
        val expenses = BudgetRepository.getExpenses()
        var expenseTotal = 0f.toDouble()
        for (expense in expenses){
            expenseTotal += expense.amount ?: 0f.toDouble()
        }
        uiState.expenseTotal = expenseTotal
    }

    fun updateDifference(){
        uiState.difference = uiState.incomeTotal - uiState.expenseTotal
    }
}