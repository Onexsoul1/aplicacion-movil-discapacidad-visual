package com.example.sumativa1.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sumativa1.data.UserRepository
import com.example.sumativa1.model.User

@Composable
fun HomeScreen() {
    val usersState = UserRepository.users.collectAsState()
    val users = usersState.value

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Usuarios registrados (${users.size}/5)",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        ElevatedCard(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(Modifier.fillMaxWidth()) {
                    Text("Nombre", Modifier.weight(1.2f), fontWeight = FontWeight.SemiBold)
                    Text("Correo", Modifier.weight(1.6f), fontWeight = FontWeight.SemiBold)
                    Text("Rol", Modifier.weight(0.8f), fontWeight = FontWeight.SemiBold)
                }
                Divider()

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth().heightIn(max = 280.dp)
                ) {
                    items(users) { u: User ->
                        Row(Modifier.fillMaxWidth()) {
                            Text(u.name, Modifier.weight(1.2f))
                            Text(u.email, Modifier.weight(1.6f))
                            Text(u.role, Modifier.weight(0.8f))
                        }
                    }
                }
            }
        }

        Text("Nota: se almacenan hasta 5 usuarios .")
    }
}
