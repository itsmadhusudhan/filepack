package com.shreekaram.filepack.screens.files

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Hexagon
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.ui.graphics.Color

enum class GroupType {
    ALL,
    PHOTO,
    VIDEO,
    AUDIO,
    DOCUMENT,
    APK,
    ARCHIVE
}

sealed class Groups(
    val id: GroupType,
    val title: String,
    val icon: ImageVector,
    val color: Color = Color.Black
) {
    object Photos : Groups(GroupType.PHOTO, "Photos", Icons.Filled.Photo)
    object Videos : Groups(GroupType.VIDEO, "Videos", Icons.Filled.Videocam)
    object Audio : Groups(GroupType.AUDIO, "Audio", Icons.Filled.MusicNote)
    object Documents : Groups(GroupType.DOCUMENT, "Documents", Icons.Filled.InsertDriveFile)
    object Apks : Groups(GroupType.APK, "APKs", Icons.Filled.Hexagon)
    object Archives : Groups(GroupType.ARCHIVE, "Archives", Icons.Filled.Archive)

}

sealed class SourceGroup(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val color: Color = Color.Black
) {

    object WhatsApp : SourceGroup("whatsapp", "WhatsApp", Icons.Filled.Whatsapp, Color(0xff25D366))
    object Downloads :
        SourceGroup("downloads", "Downloads", Icons.Filled.ArrowDownward, Color(0xff25D366))

    object Bluetooth : SourceGroup("bluetooth", "Bluetooth", Icons.Filled.Bluetooth, Color.Blue)
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BaseGroupCard(
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxSize()
            .clip(AbsoluteRoundedCornerShape(20.dp)),
        elevation = 0.dp,
        onClick = onClick,
        interactionSource = interactionSource
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 20.dp)
        ) {
            content()
        }
    }
}