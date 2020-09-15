package br.com.devlhse.kotlinauthservice.utils

fun String.getResourceContent(): String {
    return System::class.java.getResource(
        this
    ).readText()
}