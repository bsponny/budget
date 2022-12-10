package com.example.budetbybrock.ui.repositories

import com.example.budetbybrock.ui.models.Category
import com.example.budetbybrock.ui.models.Transaction
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object CategoryRepository {
    private val incomeCache = mutableListOf<Category>()
    private val incomeCacheInitialized = false
    private val expenseCache = mutableListOf<Category>()
    private var expenseCacheInitialized = false
    private val totalCache = mutableListOf<Category>()
    private var totalCacheInitialized = false

    suspend fun createCategory(
        type: Int,
        name: String
    ): Category{
        val doc = Firebase.firestore.collection("categories").document()

        val category = Category(
            id = doc.id,
            userId = UserRepository.getCurrentUserId(),
            type = type,
            name = name
        )
        doc.set(category).await()
        totalCache.add(category)
        return category
    }

    suspend fun getCategories(): List<Category>{
        if (!totalCacheInitialized){
            val snapshot = Firebase.firestore
                .collection("categories")
                .whereEqualTo("userId", UserRepository.getCurrentUserId())
                .get()
                .await()
            totalCache.addAll(snapshot.toObjects())
            totalCacheInitialized = true
        }
        return totalCache
    }

    suspend fun updateCategory(category: Category){
        Firebase.firestore
            .collection("categories")
            .document(category.id!!)
            .set(category)
            .await()

        val oldCategoryIndex = totalCache.indexOfFirst {
            it.id == category.id
        }
        totalCache[oldCategoryIndex] = category
    }

    suspend fun deleteCategory(category: Category){
        Firebase.firestore
            .collection("categories")
            .document(category.id!!)
            .delete()
            .await()

        totalCache.remove(category)
    }
}