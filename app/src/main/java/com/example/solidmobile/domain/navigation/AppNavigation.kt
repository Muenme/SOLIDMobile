package com.example.solidmobile.domain.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import com.example.solidmobile.presentation.screen.CreateSaladScreen
import com.example.solidmobile.presentation.screen.FruitDetailsScreen
import com.example.solidmobile.presentation.screen.FruitPickerDialog
import com.example.solidmobile.presentation.screen.SaladsScreen
import com.example.solidmobile.ui.screen.salad_details.SaladDetailsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Salads.route
    ) {

        composable(Screen.Salads.route) {
            SaladsScreen(
                onCreateSaladClick = {
                    navController.navigate(Screen.CreateSalad.route)
                },
                onSaladClick = { saladId ->
                    navController.navigate(
                        Screen.SaladDetails.createRoute(saladId)
                    )
                }
            )
        }

        composable(Screen.CreateSalad.route) {
            CreateSaladScreen (
                onBackClick = {
                    navController.navigateUp()
                },
                onChooseFruitsClick = {
                    navController.navigate(Screen.FruitPicker.route)
                },
                onSaveClick = { saladId ->
                    navController.navigate(
                        Screen.SaladDetails.createRoute(saladId)
                    ) {
                        popUpTo(Screen.Salads.route)
                    }
                }
            )
        }

        dialog(Screen.FruitPicker.route) {
            FruitPickerDialog(
                onDismiss = {
                    navController.navigateUp()
                },
                onFruitClick = { fruitId ->
                    navController.navigate(
                        Screen.FruitDetails.createRoute(fruitId)
                    )
                }
            )
        }

        composable(
            route = Screen.FruitDetails.route,
            arguments = listOf(
                navArgument(Screen.FruitDetails.getFruitIdArgument()) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val fruitId = backStackEntry.arguments?.getInt(
                Screen.FruitDetails.getFruitIdArgument()
            ) ?: return@composable

            FruitDetailsScreen (
                fruitId = fruitId,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        composable(
            route = Screen.SaladDetails.route,
            arguments = listOf(
                navArgument(Screen.SaladDetails.getSaladIdArgument()) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val saladId = backStackEntry.arguments?.getInt(
                Screen.SaladDetails.getSaladIdArgument()
            ) ?: return@composable

            SaladDetailsScreen(
                saladId = saladId,
                onBackClick = {
                    navController.navigateUp()
                },
                onFruitClick = { fruitId ->
                    navController.navigate(
                        Screen.FruitDetails.createRoute(fruitId)
                    )
                }
            )
        }
    }
}