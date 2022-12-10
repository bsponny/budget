package com.example.budetbybrock.ui.repositories

import androidx.compose.animation.core.snap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.budetbybrock.ui.models.Transaction
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object BudgetRepository {
    private val incomeCache = mutableListOf<Transaction>()
    private val expenseCache = mutableListOf<Transaction>()

    suspend fun getIncomes(): List<Transaction>{
        val incomeSnapshot = Firebase.firestore
            .collection("transactions")
            .whereEqualTo("userId", UserRepository.getCurrentUserId())
            .whereEqualTo("type", Transaction.INCOME)
            .get()
            .await()
        incomeCache.clear()
        incomeCache.addAll(incomeSnapshot.toObjects())
        return incomeCache
    }

    suspend fun getExpenses(): List<Transaction>{
        val snapshot = Firebase.firestore
            .collection("transactions")
            .whereEqualTo("userId", UserRepository.getCurrentUserId())
            .whereEqualTo("type", Transaction.EXPENSE)
            .get()
            .await()
        expenseCache.clear()
        expenseCache.addAll(snapshot.toObjects())
        return expenseCache
    }
}