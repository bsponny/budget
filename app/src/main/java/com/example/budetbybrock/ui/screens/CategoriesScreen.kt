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
import com.example.budetbybrock.ui.viewmodels.CategoryViewModel
import com.example.budetbybrock.ui.components.CategoryItem
import com.example.budetbybrock.ui.viewmodels.TransactionViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CategoriesScreen(navHostController: NavHostController) {
    val viewModel: CategoryViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()

    LaunchedEffect(true){
        val loadCategories = async { viewModel.getCategories() }
        delay(1000)
        loadCategories.await()
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
                items(state.categories, key = { it.id!! }) { category ->
                    CategoryItem(
                        category = category,
                        onEditPressed = {navHostController.navigate("editcategory?id=${category.id}")},
                        onDeletePressed = {scope.launch { viewModel.deleteCategory(category) }}
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}