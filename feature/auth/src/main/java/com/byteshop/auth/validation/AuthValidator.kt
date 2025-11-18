package com.byteshop.auth.validation

sealed interface ValidationResult {
    object Success : ValidationResult
    data class Error(val error: AuthValidationError) : ValidationResult
}

// Enum of all possible validation errors
enum class AuthValidationError {
    FIELD_REQUIRED,
    INVALID_EMAIL,
    INVALID_PHONE,
    USERNAME_TOO_SHORT,
    USERNAME_TOO_LONG,
    USERNAME_INVALID_CHARS,
    PASSWORD_REQUIRED,
    PASSWORD_TOO_SHORT,
    PASSWORD_TOO_LONG
}

internal object AuthValidator {
    fun validateUsernameOrEmailOrPhone(input: String): ValidationResult {
        return when {
            input.isBlank() -> ValidationResult.Error(AuthValidationError.FIELD_REQUIRED)
            input.contains('@') -> validateEmail(email = input)
            input.all { it.isDigit() || it == '+' || it == '-' } -> validatePhone(phone = input)
            else -> validateUsername(username = input)
        }
    }

    private fun validateEmail(email: String): ValidationResult {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        return if (emailRegex.matches(email)) {
            ValidationResult.Success
        } else {
            ValidationResult.Error(AuthValidationError.INVALID_EMAIL)
        }
    }

    private fun validatePhone(phone: String): ValidationResult {
        val cleanPhone = phone.replace(Regex("[^0-9+]"), "")
        return if (cleanPhone.length >= 10) {
            ValidationResult.Success
        } else {
            ValidationResult.Error(AuthValidationError.INVALID_PHONE)
        }
    }

    private fun validateUsername(username: String): ValidationResult {
        return when {
            username.length < 5 -> ValidationResult.Error(AuthValidationError.USERNAME_TOO_SHORT)
            username.length > 30 -> ValidationResult.Error(AuthValidationError.USERNAME_TOO_LONG)
            !username.matches(Regex("^[a-zA-Z0-9._]+$")) ->
                ValidationResult.Error(AuthValidationError.USERNAME_INVALID_CHARS)

            else -> ValidationResult.Success
        }
    }

    internal fun validatePassword(password: String): ValidationResult {
        return when {
            password.isEmpty() -> ValidationResult.Error(AuthValidationError.PASSWORD_REQUIRED)
            password.length < 6 -> ValidationResult.Error(AuthValidationError.PASSWORD_TOO_SHORT)
            password.length > 30 -> ValidationResult.Error(AuthValidationError.PASSWORD_TOO_LONG)
            else -> ValidationResult.Success
        }
    }
}