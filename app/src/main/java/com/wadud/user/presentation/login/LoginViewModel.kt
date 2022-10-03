    package com.wadud.user.presentation.login

    import android.util.Patterns
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.setValue
    import androidx.lifecycle.ViewModel
    import dagger.hilt.android.lifecycle.HiltViewModel
    import javax.inject.Inject

    @HiltViewModel
    class LoginViewModel @Inject constructor() : ViewModel() {

        var state by mutableStateOf(LoginState())

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
            state = state.copy(isPasswordError = password.length < 6)
            state = state.copy(isEmailError = !Patterns.EMAIL_ADDRESS.matcher(email).matches())

            state = state.copy (isEntryValid = !state.isEmailError && !state.isPasswordError)

        }
    }