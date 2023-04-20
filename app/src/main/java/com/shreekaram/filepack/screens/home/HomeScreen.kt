package com.shreekaram.filepack.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.shreekaram.filepack.navigation.Route
import androidx.navigation.compose.composable

@Composable
fun TopAppBarActionButton(
    imageVector: ImageVector,
    description: String,
    onClick: () -> Unit
) {
    IconButton(onClick = {
        onClick()
    }) {
        Icon(imageVector = imageVector, contentDescription = description)
    }
}

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
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Files", color = Color.White)
                            },
                            elevation = 0.dp,
                            actions = {
                                TopAppBarActionButton(
                                    imageVector = Icons.Outlined.Search,
                                    description = "Search"
                                ) {
                                    navController.navigate(Route.Search.id)
                                }
                            }
                        )
                    },
                ) {
                    Column {
                        Text("Files SCREEN")
                        Button(onClick = { navController.navigate(Route.FolderSpace.id) }) {
                            Text(text = "Go to Details")
                        }
                    }
                }
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


