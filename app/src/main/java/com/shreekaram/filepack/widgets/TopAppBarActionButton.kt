package com.shreekaram.filepack.widgets

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TopAppBarActionButton(
    imageVector: ImageVector,
    description: String,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(imageVector = imageVector, contentDescription = description)
    }
}

@Preview(name = "TopAppBarActionButton", showBackground = true, showSystemUi = true)
@Composable
private fun PreviewTopAppBarActionButton() {
    TopAppBarActionButton(Icons.Filled.Search, "Search icon", {})
}