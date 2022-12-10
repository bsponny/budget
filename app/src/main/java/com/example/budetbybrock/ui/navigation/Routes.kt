package com.example.budetbybrock.ui.navigation

data class Screen(val route: String)

object Routes {
    val launchNavigation = Screen("launchnavigation")
    val budgetNavigation = Screen("budgetnavigation")
    val transactionNavigation = Screen("transactionnavigation")
    val launch = Screen("launch")
    val signIn = Screen("signin")
    val signUp = Screen("signup")
    val budget = Screen("budget")
    val transactions = Screen("transactions")
    val editTransaction = Screen("edittransaction")
    val splashScreen = Screen("splashscreen")

    val categoryNavigation = Screen("categorynavigation")
    val categories = Screen("categories")
    val editCategory = Screen("editcategory")
}