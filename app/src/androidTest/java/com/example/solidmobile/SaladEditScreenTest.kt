package com.example.solidmobile.presentation.salad_edit

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import com.example.solidmobile.domain.model.Fruit
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class SaladEditScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val fruit = Fruit(
        id = 1,
        name = "Банан",
        family = "Musaceae",
        genus = "Musa",
        order = "MUMU",
        nutritionsId = 1
    )

    private fun setContent(
        state: SaladEditUiState = SaladEditUiState(
            name = "",
            nameError = null,
            selectedFruits = listOf(fruit),
            totalCalories = 95.0,
            isSaving = false,
            isEditing = false,
            errorMessage = null
        ),
        onNameChange: (String) -> Unit = {},
        onAddFruitClick: () -> Unit = {},
        onRemoveFruitClick: (Int) -> Unit = {},
        onSaveClick: () -> Unit = {},
    ) {
        composeTestRule.setContent {
            SaladEditContent(
                uiState = state,
                onNameChange = onNameChange,
                onAddFruitClick = onAddFruitClick,
                onRemoveFruitClick = onRemoveFruitClick,
                onSaveClick = onSaveClick
            )
        }
    }

    @Test
    fun enteringSaladNameTriggersCallback() {
        var enteredName = ""
        setContent(onNameChange = { enteredName = it })

        composeTestRule.onNodeWithTag("salad_name_input").performTextInput("Фруктовый")

        assertEquals("Фруктовый", enteredName)
    }

    @Test
    fun nameErrorIsDisplayed() {
        setContent(
            state = SaladEditUiState(
                name = "",
                nameError = "Введите название",
                selectedFruits = emptyList(),
                totalCalories = 0.0,
                isSaving = false,
                isEditing = false,
                errorMessage = null
            )
        )

        composeTestRule.onNodeWithText("Введите название").assertIsDisplayed()
    }

    @Test
    fun totalCaloriesDisplayedCorrectly() {
        setContent(
            state = SaladEditUiState(
                name = "Салат",
                nameError = null,
                selectedFruits = listOf(fruit),
                totalCalories = 95.0,
                isSaving = false,
                isEditing = false,
                errorMessage = null
            )
        )

        composeTestRule.onNodeWithText("Общая калорийность: 95 ккал").assertIsDisplayed()
    }

    @Test
    fun selectedFruitNameIsDisplayedInList() {
        setContent()

        composeTestRule.onNodeWithText("Банан").assertIsDisplayed()
    }

    @Test
    fun addFruitClickTriggersCallback() {
        var called = false
        setContent(onAddFruitClick = { called = true })

        composeTestRule.onNodeWithTag("add_fruit_button").performClick()

        assertTrue(called)
    }

    @Test
    fun removeFruitClickTriggersCallbackWithId() {
        var removedId: Int? = null
        setContent(onRemoveFruitClick = { removedId = it })

        composeTestRule.onNodeWithTag("remove_fruit_button_1").performClick()

        assertEquals(1, removedId)
    }

    @Test
    fun emptyFruitListShowsPlaceholder() {
        setContent(
            state = SaladEditUiState(
                name = "",
                nameError = null,
                selectedFruits = emptyList(),
                totalCalories = 0.0,
                isSaving = false,
                isEditing = false,
                errorMessage = null
            )
        )

        composeTestRule
            .onNodeWithText("Фрукты пока не выбраны. Нажмите «Добавить фрукт».")
            .assertIsDisplayed()
    }

    @Test
    fun saveButtonClickTriggersCallback() {
        var saved = false
        setContent(onSaveClick = { saved = true })

        composeTestRule.onNodeWithTag("save_button").performClick()

        assertTrue(saved)
    }

    @Test
    fun saveButtonShowsCreateTextForNewSalad() {
        setContent(
            state = SaladEditUiState(
                name = "Новый",
                nameError = null,
                selectedFruits = emptyList(),
                totalCalories = 0.0,
                isSaving = false,
                isEditing = false,
                errorMessage = null
            )
        )

        composeTestRule.onNodeWithText("Создать салат").assertIsDisplayed()
    }

    @Test
    fun saveButtonShowsSaveChangesTextForEditing() {
        setContent(
            state = SaladEditUiState(
                name = "Существующий",
                nameError = null,
                selectedFruits = emptyList(),
                totalCalories = 0.0,
                isSaving = false,
                isEditing = true,
                errorMessage = null
            )
        )

        composeTestRule.onNodeWithText("Сохранить изменения").assertIsDisplayed()
    }

    @Test
    fun savingStateShowsLoadingIndicatorInButton() {
        setContent(
            state = SaladEditUiState(
                name = "Салат",
                nameError = null,
                selectedFruits = emptyList(),
                totalCalories = 0.0,
                isSaving = true,
                isEditing = false,
                errorMessage = null
            )
        )

        composeTestRule.onNodeWithText("Создать салат").assertDoesNotExist()
        composeTestRule.onNodeWithText("Сохранить изменения").assertDoesNotExist()
    }

    @Test
    fun errorMessageIsDisplayed() {
        setContent(
            state = SaladEditUiState(
                name = "Салат",
                nameError = null,
                selectedFruits = emptyList(),
                totalCalories = 0.0,
                isSaving = false,
                isEditing = false,
                errorMessage = "Не удалось сохранить салат"
            )
        )

        composeTestRule.onNodeWithText("Не удалось сохранить салат").assertIsDisplayed()
    }
}