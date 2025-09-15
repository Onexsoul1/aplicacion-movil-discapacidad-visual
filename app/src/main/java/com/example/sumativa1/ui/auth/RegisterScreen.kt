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
import com.example.sumativa1.model.User
import com.example.sumativa1.util.isStrongPassword
import com.example.sumativa1.util.isValidEmail
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(nav: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    val roles = listOf("Usuario", "Supervisor", "Invitado")
    var expanded by remember { mutableStateOf(false) }
    var role by remember { mutableStateOf(roles.first()) }

    var accept by remember { mutableStateOf(false) }
    val notifOpts = listOf("Email", "WhatsApp", "SMS")
    var notif by remember { mutableStateOf(notifOpts.first()) } // (se deja seleccionado para la UI)

    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { AppTopBar(title = "Crear cuenta", nav = nav) },
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
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 64.dp)
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 64.dp)
            )
            OutlinedTextField(
                value = pass,
                onValueChange = { pass = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 64.dp)
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = role,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Rol") },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .heightIn(min = 64.dp)
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    roles.forEach { r ->
                        DropdownMenuItem(
                            text = { Text(r) },
                            onClick = { role = r; expanded = false }
                        )
                    }
                }
            }

            Text("Notificaciones:")
            notifOpts.forEach {
                Row {
                    RadioButton(selected = notif == it, onClick = { notif = it })
                    Text(" $it")
                }
            }

            Row {
                Checkbox(checked = accept, onCheckedChange = { accept = it })
                Text(" Acepto términos y condiciones")
            }

            Button(
                onClick = {
                    if (!accept) return@Button

                    // Validaciones con EXTENSIONES
                    if (name.isBlank()) {
                        scope.launch { snackbar.showSnackbar("Ingresa tu nombre") }
                        return@Button
                    }
                    if (!email.trim().isValidEmail()) {
                        scope.launch { snackbar.showSnackbar("Correo inválido") }
                        return@Button
                    }
                    val candidate = User(name = name.trim(), email = email.trim(), password = pass, role = role)
                    if (!candidate.isStrongPassword) {
                        scope.launch { snackbar.showSnackbar("Contraseña muy corta (mín. 6)") }
                        return@Button
                    }

                    // Alta en repositorio (usa safeRun/try-catch internamente)
                    val result = UserRepository.addUser(candidate)
                    if (result.isSuccess) {
                        name = ""; email = ""; pass = ""
                        scope.launch { snackbar.showSnackbar("Usuario registrado") }
                        nav.navigate("login")
                    } else {
                        scope.launch {
                            snackbar.showSnackbar(result.exceptionOrNull()?.message ?: "Error al registrar")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                enabled = accept,
                shape = MaterialTheme.shapes.large
            ) { Text("Registrar") }
        }
    }
}
