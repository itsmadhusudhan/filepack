package com.shreekaram.filepack.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shreekaram.filepack.screens.home.HomeScreen

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(startDestination = Route.Home.id, navController = navController) {
        composable(Route.Home.id) { HomeScreen(navController = navController) }

        composable(Route.FolderSpace.id) {
            Text(text = "Folder Space Screen")
        }

        composable(Route.Search.id) {
            Text(text = "Search Screen")
        }

        composable(Route.Settings.id) {
            Text(text = "Settings Screen")
        }
    }
}

