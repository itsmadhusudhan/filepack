package com.shreekaram.filepack.screens.home

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.shreekaram.filepack.navigation.Route
import androidx.navigation.compose.composable
import com.shreekaram.filepack.screens.files.FilesGroupScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController) {
    val homeNavController = rememberNavController()

    Scaffold(bottomBar = { BottomNavigationBar(controller = homeNavController) }) {
        NavHost(
            startDestination = Route.Files.id,
            navController = homeNavController,
        ) {
            composable(Route.Files.id) {
                FilesGroupScreen(navController)
            }

            composable(Route.Recent.id) {
                Text("Recent SCREEN")
            }

            composable(Route.Tag.id) {
                Text("Tag SCREEN")
            }
        }
    }
}


