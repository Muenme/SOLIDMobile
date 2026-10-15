package com.example.solidmobile.usecase

import com.example.solidmobile.domain.model.Fruit
import com.example.solidmobile.domain.model.Nutritions
import com.example.solidmobile.domain.usecase.CalculateSaladCaloriesUseCase
import com.example.solidmobile.domain.usecase.FakeFruitRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CalculateSaladCaloriesUseCaseTest {

    private val fakeRepository = FakeFruitRepository()
    private val useCase = CalculateSaladCaloriesUseCase(fakeRepository)

    @Before
    fun setUp() {
        fakeRepository.setNutritions(
            listOf(
                Nutritions(id = 1, carbohydrates = 10.0, protein = 1.0, fat = 0.3, calories = 52.0, sugar = 10.0),
                Nutritions(id = 2, carbohydrates = 20.0, protein = 2.0, fat = 0.5, calories = 95.0, sugar = 19.0),
                Nutritions(id = 3, carbohydrates = 15.0, protein = 1.5, fat = 0.2, calories = 60.0, sugar = 12.0)
            )
        )
    }

    private fun fruit(id: Int, name: String, nutritionsId: Int) =
        Fruit(id = id, genus = "genus", name = name, family = "family", order = "order", nutritionsId = nutritionsId)

    @Test
    fun `пустой список фруктов — калории равны нулю`() = runTest {
        val result = useCase(emptyList())

        assertEquals(0.0, result, 0.0)
    }

    @Test
    fun `один фрукт — калории равны калориям его nutrition`() = runTest {
        val fruits = listOf(fruit(1, "Apple", nutritionsId = 1))

        val result = useCase(fruits)

        assertEquals(52.0, result, 0.0)
    }

    @Test
    fun `несколько фруктов — калории суммируются`() = runTest {
        val fruits = listOf(
            fruit(1, "Apple", nutritionsId = 1),   // 52.0
            fruit(2, "Banana", nutritionsId = 2),  // 95.0
            fruit(3, "Cherry", nutritionsId = 3)   // 60.0
        )

        val result = useCase(fruits)

        assertEquals(207.0, result, 0.0)
    }

    @Test
    fun `фрукт без nutrition считается как ноль, а не падает`() = runTest {
        val fruits = listOf(
            fruit(1, "Apple", nutritionsId = 1),     // 52.0
            fruit(2, "Unknown", nutritionsId = 999)  // nutrition нет -> 0.0
        )

        val result = useCase(fruits)

        assertEquals(52.0, result, 0.0)
    }

    @Test
    fun `все фрукты без nutrition — итог ноль`() = runTest {
        val fruits = listOf(
            fruit(1, "A", nutritionsId = 100),
            fruit(2, "B", nutritionsId = 200)
        )

        val result = useCase(fruits)

        assertEquals(0.0, result, 0.0)
    }

    @Test
    fun `несколько фруктов с одинаковым nutritionsId считаются отдельно, а не дедуплицируются`() = runTest {
        val fruits = listOf(
            fruit(1, "Apple1", nutritionsId = 1), // 52.0
            fruit(2, "Apple2", nutritionsId = 1)  // 52.0 ещё раз
        )

        val result = useCase(fruits)

        assertEquals(104.0, result, 0.0)
    }

    @Test
    fun `use case обращается к репозиторию для каждого фрукта`() = runTest {
        val fruits = listOf(
            fruit(1, "Apple", nutritionsId = 1),
            fruit(2, "Banana", nutritionsId = 2),
            fruit(3, "Cherry", nutritionsId = 3)
        )

        useCase(fruits)

        assertEquals(3, fakeRepository.getNutritionsCallCount)
    }
}