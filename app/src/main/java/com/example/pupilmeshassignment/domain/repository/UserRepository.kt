package com.example.pupilmeshassignment.domain.repository

interface UserRepository {
    suspend fun signIn(email: String, password: String): Boolean
    suspend fun signOut()
    suspend fun isUserLoggedIn(): Boolean
}
