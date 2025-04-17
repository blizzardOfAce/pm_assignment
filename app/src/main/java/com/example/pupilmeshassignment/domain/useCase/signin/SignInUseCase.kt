package com.example.pupilmeshassignment.domain.useCase.signin

import com.example.pupilmeshassignment.domain.repository.UserRepository

class SignInUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(email: String, password: String): Boolean {
        return repository.signIn(email, password)
    }
}
