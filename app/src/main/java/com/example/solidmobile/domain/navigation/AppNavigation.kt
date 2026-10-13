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
import com.example.solidmobile.presentation.fruit_details.FruitDetailsScreen
import com.example.solidmobile.presentation.fruit_picker.FruitPickerDialog
import com.example.solidmobile.presentation.salad_edit.SaladEditScreen
import com.example.solidmobile.ui.screen.salad_details.SaladDetailsScreen
import com.example.solidmobile.ui.screen.salads.SaladsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Salads.route
    ) {
        composable(
            route = Screen.SaladEdit.route,
            arguments = listOf(
                navArgument("saladId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val saladId = backStackEntry.arguments
                ?.getInt("saladId")
                ?: Screen.SaladEdit.NEW_SALAD

            SaladEditScreen(
                saladId = saladId,
                onBackClick = {
                    navController.navigateUp()
                },
                onFruitDetailsClick = { fruitId ->
                    navController.navigate(
                        Screen.FruitDetails.createRoute(fruitId)
                    )
                },
                onSavedClick = { savedSaladId ->
                    navController.navigate(
                        Screen.SaladDetails.createRoute(savedSaladId)
                    ) {
                        popUpTo(Screen.Salads.route)
                    }
                }
            )
        }

        composable(Screen.Salads.route) {
            SaladsScreen(
                onCreateSaladClick = {
                    navController.navigate(Screen.SaladEdit.createRoute(
                        Screen.SaladEdit.NEW_SALAD
                        )
                    )
                },
                onSaladClick = { saladId ->
                    navController.navigate(
                        Screen.SaladDetails.createRoute(saladId)
                    )
                }
            )
        }

//        dialog(Screen.FruitPicker.route) {
//            FruitPickerDialog (
//                onDismiss = {
//                    navController.navigateUp()
//                },
//                onFruitClick = { fruitId ->
//                    navController.navigate(
//                        Screen.FruitDetails.createRoute(fruitId)
//                    )
//                },
//                onAddFruitClick =
//            )
//        }

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