package com.example.budetbybrock.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.budetbybrock.ui.models.Transaction
import com.example.budetbybrock.ui.screens.*
import com.example.budetbybrock.ui.viewmodels.RootViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RootNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val viewModel: RootViewModel = viewModel()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (currentDestination?.hierarchy?.none { it.route == Routes.launchNavigation.route || it.route == Routes.splashScreen.route } == true) {
                TopAppBar {
                    if (currentDestination.route == Routes.budget.route ){
                        IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    } else {
                        IconButton(onClick = {navController.popBackStack()}) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                }
            }
        },
        drawerContent = {
            if (currentDestination?.hierarchy?.none { it.route == Routes.launchNavigation.route || it.route == Routes.splashScreen.route } == true) {
                Text(
                    text = viewModel.getUserEmail().toString(),
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.h5
                )
                DropdownMenuItem(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.snapTo(DrawerValue.Closed)
                    }
                    navController.navigate(Routes.categoryNavigation.route) {
                    }
                }) {
                    Icon(Icons.Default.Add, "Categories")
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Categories")
                }
                DropdownMenuItem(onClick = {
                    viewModel.signOutUser()
                    scope.launch {
                        scaffoldState.drawerState.snapTo(DrawerValue.Closed)
                    }
                    navController.navigate(Routes.launchNavigation.route) {
                        popUpTo(0) // clear back stack and basically start the app from scratch
                    }
                }) {
                    Icon(Icons.Default.ExitToApp, "Logout")
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Logout")
                }
            }
        },
        floatingActionButton = {
            if (currentDestination?.route == "transactions?type={type}") {
                FloatingActionButton(onClick = { navController.navigate(Routes.editTransaction.route) }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add transaction"
                    )
                }
            }
            if (currentDestination?.route == Routes.categories.route){
                FloatingActionButton(onClick = { navController.navigate(Routes.editCategory.route) }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add category"
                    )
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.splashScreen.route,
            modifier = Modifier.padding(paddingValues = it)
        ) {
            navigation(
                route = Routes.launchNavigation.route,
                startDestination = Routes.launch.route
            ) {
                composable(route = Routes.launch.route) { LaunchScreen(navController) }
                composable(route = Routes.signIn.route) { SignInScreen(navController) }
                composable(route = Routes.signUp.route) { SignUpScreen(navController) }
            }
            navigation(
                route = Routes.budgetNavigation.route,
                startDestination = Routes.budget.route
            ) {
                composable(route = Routes.budget.route) { BudgetScreen(navController) }
            }
            navigation(
                route = Routes.transactionNavigation.route,
                startDestination = Routes.transactions.route
            ){
                composable(
                    route = "transactions?type={type}",
                    arguments = listOf(navArgument("type"){defaultValue = Transaction.INCOME})
                ){ navBackStackEntry ->
                    TransactionsScreen(navController, Integer.parseInt(navBackStackEntry.arguments?.get("type").toString()))
                }
                composable(
                    route = "edittransaction?id={id}",
                    arguments = listOf(navArgument("id") { defaultValue = "new" })
                ) { navBackStackEntry ->
                    TransactionModificationScreen(navController, navBackStackEntry.arguments?.get("id").toString())
                }
            }
            navigation(
                route = Routes.categoryNavigation.route,
                startDestination = Routes.categories.route
            ){
                composable(route = Routes.categories.route) { CategoriesScreen(navController)}
                composable(
                    route = "editcategory?id={id}",
                    arguments = listOf(navArgument("id"){defaultValue = "new"})
                ) { navBackStackEntry ->
                    CategoryModificationScreen(navController, navBackStackEntry.arguments?.get("id").toString())
                }
            }
            composable(route = Routes.splashScreen.route) { SplashScreen(navController) }
        }
    }
}