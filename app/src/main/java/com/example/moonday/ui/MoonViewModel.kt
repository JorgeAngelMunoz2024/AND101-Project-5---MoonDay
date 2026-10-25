package com.example.moonday.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moonday.data.repository.MoonRepository
import com.example.moonday.data.repository.MoonRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class MoonViewModel(
    private val repository: MoonRepository = MoonRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow<MoonUiState>(MoonUiState.Idle)
    val uiState: StateFlow<MoonUiState> = _uiState.asStateFlow()

    fun fetchMoonPhase(date: String? = null) {
        viewModelScope.launch {
            _uiState.value = MoonUiState.Loading
            repository.getMoonPhase(date)
                .onSuccess { response ->
                    _uiState.value = MoonUiState.Success(response)
                }
                .onFailure { exception ->
                    val errorMessage = exception.localizedMessage ?: "Failed to load moon phase data."
                    _uiState.value = MoonUiState.Error(errorMessage)
                }
        }
    }

    fun fetchMoonPhaseForDate(year: Int, month: Int, dayOfMonth: Int) {
        val formattedDate = String.format(Locale.US, "%04d-%02d-%02d", year, month, dayOfMonth)
        fetchMoonPhase(formattedDate)
    }
}
