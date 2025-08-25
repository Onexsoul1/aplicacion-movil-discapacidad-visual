package com.example.sumativa1.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sumativa1.ui.common.AppTopBar

@Composable
fun RecoverScreen(nav: NavController) {
    var email by remember { mutableStateOf("") }

    Scaffold(
        topBar = { AppTopBar(title = "Recuperar contraseña", nav = nav) }
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
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 64.dp)
            )

            Button(
                onClick = { nav.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = MaterialTheme.shapes.large
            ) { Text("Enviar enlace") }
        }
    }
}
