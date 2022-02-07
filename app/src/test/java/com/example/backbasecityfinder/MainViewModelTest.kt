package com.example.backbasecityfinder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.backbasecityfinder.data.remote.dto.City
import com.example.backbasecityfinder.data.remote.dto.Coord
import com.example.backbasecityfinder.domain.interactors.GetCitiesUserCase
import com.example.backbasecityfinder.domain.interactors.GetFilteredCitiesUserCase
import com.example.backbasecityfinder.domain.model.CityDomainModel
import com.example.backbasecityfinder.domain.repository.CityRepository
import com.example.backbasecityfinder.ui.MainViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.Assert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private lateinit var viewModel: MainViewModel

    @MockK
    lateinit var cityRepository: CityRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = MainViewModel(
            GetCitiesUserCase(
                cityRepository
            ),
            GetFilteredCitiesUserCase(
                cityRepository
            )
        )
        mockUseCaseExecuteCalls()
        mockObserverOnChangedCalls()

        observeAllInViewModel(viewModel)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when request city code, should load loader should display first Then result should display`() {
        val capturingSlot1 = slot<List<CityDomainModel>>()
        viewModel.cityCodeRequest.postValue(Unit)
        verifySequence {
            runBlocking {
                launch {
                    shouldShowLoaderObserver.onChanged(capture(shouldShowLoaderResult))
                    // Success signal should fire
                    onConfirmSuccessObserver.onChanged(capture(onConfirmSuccessResult))
                }
            }
        }

        verify { onConfirmSuccessObserver.onChanged(capture(capturingSlot1)) }
        Assert.assertEquals(
            listOf(
                CityDomainModel("Singapore, Singapore", 0.0, 0.0)
            ),
            capturingSlot1.captured
        )
    }

    // Outputs
    // Loader
    private val shouldShowLoaderObserver = mockk<Observer<Boolean>>()
    private val shouldShowLoaderResult = slot<Boolean>()

    // API
    private val onConfirmSuccessObserver =
        mockk<Observer<List<CityDomainModel>>>()
    private val onConfirmSuccessResult = slot<List<CityDomainModel>>()

    private fun mockUseCaseExecuteCalls() {
        coEvery { cityRepository.getCities() } coAnswers {
            delay(10)
            listOf(
                City(
                    "Singapore",
                    "Singapore",
                    1,
                    Coord(0.0, 0.0)
                )
            )
        }
    }

    private fun observeAllInViewModel(viewModel: MainViewModel) {
        viewModel.cityCodeLoading.observeForever(shouldShowLoaderObserver)
        viewModel.cityCode.observeForever(onConfirmSuccessObserver)
    }

    private fun mockObserverOnChangedCalls() {
        every { shouldShowLoaderObserver.onChanged(any()) } returns Unit
        every { onConfirmSuccessObserver.onChanged(any()) } returns Unit
    }

    @After
    fun tearDown() {
        clearAllMocks()
        unmockkAll()
    }
}

