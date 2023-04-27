package com.shreekaram.filepack.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shreekaram.filepack.screens.files.GroupType
import com.shreekaram.filepack.screens.folderspace.FolderSpaceScreen
import com.shreekaram.filepack.screens.home.HomeScreen

//class GroupNavType : NavType<GroupType>(isNullableAllowed = false) {
//    override fun get(bundle: Bundle, key: String): GroupType? {
//        return bundle.getParcelable(key)
//    }
//
//    override fun parseValue(value: String): GroupType {
//        return GroupType.valueOf(value)
//    }
//
//    override fun put(bundle: Bundle, key: String, value: GroupType) {
//        bundle.putParcelable(key, value)
//    }
//
//}

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(startDestination = Route.Home.id, navController = navController) {
        composable(Route.Home.id) { HomeScreen(navController = navController) }

        composable(
            route = Route.FolderSpace.id + Route.FolderSpace.args, arguments = listOf(
                navArgument("folderName") {
                    type = NavType.StringType
                    defaultValue = "/"
                },
                navArgument("groupType") {
                    type = NavType.StringType
                    defaultValue = GroupType.ALL.name
                }
            )
        ) {
            val folderName = it.arguments?.getString("folderName")
            val groupType = it.arguments?.getString("groupType")!!



            FolderSpaceScreen(
                navController = navController,
                folderName = folderName,
                groupType = groupType
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

