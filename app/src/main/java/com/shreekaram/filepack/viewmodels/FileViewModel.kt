package com.shreekaram.filepack.viewmodels

import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileFilter
import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.FileVisitResult.CONTINUE
import java.nio.file.FileVisitResult.SKIP_SIBLINGS
import java.nio.file.FileVisitResult.SKIP_SUBTREE
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import javax.inject.Inject
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.fileVisitor
import kotlin.io.path.isDirectory
import kotlin.io.path.name


data class FileData(
    var photos: List<File> = listOf(),
    var videos: List<File> = listOf(),
    var audios: List<File> = listOf(),
    var documents: List<File> = listOf(),
    var apk: List<File> = listOf(),
    var archives: List<File> = listOf(),
) {
    companion object {
        fun default(): FileData {
            return FileData()
        }
    }
}

class ImageFileFilter : FileFilter {
    override fun accept(file: File): Boolean {
        if (file.isFile && !file.isHidden && !file.parent.endsWith(
                "Sent"
            ) && file.name.endsWith(
                ".jpg"
            ) || file.name.endsWith(".png")
        ) {
            return true
        }
        return false
    }
}


@HiltViewModel
class FileViewModel @Inject constructor() : ViewModel() {
    private val _files = MutableStateFlow(listOf<File>())
    val files: StateFlow<List<File>> get() = _files

    init {
        initialiseFiles()
    }

    @OptIn(ExperimentalPathApi::class)
    private fun initialiseFiles() {
        viewModelScope.launch {
            val path = Environment.getExternalStorageDirectory().toString() + "/"
            val directory = File(path)

            var fileData = listOf<File>()
            val filter = ImageFileFilter()

            withContext(Dispatchers.IO) {
                Files.walkFileTree(directory.toPath(), fileVisitor {
                    onPreVisitDirectory { file, _ ->
                        if (file.name.contains(".")) {
                            return@onPreVisitDirectory SKIP_SUBTREE
                        }

                        CONTINUE
                    }

                    onVisitFile { file, _ ->
                        if (file.parent.name.contains(".")) {
                            return@onVisitFile SKIP_SIBLINGS
                        }

                        if (filter.accept(file.toFile()))
                            fileData = fileData.plus(file.toFile())

                        CONTINUE
                    }

                    onVisitFileFailed { _file, _ ->
                        CONTINUE
                    }
                })
            }

//            directory.walk()
//                .filter { Files.isRegularFile(it.toPath()) && !it.name.endsWith(".thumbnails") }
//                .forEach {
//                    if (filter.accept(it)) {
//                        Log.d("FILES", it.parent)
////                        fileData = fileData.plus(it)
//                    }

//                if (it.isFile && it.name.endsWith(".mp4")) {
//                    fileData.videos = fileData.videos.plus(it)
//                }
//
//                if (it.isFile && it.name.endsWith(".apk")) {
//                    fileData.apk = fileData.apk.plus(it)
//                }
//
//                if (it.isFile && it.name.endsWith(".pdf")) {
//                    fileData.documents = fileData.documents.plus(it)
//                }
//
//
//                if (it.isFile && it.name.endsWith(".zip")) {
//                    fileData.archives = fileData.archives.plus(it)
//                }
//                }

            _files.emit(fileData.sortedBy { it.parentFile?.name })
        }
    }


}