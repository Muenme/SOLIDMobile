package com.example.solidmobile.viewmodel

import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.presentation.fruit_picker.FruitPickerViewModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FruitPickerViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeRepository: FakeFruitRepository

    private fun fruit(id: Int, name: String) =
        Fruit(id = id, genus = "genus", name = name, family = "family", order = "order", nutritionsId = id)

    @Before
    fun setUp() {
        fakeRepository = FakeFruitRepository()
    }

    @Test
    fun `успешная загрузка — fruits заполнены, isLoading = false, errorMessage = null`() = runTest {
        val fruits = listOf(fruit(1, "Apple"), fruit(2, "Banana"))
        fakeRepository.setFruits(fruits)

        val viewModel = FruitPickerViewModel(fakeRepository)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(fruits, state.fruits)
        assertNull(state.errorMessage)
    }

    @Test
    fun `пустой список фруктов — fruits пустой, ошибки нет`() = runTest {
        fakeRepository.setFruits(emptyList())

        val viewModel = FruitPickerViewModel(fakeRepository)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertTrue(state.fruits.isEmpty())
        assertNull(state.errorMessage)
    }

    @Test
    fun `ошибка репозитория — errorMessage содержит текст исключения`() = runTest {
        fakeRepository.shouldThrow = true
        fakeRepository.exceptionToThrow = RuntimeException("Нет сети")

        val viewModel = FruitPickerViewModel(fakeRepository)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals("Нет сети", state.errorMessage)
        assertTrue(state.fruits.isEmpty())
    }

    @Test
    fun `ошибка без message — используется дефолтный текст`() = runTest {
        fakeRepository.shouldThrow = true
        fakeRepository.exceptionToThrow = RuntimeException() // message == null

        val viewModel = FruitPickerViewModel(fakeRepository)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals("Не удалось загрузить список фруктов", state.errorMessage)
    }

    @Test
    fun `повторный вызов loadFruits сбрасывает предыдущую ошибку`() = runTest {
        fakeRepository.shouldThrow = true
        val viewModel = FruitPickerViewModel(fakeRepository)
        advanceUntilIdle()

        assertEquals(
            fakeRepository.exceptionToThrow.message ?: "Не удалось загрузить список фруктов",
            viewModel.uiState.value.errorMessage
        )

        // Чиним репозиторий и перезагружаем
        fakeRepository.shouldThrow = false
        fakeRepository.setFruits(listOf(fruit(1, "Apple")))

        viewModel.loadFruits()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertNull(state.errorMessage)
        assertEquals(1, state.fruits.size)
    }

    @Test
    fun `loadFruits можно вызывать несколько раз подряд`() = runTest {
        fakeRepository.setFruits(listOf(fruit(1, "Apple")))
        val viewModel = FruitPickerViewModel(fakeRepository)
        advanceUntilIdle()

        fakeRepository.setFruits(listOf(fruit(1, "Apple"), fruit(2, "Banana")))
        viewModel.loadFruits()
        advanceUntilIdle()

        fakeRepository.setFruits(listOf(fruit(3, "Cherry")))
        viewModel.loadFruits()
        advanceUntilIdle()

        assertEquals(listOf(fruit(3, "Cherry")), viewModel.uiState.value.fruits)
    }
}