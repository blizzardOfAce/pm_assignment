package com.example.pupilmeshassignment.domain.useCase.signin

import com.example.pupilmeshassignment.domain.repository.UserRepository

class SignOutUseCase(private val repository: UserRepository) {
    suspend operator fun invoke() {
        repository.signOut()
    }
}
