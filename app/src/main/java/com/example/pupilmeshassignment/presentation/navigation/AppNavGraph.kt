package com.example.pupilmeshassignment.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pupilmeshassignment.presentation.screens.facerecognition.FaceDetectionScreen
import com.example.pupilmeshassignment.presentation.screens.manga.MangaDetailsScreen
import com.example.pupilmeshassignment.presentation.screens.manga.MangaScreen
import com.example.pupilmeshassignment.presentation.screens.signIn.SignInScreen

@Composable
fun AppNavGraph(
    navController: NavHostController, startDestination: String, innerPadding: PaddingValues
) {
    NavHost(navController, startDestination = startDestination) {
        composable(Screen.SignIn.route) {
            SignInScreen(
                innerPadding = innerPadding, navController = navController
            )
        }

        composable(Screen.Manga.route) {
            MangaScreen(
                onClickManga = { mangaId -> navController.navigate("details/$mangaId") },
                onLogout = { navController.navigate(Screen.SignIn.route) { popUpTo(0) } })
        }
        composable(Screen.FaceDetection.route) {
            FaceDetectionScreen()
        }

        composable(
            route = "details/{mangaId}",
            arguments = listOf(navArgument("mangaId") { type = NavType.StringType })
        ) {
            val mangaId = it.arguments?.getString("mangaId") ?: return@composable
            MangaDetailsScreen(mangaId = mangaId, innerPadding = innerPadding)
        }

    }
}
