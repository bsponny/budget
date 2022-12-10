package com.example.budetbybrock.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.budetbybrock.ui.models.Transaction
import com.example.budetbybrock.ui.repositories.TransactionRepository

class TransactionModificationScreenState{
    var type by mutableStateOf(Transaction.INCOME)
    var date by mutableStateOf("")
    var amount by mutableStateOf("")
    var description by mutableStateOf("")

    var dateError by mutableStateOf(false)
    var amountError by mutableStateOf(false)

    var typeDropdownExpanded by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var saveSuccess by mutableStateOf(false)
}

class TransactionModificationViewModel(application: Application): AndroidViewModel(application) {
    val uiState = TransactionModificationScreenState()
    var id: String? = null

    suspend fun saveTransaction(){
        uiState.errorMessage = ""
        uiState.amountError = false
        uiState.dateError = false
        val dateRegex = Regex("""^\w{3}\s\d{1,2}$""")

        if (uiState.date.isEmpty()){
            uiState.dateError = true
            uiState.errorMessage = "Date cannot be blank"
            return
        }
        if (!uiState.date.matches(dateRegex)){
            uiState.dateError = true
            uiState.errorMessage = "Date must be input in the format \"Nov 25\""
            return
        }

        if (uiState.amount.toDouble().isNaN()){
            uiState.amountError = true
            uiState.errorMessage = "Amount cannot be blank"
            return
        }

        if (id == null) {
            TransactionRepository.createTransaction(
                uiState.type,
                uiState.date,
                uiState.amount.toDouble(),
                uiState.description
            )
        }
        else{
            val transaction = TransactionRepository.getTransactions(uiState.type).find {it.id == id} ?: return
            TransactionRepository.updateTransaction(
                transaction.copy(
                    type = uiState.type,
                    date = uiState.date,
                    amount = uiState.amount.toDouble(),
                    description = uiState.description
                )
            )
        }
        uiState.saveSuccess = true
    }

    suspend fun setUpInitialState(id: String?){
        println("id: $id")
        if (id == null || id == "new") return
        this.id = id
        val transaction = TransactionRepository.getTransactions().find {it.id == id} ?: return

        uiState.type = transaction.type ?: Transaction.EXPENSE
        uiState.date = transaction.date ?: ""
        uiState.amount = transaction.amount.toString() ?: ""
        uiState.description = transaction.description ?: ""
    }
}