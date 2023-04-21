package com.shreekaram.filepack.screens.files

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.shreekaram.filepack.navigation.Route
import com.shreekaram.filepack.widgets.TopAppBarActionButton

@Composable
fun AppBar(navController: NavHostController) {
    val borderColor = MaterialTheme.colors.onBackground.copy(alpha = 0.5F)

    TopAppBar(
        title = { Text(text = "Files", color = Color.Black) },
        elevation = 0.dp,
        actions = {
            // FIXME: Update this later
            TopAppBarActionButton(imageVector = Icons.Outlined.Search, description = "Search") {
                navController.navigate(Route.Search.id)
            }
        },
        backgroundColor = Color.White,
        modifier = Modifier.drawBehind {
            drawLine(
                borderColor,
                Offset(0F, size.height),
                Offset(size.width, size.height),
                1F
            )
        }
    )
}