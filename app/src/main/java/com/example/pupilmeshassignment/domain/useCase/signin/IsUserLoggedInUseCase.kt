package com.example.pupilmeshassignment.domain.useCase.signin

import com.example.pupilmeshassignment.domain.repository.UserRepository

class IsUserLoggedInUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(): Boolean {
        return repository.isUserLoggedIn()
    }
}
