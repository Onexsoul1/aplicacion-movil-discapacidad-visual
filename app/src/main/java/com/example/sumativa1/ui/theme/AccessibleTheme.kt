package com.example.sumativa1.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.sumativa1.model.AccessibilityPrefs
import com.example.sumativa1.model.VisualProfile

// Paleta para Miopía: alto contraste, texto claro sobre fondo oscuro
private val MyopiaScheme = darkColorScheme(
    primary = Color(0xFF0EA5E9),   // azul claro legible
    onPrimary = Color(0xFF001018),
    secondary = Color(0xFF22C55E), // verde accesible (no puro)
    onSecondary = Color(0xFF00150A),
    background = Color(0xFF0B0B0D),
    onBackground = Color(0xFFF2F3F5),
    surface = Color(0xFF131316),
    onSurface = Color(0xFFF2F3F5)
)

// Paleta para Daltonismo: evita combinaciones rojo/verde, usa azul/naranja y alto contraste
private val DaltonismScheme = darkColorScheme(
    primary = Color(0xFF1D4ED8),   // azul fuerte
    onPrimary = Color.White,
    secondary = Color(0xFFEA580C), // naranja
    onSecondary = Color.White,
    background = Color(0xFF0E1116),
    onBackground = Color(0xFFECEFF4),
    surface = Color(0xFF151922),
    onSurface = Color(0xFFECEFF4)
)

// Paleta para otras condiciones
private val OtherScheme = darkColorScheme(
    primary = Color(0xFF7C3AED),   // violeta
    onPrimary = Color.White,
    secondary = Color(0xFF06B6D4), // cian
    onSecondary = Color.Black,
    background = Color(0xFF0D0F12),
    onBackground = Color(0xFFF5F7FA),
    surface = Color(0xFF15181D),
    onSurface = Color(0xFFF5F7FA)
)

@Composable
fun AccessibleTheme(prefs: AccessibilityPrefs, content: @Composable () -> Unit) {
    val scheme = when (prefs.profile) {
        VisualProfile.DALTONISMO -> DaltonismScheme
        VisualProfile.MIOPIA -> MyopiaScheme
        VisualProfile.OTRO -> OtherScheme
    }

    val s = when (prefs.profile) {
        VisualProfile.DALTONISMO -> 1.2f
        else -> prefs.fontScale.coerceIn(1.3f, 2.2f)
    }

    val base = Typography()
    val typos = Typography(
        displayLarge = base.displayLarge.copy(fontSize = base.displayLarge.fontSize * s),
        titleLarge   = base.titleLarge.copy(fontSize   = base.titleLarge.fontSize   * s),
        bodyLarge    = base.bodyLarge.copy(fontSize    = base.bodyLarge.fontSize    * s),
        labelLarge   = base.labelLarge.copy(fontSize   = base.labelLarge.fontSize   * s)
    )

    MaterialTheme(colorScheme = scheme, typography = typos, content = content)
}
