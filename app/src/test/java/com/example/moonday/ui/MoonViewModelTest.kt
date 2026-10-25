package com.example.moonday.ui

import com.example.moonday.data.model.MoonPhaseResponse
import com.example.moonday.data.model.MoonVisualInfo
import com.example.moonday.data.model.PhaseInfo
import com.example.moonday.data.repository.MoonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MoonViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private class FakeMoonRepository(
        private val shouldFail: Boolean = false
    ) : MoonRepository {
        override suspend fun getMoonPhase(date: String?): Result<MoonPhaseResponse> {
            return if (shouldFail) {
                Result.failure(Exception("Network error"))
            } else {
                Result.success(
                    MoonPhaseResponse(
                        timestamp = date ?: "2026-04-15",
                        phase = PhaseInfo(name = "Waning Gibbous", illumination = 0.85),
                        moonVisual = MoonVisualInfo(svg = "<svg>moon</svg>")
                    )
                )
            }
        }
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchMoonPhase updates uiState to Success when repository returns data`() = runTest {
        val repository = FakeMoonRepository(shouldFail = false)
        val viewModel = MoonViewModel(repository)

        viewModel.fetchMoonPhase("2026-04-15")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is MoonUiState.Success)
        val successState = state as MoonUiState.Success
        assertEquals("Waning Gibbous", successState.response.phase?.name)
        assertEquals("<svg>moon</svg>", successState.response.moonVisual?.svg)
    }

    @Test
    fun `fetchMoonPhaseForDate formats date correctly and updates uiState`() = runTest {
        val repository = FakeMoonRepository(shouldFail = false)
        val viewModel = MoonViewModel(repository)

        viewModel.fetchMoonPhaseForDate(year = 2026, month = 4, dayOfMonth = 5)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is MoonUiState.Success)
        val successState = state as MoonUiState.Success
        assertEquals("2026-04-05", successState.response.timestamp)
    }

    @Test
    fun `fetchMoonPhase updates uiState to Error when repository fails`() = runTest {
        val repository = FakeMoonRepository(shouldFail = true)
        val viewModel = MoonViewModel(repository)

        viewModel.fetchMoonPhase("2026-04-15")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is MoonUiState.Error)
        val errorState = state as MoonUiState.Error
        assertEquals("Network error", errorState.message)
    }
}
