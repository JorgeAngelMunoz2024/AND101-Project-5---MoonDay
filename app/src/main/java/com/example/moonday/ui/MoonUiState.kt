package com.example.moonday.ui

import com.example.moonday.data.model.MoonPhaseResponse

sealed interface MoonUiState {
    object Idle : MoonUiState
    object Loading : MoonUiState
    data class Success(val response: MoonPhaseResponse) : MoonUiState
    data class Error(val message: String) : MoonUiState
}
