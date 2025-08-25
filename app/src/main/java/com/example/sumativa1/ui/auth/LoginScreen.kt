package com.example.sumativa1.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sumativa1.ui.common.AppTopBar
import com.example.sumativa1.data.UserRepository
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(nav: NavController) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { AppTopBar(title = "Iniciar sesión", nav = nav, canNavigateBack = true) },
        snackbarHost = { SnackbarHost(hostState = snackbar) }
    ) { inner ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth().heightIn(min = 64.dp)
            )
            OutlinedTextField(
                value = pass,
                onValueChange = { pass = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth().heightIn(min = 64.dp),
                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                onClick = {
                    if (UserRepository.validate(email.trim(), pass)) {
                        nav.navigate("home")
                    } else {
                        scope.launch {
                            snackbar.showSnackbar("Correo o contraseña incorrectos")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(64.dp),
                shape = MaterialTheme.shapes.large
            ) { Text("Entrar") }

            TextButton(onClick = { nav.navigate("recover") }) { Text("¿Olvidaste tu contraseña?") }
            TextButton(onClick = { nav.navigate("register") }) { Text("Crear cuenta") }
        }
    }
}
