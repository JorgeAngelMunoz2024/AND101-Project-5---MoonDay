package com.example.moonday.data.api

import com.example.moonday.data.model.MoonPhaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FreeAstroApiService {

    @GET("api/v1/moon/phase")
    suspend fun getMoonPhase(
        @Query("date") date: String? = null,
        @Query("include_visuals") includeVisuals: Boolean = true,
        @Query("include_zodiac") includeZodiac: Boolean = true,
        @Query("include_special") includeSpecial: Boolean = true,
        @Query("include_traditional_moon") includeTraditionalMoon: Boolean = true,
        @Query("include_interpretation") includeInterpretation: Boolean = true
    ): MoonPhaseResponse
}
