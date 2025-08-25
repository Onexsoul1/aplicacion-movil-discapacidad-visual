package com.example.sumativa1.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.sumativa1.model.AccessibilityPrefs
import com.example.sumativa1.model.VisualProfile
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("a11y_prefs")

object AccessibilityStore {
    private val KEY_PROFILE = stringPreferencesKey("profile")
    private val KEY_FONT = floatPreferencesKey("font_scale")
    private val KEY_CONTRAST = booleanPreferencesKey("high_contrast")
    private val KEY_CBLIND = booleanPreferencesKey("color_blind_safe")

    fun flow(context: Context) = context.dataStore.data.map { p ->
        AccessibilityPrefs(
            profile = VisualProfile.valueOf(p[KEY_PROFILE] ?: VisualProfile.MIOPIA.name),
            fontScale = p[KEY_FONT] ?: 1.4f,
            highContrast = p[KEY_CONTRAST] ?: true,
            colorBlindSafe = p[KEY_CBLIND] ?: true
        )
    }

    suspend fun save(c: Context, prefs: AccessibilityPrefs) {
        c.dataStore.edit { e ->
            e[KEY_PROFILE] = prefs.profile.name
            e[KEY_FONT] = prefs.fontScale
            e[KEY_CONTRAST] = prefs.highContrast
            e[KEY_CBLIND] = prefs.colorBlindSafe
        }
    }
}
