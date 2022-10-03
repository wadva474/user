package com.wadud.user.presentation.login

sealed class LoginEvent{
    data class OnEmailChanged(val email : String) : LoginEvent()
    data class OnPassWordChanged(val password : String) : LoginEvent()
    object  OnLogInPressed : LoginEvent()
}