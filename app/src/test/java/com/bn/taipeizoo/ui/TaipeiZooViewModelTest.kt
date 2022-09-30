package com.bn.taipeizoo.ui

import com.bn.taipeizoo.data.model.TaipeiZooAnimal
import com.bn.taipeizoo.data.model.TaipeiZooArea
import com.bn.taipeizoo.data.repository.TaipeiZooRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaipeiZooViewModelTest {
    private val repository = mockk<TaipeiZooRepository>()
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: TaipeiZooViewModel

    private val areaA = mockk<TaipeiZooArea>()
    private val areaB = mockk<TaipeiZooArea>()

    private val animalA = mockk<TaipeiZooAnimal>()
    private val animalB = mockk<TaipeiZooAnimal>()
    private val animalC = mockk<TaipeiZooAnimal>()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        every { areaA.name } returns "areaA"
        every { areaB.name } returns "areaB"
        every { animalA.location } returns areaA.name
        every { animalB.location } returns areaB.name
        every { animalC.location } returns areaB.name

        coEvery { repository.getTaipeiZooAnimals() } returns listOf(
            animalA, animalB, animalC
        )
        coEvery { repository.getTaipeiZooAreas() } returns listOf(
            areaA, areaB
        )

        viewModel = TaipeiZooViewModel(repository)
    }

    @Test
    fun getAreas() = runTest{
        val result = viewModel.areas.value

        coVerify { repository.getTaipeiZooAreas() }
        assertThat(result).containsExactly(areaA, areaB)
    }


    @Test
    fun `normally getting area animals`() {
        val areaAResult = viewModel.getAreaAnimals(areaA.name)
        val areaBResult = viewModel.getAreaAnimals(areaB.name)

        coVerify { repository.getTaipeiZooAnimals() }
        assertThat(areaAResult).containsExactly(animalA)
        assertThat(areaBResult).containsExactly(animalB, animalC)
    }

    @Test
    fun `getting area animals with empty area string`() {
        val result = viewModel.getAreaAnimals("")

        assertThat(result).isEmpty()
    }

    @After
    fun clearDispatcher() {
        Dispatchers.resetMain()
    }
}