package com.shreekaram.filepack.viewmodels

import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class FileViewModel @Inject constructor() : ViewModel() {
    private val initialiseFiles: () -> MutableStateFlow<Array<File>> = {
        val liveData: MutableStateFlow<Array<File>> = MutableStateFlow(arrayOf())

        viewModelScope.launch {
            val path = Environment.getExternalStorageDirectory().toString() + "/"
            val directory = File(path)

            var tempFiles = arrayOf<File>()

            directory.walk().forEach {

                tempFiles = tempFiles.plus(it)
            }

            liveData.emit(tempFiles)
        }

        liveData
    }

    private val _files: MutableStateFlow<Array<File>> by lazy(
        initializer = initialiseFiles
    )
    val files: StateFlow<Array<File>> get() = _files

}