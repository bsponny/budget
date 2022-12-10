package com.example.budetbybrock.ui.models

data class Transaction (
    val id: String? = null,
    val userId: String? = null,
    val type: Int? = null,
    val date: String? = null,
    val amount: Double? = null,
    val description: String? = null
){
    companion object Type{
        const val INCOME = 0
        const val EXPENSE = 1
    }
}