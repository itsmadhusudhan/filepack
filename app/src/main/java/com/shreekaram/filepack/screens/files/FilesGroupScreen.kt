package com.shreekaram.filepack.screens.files

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.shreekaram.filepack.navigation.Route
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

val groupsList = listOf(
    Groups.Photos, Groups.Videos, Groups.Audio, Groups.Documents, Groups.Apks, Groups.Archives
)

val sourcesGroup = listOf(
    Groups.WhatsApp,
    Groups.Downloads,
    Groups.Bluetooth
)

class NoRippleInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()
    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction) = true
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FilesGroupScreen(navController: NavHostController) {
    val interactionSource = remember { NoRippleInteractionSource() }

    Scaffold(
        topBar = { AppBar(navController) },
        backgroundColor = Color(0xfff5f5f5),
    ) { values ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 12.dp, top = 20.dp, end = 12.dp, bottom = 20.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = values.calculateBottomPadding() + 50.dp)
        ) {
            item(span = { GridItemSpan(2) }) {
                BaseGroupCard(
                    interactionSource = interactionSource,
                    onClick = { navController.navigate(Route.FolderSpace.id) },
                ) {
                    Text("Device Storage", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(text = "45GB")
                }
            }

            item {
                BaseGroupCard(
                    onClick = { navController.navigate(Route.FolderSpace.id) },
                    interactionSource = interactionSource
                ) {
                    Text("Cloud drive", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(60.dp))
                }
            }

            items(groupsList, key = { it.id }) { group ->
                BaseGroupCard(
                    interactionSource,
                    onClick = { navController.navigate(Route.FolderSpace.id) },
                ) {
                    Icon(group.icon, group.title, tint = MaterialTheme.colors.primary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(group.title, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    Text("1947", fontSize = 12.sp, color = Color.Gray)
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(text = "SOURCES", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }

            sourceGroup()
        }
    }
}


fun LazyGridScope.sourceGroup() {
    item(span = { GridItemSpan(maxLineSpan) }) {
        Surface(
            elevation = 1.dp,
            modifier = Modifier
                .clip(AbsoluteRoundedCornerShape(20.dp))

        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    sourcesGroup.forEach { group ->
                        Row(
                            modifier = Modifier
                                .clickable { }
                                .fillMaxSize()
                                .padding(horizontal = 12.dp, vertical = 20.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(group.color)
                                    .padding(4.dp)
                            ) {
                                Icon(group.icon, group.title, tint = Color.White)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(group.title)
                        }
                    }
                }
            }

        }
    }
}

