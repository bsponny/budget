package com.example.budetbybrock.ui.repositories

import com.example.budetbybrock.ui.models.Transaction
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object TransactionRepository {
    private val incomeCache = mutableListOf<Transaction>()
    private var incomeCacheInitialized = false
    private val expenseCache = mutableListOf<Transaction>()
    private var expenseCacheInitialized = false
    private val totalCache = mutableListOf<Transaction>()
    private var totalCacheInitialized = false

    suspend fun createTransaction(
        type: Int,
        date: String,
        amount: Double,
        description: String
    ): Transaction {
        val doc = Firebase.firestore.collection("transactions").document()

        val transaction = Transaction(
            id = doc.id,
            userId = UserRepository.getCurrentUserId(),
            type = type,
            date = date,
            amount = amount,
            description = description
        )

        doc.set(transaction).await()

        if (transaction.type == Transaction.INCOME){
            incomeCache.add(transaction)
        }
        else{
            expenseCache.add(transaction)
        }
        return transaction
    }

    suspend fun getTransactions(): List<Transaction>{
        if (!totalCacheInitialized) {
            val snapshot = Firebase.firestore
                .collection("transactions")
                .whereEqualTo("userId", UserRepository.getCurrentUserId())
                .get()
                .await()
            totalCache.addAll(snapshot.toObjects())
            totalCacheInitialized = true
        }
        return totalCache
    }

    suspend fun getTransactions(type: Int): List<Transaction>{
        if (type == Transaction.INCOME) {
            if (!incomeCacheInitialized) {
                val snapshot = Firebase.firestore
                    .collection("transactions")
                    .whereEqualTo("userId", UserRepository.getCurrentUserId())
                    .whereEqualTo("type", Transaction.INCOME)
                    .get()
                    .await()
                incomeCache.addAll(snapshot.toObjects())
                incomeCacheInitialized = true
            }
            return incomeCache
        }
        else{
            if (!expenseCacheInitialized) {
                val snapshot = Firebase.firestore
                    .collection("transactions")
                    .whereEqualTo("userId", UserRepository.getCurrentUserId())
                    .whereEqualTo("type", Transaction.EXPENSE)
                    .get()
                    .await()
                expenseCache.addAll(snapshot.toObjects())
                expenseCacheInitialized = true
            }
            return expenseCache
        }
    }

    suspend fun updateTransaction(transaction: Transaction){
        Firebase.firestore
            .collection("transactions")
            .document(transaction.id!!)
            .set(transaction)
            .await()

        if (transaction.type == Transaction.INCOME){
            val oldTransactionIndex = incomeCache.indexOfFirst {
                it.id == transaction.id
            }
            incomeCache[oldTransactionIndex] = transaction
        }
        else{
            val oldTransactionIndex = expenseCache.indexOfFirst {
                it.id == transaction.id
            }
            expenseCache[oldTransactionIndex] = transaction
        }
        val oldTransactionIndex = totalCache.indexOfFirst{
            it.id == transaction.id
        }
        totalCache[oldTransactionIndex] = transaction
    }

    suspend fun deleteTransaction(transaction: Transaction){
        Firebase.firestore
            .collection("transactions")
            .document(transaction.id!!)
            .delete()
            .await()
        if (transaction.type == Transaction.INCOME){
            incomeCache.remove(transaction)
        }
        else{
            expenseCache.remove(transaction)
        }
    }
}