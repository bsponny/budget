package com.example.budetbybrock.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.budetbybrock.ui.models.Category
import com.example.budetbybrock.ui.models.Transaction
import com.example.budetbybrock.ui.repositories.CategoryRepository
import com.example.budetbybrock.ui.screens.CategoryModificationScreen

class CategoryScreenState{
    val _categories = mutableStateListOf<Category>()
    val categories: List<Category> get() = _categories
    var loading by mutableStateOf(true)
}

class CategoryViewModel(application: Application): AndroidViewModel(application) {
    val uiState = CategoryScreenState()

    suspend fun getCategories(){
        uiState._categories.clear()
        val categories = CategoryRepository.getCategories()
        uiState._categories.addAll(categories)
    }

    suspend fun deleteCategory(category: Category){
        CategoryRepository.deleteCategory(category)
        uiState._categories.remove(category)
    }
}