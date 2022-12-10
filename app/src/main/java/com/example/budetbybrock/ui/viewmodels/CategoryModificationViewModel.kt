package com.example.budetbybrock.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.budetbybrock.ui.models.Category
import com.example.budetbybrock.ui.models.Transaction
import com.example.budetbybrock.ui.repositories.CategoryRepository
import com.example.budetbybrock.ui.screens.CategoryModificationScreen

class CategoryModificationScreenState{
    var type by mutableStateOf(Transaction.INCOME)
    var name by mutableStateOf("")
    var nameError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var saveSuccess by mutableStateOf(false)
    var typeDropdownExpanded by mutableStateOf(false)
}

class CategoryModificationViewModel(application: Application): AndroidViewModel(application) {
    val uiState = CategoryModificationScreenState()
    var id: String? = null

    suspend fun saveCategory(){
        uiState.errorMessage = ""
        uiState.nameError = false

        if (uiState.name.isEmpty()){
            uiState.nameError = true
            uiState.errorMessage = "Name cannot be blank"
            return
        }

        if (id == null){
            CategoryRepository.createCategory(
                uiState.type,
                uiState.name
            )
        }
        else{
            val category = CategoryRepository.getCategories().find { it.id == id } ?: return
            CategoryRepository.updateCategory(
                category.copy(
                    type = uiState.type,
                    name = uiState.name
                )
            )
        }

        uiState.saveSuccess = true
    }

    suspend fun setUpInitialState(id: String?){
        if (id == null || id == "new") return
        this.id = id
        val category = CategoryRepository.getCategories().find { it.id == id } ?: return

        uiState.type = category.type ?: Transaction.INCOME
        uiState.name = category.name ?: ""
    }
}