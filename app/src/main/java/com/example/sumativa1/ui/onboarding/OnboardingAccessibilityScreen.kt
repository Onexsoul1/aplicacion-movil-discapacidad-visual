package com.example.sumativa1.ui.onboarding

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sumativa1.data.AccessibilityStore
import com.example.sumativa1.model.AccessibilityPrefs
import com.example.sumativa1.model.VisualProfile
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingAccessibilityScreen(nav: NavController) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()

    var profile by remember { mutableStateOf(VisualProfile.MIOPIA) }
    var fontScale by remember { mutableStateOf(1.8f) }      // más grande por defecto
    val tts = remember { TextToSpeech(ctx) { } }
    DisposableEffect(Unit) { onDispose { tts.shutdown() } }
    fun speak(x: String) { tts.language = Locale("es","CL"); tts.speak(x, TextToSpeech.QUEUE_FLUSH, null, "a11y") }

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Configura tu visión") }) }
    ) { inner ->
        Column(
            Modifier.fillMaxSize().padding(inner).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Tarjetas grandes y fáciles de tocar (alto de 84dp, radio grande)
            listOf(
                "Miopía" to VisualProfile.MIOPIA,
                "Daltonismo" to VisualProfile.DALTONISMO,
                "Otra condición visual" to VisualProfile.OTRO
            ).forEach { (label, value) ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(84.dp)
                        .semantics { contentDescription = "Opción $label" }
                        .selectable(selected = profile == value, onClick = {
                            profile = value; speak(label)
                        }),
                    colors = CardDefaults.elevatedCardColors()
                ) {
                    Row(
                        Modifier.fillMaxSize().padding(horizontal = 18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = profile == value, onClick = {
                            profile = value; speak(label)
                        })
                        Spacer(Modifier.width(14.dp))
                        Text(label, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            // Controles de tamaño de texto grandes
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = { fontScale = (fontScale + 0.1f).coerceAtMost(2.2f) },
                    modifier = Modifier.weight(1f).height(56.dp).clip(MaterialTheme.shapes.large)
                ) { Text("Texto +") }
                OutlinedButton(
                    onClick = { fontScale = (fontScale - 0.1f).coerceAtLeast(1.3f) },
                    modifier = Modifier.weight(1f).height(56.dp).clip(MaterialTheme.shapes.large)
                ) { Text("Texto −") }
            }

            Spacer(Modifier.weight(1f))

            // Botón continuar
            Button(
                onClick = {
                    scope.launch {
                        AccessibilityStore.save(ctx, AccessibilityPrefs(profile, fontScale, true, profile == VisualProfile.DALTONISMO))
                        nav.navigate("login") { popUpTo("onboarding") { inclusive = true } }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(64.dp).clip(MaterialTheme.shapes.large)
            ) { Text("Guardar y continuar") }
        }
    }
}
