package com.example.sumativa1.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sumativa1.ui.common.AppTopBar
import com.example.sumativa1.data.UserRepository
import com.example.sumativa1.model.User
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
    var notif by remember { mutableStateOf(notifOpts.first()) }

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
                modifier = Modifier.fillMaxWidth().heightIn(min = 64.dp)
            )
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
                modifier = Modifier.fillMaxWidth().heightIn(min = 64.dp)
            )

            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    value = role, onValueChange = {}, readOnly = true,
                    label = { Text("Rol") },
                    modifier = Modifier.menuAnchor().fillMaxWidth().heightIn(min = 64.dp)
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    roles.forEach { r ->
                        DropdownMenuItem(text = { Text(r) }, onClick = { role = r; expanded = false })
                    }
                }
            }

            Text("Notificaciones:")
            notifOpts.forEach {
                Row { RadioButton(selected = notif == it, onClick = { notif = it }); Text(" $it") }
            }

            Row { Checkbox(checked = accept, onCheckedChange = { accept = it }); Text(" Acepto términos y condiciones") }

            Button(
                onClick = {
                    if (accept) {
                        val result = UserRepository.addUser(
                            User(name = name.trim(), email = email.trim(), password = pass, role = role)
                        )
                        if (result.isSuccess) {
                            name = ""; email = ""; pass = ""
                            nav.navigate("login")
                        } else {
                            scope.launch {
                                snackbar.showSnackbar(result.exceptionOrNull()?.message ?: "Error al registrar")
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(64.dp),
                enabled = accept,
                shape = MaterialTheme.shapes.large
            ) { Text("Registrar") }
        }
    }
}
