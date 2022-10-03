package com.wadud.user.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.wadud.user.R


@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Destination
@RootNavGraph(start = true)
fun LogInScreen(
    navigator : DestinationsNavigator,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val (focusUserName, focusPassword) = remember { FocusRequester.createRefs() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //Login Title
        Text(
            text = stringResource(R.string.login),
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.size(24.dp))

        //Email TextField
        EmailField(
            state.email,
            focusUserName,
            focusPassword,
            state.isEmailError
        ) { viewModel.onEvent(LoginEvent.OnEmailChanged(it)) }

        //Password TextField
        PasswordField(state.password, focusPassword, keyboardController, state.isPasswordError) {
            viewModel.onEvent(LoginEvent.OnPassWordChanged(it))
        }

        //Login Button
        Button(
            onClick = { viewModel.onEvent(LoginEvent.OnLogInPressed) },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
                .height(50.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = stringResource(R.string.login))
        }

       state.isEntryValid?.let {
        TODO("Navigate to User screen")
       }
    }
}

@Composable
private fun EmailField(
    email: String,
    focusUserName: FocusRequester,
    focusPassword: FocusRequester,
    isEmailError: Boolean,
    onEmailChanged: (String) -> Unit

) {
    Column {
        OutlinedTextField(
            value = email,
            onValueChange = {
                onEmailChanged(it)
            },
            isError = isEmailError,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                .fillMaxWidth().focusRequester(focusUserName),
            maxLines = 1,
            label = { Text(text = stringResource(R.string.email)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardActions = KeyboardActions(onNext = { focusPassword.requestFocus() }),
            singleLine = true
        )
        if (isEmailError) ErrorField(stringResource(R.string.invalid_email_format))
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun PasswordField(
    password: String,
    focusPassword: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
    isPasswordError: Boolean,
    onPasswordChanged: (String) -> Unit
) {
    Column {
        OutlinedTextField(
            value = password,
            onValueChange = {
                onPasswordChanged(it)
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth()
                .focusRequester(focusPassword),
            maxLines = 1,
            label = { Text(text = stringResource(R.string.password)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            isError = isPasswordError,
            keyboardActions = KeyboardActions(onNext = { keyboardController?.hide() }),
            singleLine = true
        )
        if (isPasswordError) ErrorField(stringResource(R.string.invalid_password))
    }
}


@Composable
fun ErrorField(error: String) {
    Text(
        text = error,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        style = TextStyle(color = MaterialTheme.colors.error)
    )
}