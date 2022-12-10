package com.example.budetbybrock.uitl

fun typeText(type: Int) = when(type) {
    0 -> "Income"
    1 -> "Expense"
    else -> ""
}