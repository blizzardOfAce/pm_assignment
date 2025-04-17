package com.example.pupilmeshassignment.presentation.screens.signIn

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pupilmeshassignment.domain.useCase.signin.IsUserLoggedInUseCase
import com.example.pupilmeshassignment.domain.useCase.signin.SignInUseCase
import kotlinx.coroutines.launch

class SignInViewModel(
    private val signInUseCase: SignInUseCase,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase
) : ViewModel() {

    var signInState by mutableStateOf(SignInState())
        private set

    init {
        checkIfUserIsLoggedIn()
    }

    private fun checkIfUserIsLoggedIn() {
        viewModelScope.launch {
            val isLoggedIn = isUserLoggedInUseCase()
            signInState = signInState.copy(isSignedIn = isLoggedIn)
        }
    }

    fun onEmailChange(email: String) {
        signInState = signInState.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        signInState = signInState.copy(password = password)
    }

    fun onSignInClick() {
        val email = signInState.email.trim()
        val password = signInState.password.trim()

        if (email.isBlank() || password.isBlank()) {
            signInState = signInState.copy(errorMessage = "Fields cannot be empty")
            return
        }

        // Set loading to true when the sign-in attempt starts
        signInState = signInState.copy(isLoading = true)

        viewModelScope.launch {
            val success = signInUseCase(email, password)

            // Set loading to false after the sign-in attempt finishes
            signInState = if (success) {
                signInState.copy(isSignedIn = true, errorMessage = null, isLoading = false)
            } else {
                signInState.copy(errorMessage = "Authentication failed", isLoading = false)
            }
        }
    }

    fun onErrorConsumed() {
        signInState = signInState.copy(errorMessage = null)
    }
}

