package com.example.budetbybrock.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.budetbybrock.ui.models.Transaction
import com.example.budetbybrock.ui.repositories.TransactionRepository

class TransactionScreenState{
    val _transactions = mutableStateListOf<Transaction>()
    val transactions: List<Transaction> get() = _transactions
    var loading by mutableStateOf(true)
}

class TransactionViewModel (application: Application): AndroidViewModel(application){
    val uiState = TransactionScreenState()

    suspend fun getTransactions(){
        uiState._transactions.clear()
        val transactions = TransactionRepository.getTransactions()
        uiState._transactions.addAll(transactions)
    }

    suspend fun getTransactions(type: Int){
        uiState._transactions.clear()
        val transactions = TransactionRepository.getTransactions(type)
        uiState._transactions.addAll(transactions)
    }

    suspend fun deleteTransaction(transaction: Transaction){
        TransactionRepository.deleteTransaction(transaction)
        uiState._transactions.remove(transaction)
    }
}