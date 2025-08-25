package com.example.sumativa1.model

enum class VisualProfile { MIOPIA, DALTONISMO, OTRO }

data class AccessibilityPrefs(
    val profile: VisualProfile = VisualProfile.MIOPIA,
    val fontScale: Float = 1.4f,
    val highContrast: Boolean = true,
    val colorBlindSafe: Boolean = true
)
