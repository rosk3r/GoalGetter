package ru.rosk3r.goalgetter.util

fun String.isValidEmail(): Boolean {
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$")
    return emailRegex.matches(this)
}