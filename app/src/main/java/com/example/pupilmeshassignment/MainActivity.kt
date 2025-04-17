package com.example.pupilmeshassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pupilmeshassignment.presentation.common.theme.PupilMeshAssignmentTheme
import com.example.pupilmeshassignment.presentation.navigation.AppNavGraph
import com.example.pupilmeshassignment.presentation.navigation.BottomNavBar
import com.example.pupilmeshassignment.presentation.navigation.Screen
import com.example.pupilmeshassignment.presentation.screens.signIn.LauncherViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val launcherViewModel: LauncherViewModel = getViewModel()
        val startDestination = launcherViewModel.startDestination.value
        enableEdgeToEdge()

        setContent {
            PupilMeshAssignmentTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                if (startDestination != null) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            if (currentRoute != Screen.SignIn.route) {
                                BottomNavBar(navController = navController)
                            }
                        }
                    ) { innerPadding ->
                        AppNavGraph(
                            navController = navController,
                            startDestination = startDestination,
                            innerPadding = innerPadding
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
