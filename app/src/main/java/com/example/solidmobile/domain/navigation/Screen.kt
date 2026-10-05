package com.example.solidmobile.domain.navigation

sealed class Screen(val route: String) {

    data object Salads : Screen("salads")

    data object CreateSalad : Screen("create_salad")

    data object SaladDetails : Screen("salad_details/{saladId}") {
        private const val SALAD_ID_ARG = "saladId"

        fun createRoute(saladId: Int): String {
            return "salad_details/$saladId"
        }

        fun getSaladIdArgument(): String = SALAD_ID_ARG
    }

    data object FruitPicker : Screen("fruit_picker")

    data object FruitDetails : Screen("fruit_details/{fruitId}") {
        private const val FRUIT_ID_ARG = "fruitId"

        fun createRoute(fruitId: Int): String {
            return "fruit_details/$fruitId"
        }

        fun getFruitIdArgument(): String = FRUIT_ID_ARG
    }
}