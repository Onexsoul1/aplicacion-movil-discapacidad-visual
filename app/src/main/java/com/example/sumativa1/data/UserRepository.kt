package com.example.sumativa1.data

import com.example.sumativa1.model.User
import com.example.sumativa1.util.safeRun
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object UserRepository {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    // 🔹 1) Agregar usuario con try/catch usando safeRun (inline function)
    fun addUser(user: User): Result<Unit> = safeRun {
        val current = _users.value
        if (current.size >= 5) throw IllegalStateException("Máximo 5 usuarios permitidos")
        if (findUserByEmailLabeled(user.email) != null) throw IllegalArgumentException("El correo ya existe")
        _users.value = current + user
        Unit
    }

    // 🔹 2) Validación usando filter
    fun validateWithFilter(email: String, password: String): Boolean =
        _users.value.filter { it.email.equals(email, true) && it.password == password }.isNotEmpty()

    // 🔹 3) Lambda con etiqueta para buscar usuario por correo
    fun findUserByEmailLabeled(email: String): User? {
        var found: User? = null
        _users.value.forEach search@{ u ->
            if (u.email.equals(email, true)) {
                found = u
                return@search // corta la iteración con etiqueta
            }
        }
        return found
    }

    // 🔹 Compatibilidad con la validación clásica
    fun validate(email: String, password: String): Boolean = validateWithFilter(email, password)
}
