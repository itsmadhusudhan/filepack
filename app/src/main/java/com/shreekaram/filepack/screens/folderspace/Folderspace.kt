package com.shreekaram.filepack.screens.folderspace

import android.annotation.SuppressLint
import android.os.Environment
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material.icons.outlined.GridOn
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.shreekaram.filepack.navigation.Route
import com.shreekaram.filepack.screens.files.GroupType
import com.shreekaram.filepack.widgets.TopAppBarActionButton
import java.io.File
import java.io.FileFilter
import kotlin.math.roundToInt


class ImageFileFilter : FileFilter {
    override fun accept(file: File): Boolean {
        if (file.isFile && file.name.endsWith(".jpg") || file.name.endsWith(".png")) {
            return true
        }
        return false
    }
}

class AudioFileFilter : FileFilter {
    override fun accept(file: File): Boolean {
        if (file.isFile && file.name.endsWith(".mp3") || file.name.endsWith(".wav")) {
            return true
        }
        return false
    }
}

class VideoFileFilter : FileFilter {
    override fun accept(file: File): Boolean {
        if (file.isFile && file.name.endsWith(".mp4")) {
            return true
        }
        return false
    }
}

class DocumentFilter : FileFilter {
    override fun accept(file: File): Boolean {
        if (file.isFile && file.name.endsWith(".pdf") || file.name.endsWith(".docx")) {
            return true
        }
        return false
    }
}

class ApkFilter : FileFilter {
    override fun accept(file: File): Boolean {
        if (file.isFile && file.name.endsWith(".aab") || file.name.endsWith(".apk")) {
            return true
        }
        return false
    }
}

class ArchiveFilter : FileFilter {
    override fun accept(file: File): Boolean {
        if (file.isFile && file.name.endsWith(".zip") || file.name.endsWith(".7z")) {
            return true
        }
        return false
    }
}

enum class LayoutType {
    GRID,
    LIST
}

val filtersMap = mapOf(
    GroupType.PHOTO to ImageFileFilter(),
    GroupType.VIDEO to VideoFileFilter(),
    GroupType.AUDIO to AudioFileFilter(),
    GroupType.DOCUMENT to DocumentFilter(),
    GroupType.APK to ApkFilter(),
    GroupType.ARCHIVE to ArchiveFilter(),
)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FolderSpaceScreen(navController: NavHostController, folderName: String?, groupType: String) {
    var layoutType by remember { mutableStateOf(LayoutType.LIST) }
    val documents = remember {
        val path = Environment.getExternalStorageDirectory().toString() + folderName
        val directory = File(path)

        var files = arrayOf<File>()
        var filter: FileFilter? = filtersMap.get(GroupType.valueOf(groupType))

        if (filter !== null) {
            directory.walk().forEach {
                Log.d("FILES", it.name)

                if (it.isFile && filter.accept(it)) {

                    files = files.plus(it)
                }
            }
        } else {
            val _files = directory.listFiles()

            if (_files !== null) {
                files = _files
            }

        }

        Log.d("FILES", files.size.toString())

        files
    }

    fun navigate(file: File) {
        if (file.isDirectory)
            navController.navigate(Route.FolderSpace.id + "?" + "folderName=${folderName}/${file.name}")
    }

    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = {
                Icon(Icons.Default.ArrowBack, "Back")
            },
            title = { Text(text = "Device Storage", color = MaterialTheme.colors.onBackground) },
            actions = {
                TopAppBarActionButton(
                    imageVector = if (layoutType == LayoutType.GRID) Icons.Outlined.List else Icons.Outlined.GridView,
                    description = "Layout"
                ) {
                    layoutType = when (layoutType) {
                        LayoutType.GRID -> LayoutType.LIST
                        LayoutType.LIST -> LayoutType.GRID
                    }
                }
                TopAppBarActionButton(imageVector = Icons.Outlined.Search, description = "Search") {
                    navController.navigate(Route.Search.id)
                }
            },
        )
    }) {
        if (folderName != null) {
            Text(folderName)
        }

        when (layoutType) {
            LayoutType.GRID -> {
                LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                    items(documents.size) {
                        val file = documents[it]

                        Column(
                            modifier = Modifier
                                .clickable { navigate(file) }
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (file.name.endsWith(".jpg") || file.name.endsWith(".jpeg")) {
                                AsyncImage(
                                    model = file.absolutePath,
                                    contentDescription = file.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(40.dp)
                                )
                            } else {
                                Icon(
                                    Icons.Filled.Folder,
                                    file.name,
                                    tint = MaterialTheme.colors.primary,
                                    modifier = Modifier.size(48.dp)
                                )
                            }

                            Spacer(modifier = Modifier.padding(bottom = 8.dp))
                            Text(file.name, fontSize = 13.sp)
                        }
                    }
                }
            }

            LayoutType.LIST -> {
                LazyColumn {
                    items(documents.size) {
                        val file = documents[it]

                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .clickable { navigate(file) }
                                .fillMaxSize()
                                .padding(20.dp),
                        ) {
                            if (file.isDirectory) {
                                Icon(Icons.Filled.Folder, "Folder")
                            } else {
                                if (file.name.endsWith(".jpg") || file.name.endsWith(".jpeg")) {
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

    }
}

val File.size get() = if (!exists()) 0.0 else length().toDouble()
val File.sizeInKb get() = size / 1024
val File.sizeInMb get() = sizeInKb / 1024
val File.sizeInGb get() = sizeInMb / 1024
val File.sizeInTb get() = sizeInGb / 1024