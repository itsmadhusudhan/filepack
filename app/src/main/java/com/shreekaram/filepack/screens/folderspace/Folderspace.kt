package com.shreekaram.filepack.screens.folderspace

import android.annotation.SuppressLint
import android.os.Environment
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.shreekaram.filepack.navigation.Route
import java.io.File
import kotlin.math.roundToInt


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FolderspaceScreen(navController: NavHostController, folderName: String?) {
    val photos = remember {
        val path = Environment.getExternalStorageDirectory().toString() + folderName
        val directory = File(path)
        val files = directory.listFiles()

        if (files === null) {
            Log.d("ARGS", "DON'T HAVE FILES")
        }

        files
    }

    Scaffold {
        LazyColumn() {
            if (photos !== null)
                items(photos.size) {
                    val file = photos[it]

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .clickable() {
                                if (file.isDirectory)
                                    navController.navigate(Route.FolderSpace.id + "?" + "folderName=${folderName}/${file.name}")
                            }
                            .fillMaxSize()
                            .padding(20.dp),

                        ) {
                        if (file.isDirectory) {
                            Icon(Icons.Filled.Folder, "Folder")
                        } else {
                            if (file.name.endsWith(".jpg")) {
                                AsyncImage(
                                    model = file.absolutePath,
                                    contentDescription = file.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                )
                            } else {
                                Icon(Icons.Filled.InsertDriveFile, "File")
                            }
                        }
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                        ) {
                            Text(file.name)
                            if (file.isFile) Text("${(file.sizeInMb * 100.0).roundToInt() / 100.0}Mb")
                        }
                    }
                }
        }
    }
}

val File.size get() = if (!exists()) 0.0 else length().toDouble()
val File.sizeInKb get() = size / 1024
val File.sizeInMb get() = sizeInKb / 1024
val File.sizeInGb get() = sizeInMb / 1024
val File.sizeInTb get() = sizeInGb / 1024