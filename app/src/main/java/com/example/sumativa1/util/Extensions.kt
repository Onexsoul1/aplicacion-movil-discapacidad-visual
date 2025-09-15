package com.example.sumativa1.util

import com.example.sumativa1.model.User

// 1) Función de extensión
fun String.isValidEmail(): Boolean =
    this.contains("@") && this.contains(".") && this.length >= 6

// 2) Propiedad de extensión
val User.isStrongPassword: Boolean
    get() = password.length >= 6

// 3) Función inline con try/catch
inline fun <T> safeRun(block: () -> T): Result<T> =
    try {
        Result.success(block())
    } catch (e: Throwable) {
        Result.failure(e)
    }
