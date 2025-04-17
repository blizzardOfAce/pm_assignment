package com.example.pupilmeshassignment.data.repository

import android.content.Context
import com.example.pupilmeshassignment.data.local.dao.UserDao
import com.example.pupilmeshassignment.data.local.entity.UserEntity
import com.example.pupilmeshassignment.domain.repository.UserRepository
import androidx.core.content.edit

class UserRepositoryImpl(
    private val userDao: UserDao,
    context: Context
) : UserRepository {

    private val sharedPrefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    override suspend fun signIn(email: String, password: String): Boolean {
        val user = userDao.getUserByEmail(email)
        return if (user == null) {
            // Create new user
            userDao.insertUser(UserEntity(email, password))
            setLoggedInUser(email)
            true
        } else if (user.password == password) {
            setLoggedInUser(email)
            true
        } else {
            false
        }
    }

    override suspend fun signOut() {
        sharedPrefs.edit { remove("logged_in_email") }
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return sharedPrefs.contains("logged_in_email")
    }

    private fun setLoggedInUser(email: String) {
        sharedPrefs.edit { putString("logged_in_email", email) }
    }
}
