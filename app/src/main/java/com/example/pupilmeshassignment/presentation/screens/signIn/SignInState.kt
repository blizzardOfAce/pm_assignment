package com.example.pupilmeshassignment.presentation.screens.signIn

data class SignInState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSignedIn: Boolean = false
)
