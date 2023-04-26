package com.shreekaram.filepack.navigation

import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shreekaram.filepack.screens.folderspace.FolderspaceScreen
import com.shreekaram.filepack.screens.home.HomeScreen

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(startDestination = Route.Home.id, navController = navController) {
        composable(Route.Home.id) { HomeScreen(navController = navController) }

        composable(
            route = Route.FolderSpace.id + Route.FolderSpace.args, arguments = listOf(
                navArgument("folderName") {
                    type = NavType.StringType
                    defaultValue = "/"
                }
            )
        ) {


            val folderName = it.arguments?.getString("folderName")

            if (folderName != null) {
                Log.d("ARGS", folderName)
            }


            FolderspaceScreen(
                navController = navController,
                folderName = folderName
            )
        }

        composable(Route.Search.id) {
            Text(text = "Search Screen")
        }

        composable(Route.Settings.id) {
            Text(text = "Settings Screen")
        }
    }
}

