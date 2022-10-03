package com.wadud.user.presentation.login

data class LoginState(
    val email : String = "",
    val password : String = "",
    val isEmailError : Boolean = false,
    val isPasswordError : Boolean = false
)