package com.example.moonday.data.repository

import com.example.moonday.data.api.ApiClient
import com.example.moonday.data.api.FreeAstroApiService
import com.example.moonday.data.model.MoonPhaseResponse

interface MoonRepository {
    suspend fun getMoonPhase(date: String? = null): Result<MoonPhaseResponse>
}

class MoonRepositoryImpl(
    private val apiService: FreeAstroApiService = ApiClient.service
) : MoonRepository {

    override suspend fun getMoonPhase(date: String?): Result<MoonPhaseResponse> {
        return try {
            val response = apiService.getMoonPhase(date = date)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
