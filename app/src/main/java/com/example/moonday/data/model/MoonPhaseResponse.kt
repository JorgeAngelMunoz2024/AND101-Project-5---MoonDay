package com.example.moonday.data.model

import com.google.gson.annotations.SerializedName

data class MoonPhaseResponse(
    @SerializedName("timestamp") val timestamp: String? = null,
    @SerializedName("phase") val phase: PhaseInfo? = null,
    @SerializedName("next_phases") val nextPhases: NextPhasesInfo? = null,
    @SerializedName("zodiac") val zodiac: ZodiacInfo? = null,
    @SerializedName("moon_visual") val moonVisual: MoonVisualInfo? = null,
    @SerializedName("special_moon") val specialMoon: SpecialMoonInfo? = null,
    @SerializedName("traditional_moon") val traditionalMoon: TraditionalMoonInfo? = null,
    @SerializedName("interpretation") val interpretation: InterpretationInfo? = null
)

data class PhaseInfo(
    @SerializedName("name") val name: String? = null,
    @SerializedName("phase_angle_deg") val phaseAngleDeg: Double? = null,
    @SerializedName("illumination") val illumination: Double? = null,
    @SerializedName("age_days") val ageDays: Double? = null,
    @SerializedName("distance_km") val distanceKm: Long? = null,
    @SerializedName("is_waxing") val isWaxing: Boolean? = null
)

data class NextPhasesInfo(
    @SerializedName("new_moon") val newMoon: String? = null,
    @SerializedName("first_quarter") val firstQuarter: String? = null,
    @SerializedName("full_moon") val fullMoon: String? = null,
    @SerializedName("last_quarter") val lastQuarter: String? = null
)

data class ZodiacInfo(
    @SerializedName("sign") val sign: String? = null,
    @SerializedName("sign_id") val signId: String? = null,
    @SerializedName("degree") val degree: Double? = null,
    @SerializedName("zodiac_type") val zodiacType: String? = null
)

data class MoonVisualInfo(
    @SerializedName("type") val type: String? = null,
    @SerializedName("svg") val svg: String? = null,
    @SerializedName("shadow_ratio") val shadowRatio: Double? = null,
    @SerializedName("waxing") val waxing: Boolean? = null
)

data class SpecialMoonInfo(
    @SerializedName("is_supermoon") val isSupermoon: Boolean? = null,
    @SerializedName("is_micromoon") val isMicromoon: Boolean? = null,
    @SerializedName("is_blue_moon") val isBlueMoon: Boolean? = null,
    @SerializedName("is_black_moon") val isBlackMoon: Boolean? = null,
    @SerializedName("is_harvest_moon") val isHarvestMoon: Boolean? = null,
    @SerializedName("is_hunter_moon") val isHunterMoon: Boolean? = null,
    @SerializedName("labels") val labels: List<String>? = null
)

data class TraditionalMoonInfo(
    @SerializedName("name") val name: String? = null,
    @SerializedName("naming_system") val namingSystem: String? = null,
    @SerializedName("month") val month: String? = null,
    @SerializedName("applies_to_full_moon_at") val appliesToFullMoonAt: String? = null,
    @SerializedName("is_current_full_moon") val isCurrentFullMoon: Boolean? = null
)

data class InterpretationInfo(
    @SerializedName("title") val title: String? = null,
    @SerializedName("body") val body: String? = null,
    @SerializedName("tone") val tone: String? = null,
    @SerializedName("tags") val tags: List<String>? = null
)
