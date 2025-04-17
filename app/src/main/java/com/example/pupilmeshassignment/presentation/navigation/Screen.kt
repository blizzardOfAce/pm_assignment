package com.example.pupilmeshassignment.presentation.navigation

import com.example.pupilmeshassignment.R


sealed class Screen(val route: String, val title: String, val resId: Int) {
    object SignIn: Screen("sign_in", "Sign In", R.drawable.login_32dp)
    object Manga : Screen("manga", "Manga", R.drawable.menu_book_32dp)
    object FaceDetection : Screen("face_detection", "Face Recognition", R.drawable.face_32dp)
}
