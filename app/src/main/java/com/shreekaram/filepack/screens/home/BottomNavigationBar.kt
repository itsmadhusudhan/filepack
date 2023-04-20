package com.shreekaram.filepack.screens.home

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.shreekaram.filepack.navigation.Route

sealed class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val selectedIcon: ImageVector
) {
    object Files : BottomNavItem(
        title = Route.Files.title,
        icon = Icons.Outlined.Folder,
        route = Route.Files.id,
        selectedIcon = Icons.Filled.Folder
    )

    object Recent : BottomNavItem(
        Route.Recent.title,
        Icons.Outlined.AccessTime,
        Route.Recent.id,
        Icons.Filled.AccessTimeFilled
    )

    object Tag : BottomNavItem(
        Route.Tag.title,
        Icons.Outlined.BookmarkBorder,
        Route.Tag.id,
        Icons.Filled.Bookmark
    )
}

@Composable
fun BottomNavigationBar(controller: NavHostController) {
    val borderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5F)

    BottomNavigation(backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
        elevation = 0.dp,
        modifier = Modifier
            .drawBehind {
                drawLine(
                    borderColor,
                    Offset(0f, 0F),
                    Offset(size.width, 0F),
                    1F
                )
            }) {
        val items = listOf(
            BottomNavItem.Files,
            BottomNavItem.Recent,
            BottomNavItem.Tag,
        )

        val stackEntry by controller.currentBackStackEntryAsState()
        val currentRoute = stackEntry?.destination?.route

        items.forEach {
            val selected = currentRoute == it.route

            BottomNavigationItem(
                selected = selected,
                label = { Text(text = it.title, fontSize = 10.sp) },
                icon = {
                    Icon(
                        imageVector = if (selected) it.selectedIcon else it.icon,
                        contentDescription = it.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                unselectedContentColor = MaterialTheme.colors.onBackground.copy(alpha = 0.5F),
                selectedContentColor = MaterialTheme.colors.onBackground,
                onClick = {
                    controller.navigate(it.route) {
                        controller.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }

    }
}