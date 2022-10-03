package com.wadud.user.presentation.login

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(LoginState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChanged -> {
                state = state.copy(email = event.email)
            }

            is LoginEvent.OnPassWordChanged -> {
                state = state.copy(password = event.password)
            }
            is LoginEvent.OnLogInPressed -> {
                validateUserInput(state.email, state.password)
            }
        }
    }

    private fun validateUserInput(email: String, password: String) {
        val emailResult = !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val passwordResult = password.length < 6
        val hasError = listOf(emailResult, passwordResult).any { it }
        state = state.copy(isPasswordError = passwordResult, isEmailError = emailResult)
        if (hasError) {
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }
}

sealed class ValidationEvent {
    object Success : ValidationEvent()
}
