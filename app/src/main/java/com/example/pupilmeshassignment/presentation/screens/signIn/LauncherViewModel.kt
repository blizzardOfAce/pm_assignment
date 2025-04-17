package com.example.pupilmeshassignment.presentation.screens.signIn

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pupilmeshassignment.domain.useCase.signin.IsUserLoggedInUseCase
import com.example.pupilmeshassignment.presentation.navigation.Screen
import kotlinx.coroutines.launch

class LauncherViewModel(
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase
) : ViewModel() {

    private val _startDestination = mutableStateOf<String?>(null)
    val startDestination: State<String?> = _startDestination

    init {
        viewModelScope.launch {
            val isLoggedIn = isUserLoggedInUseCase()
            _startDestination.value = if (isLoggedIn) Screen.Manga.route else Screen.SignIn.route
        }
    }
}
