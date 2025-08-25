package com.example.sumativa1.data

import com.example.sumativa1.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object UserRepository {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    fun addUser(user: User): Result<Unit> {
        val current = _users.value
        if (current.size >= 5) return Result.failure(IllegalStateException("Máximo 5 usuarios permitidos"))
        if (current.any { it.email.equals(user.email, ignoreCase = true) }) {
            return Result.failure(IllegalArgumentException("El correo ya existe"))
        }
        _users.value = current + user
        return Result.success(Unit)
    }

    fun validate(email: String, password: String): Boolean =
        _users.value.any { it.email.equals(email, true) && it.password == password }
}
