package com.shreekaram.filepack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.shreekaram.filepack.navigation.RootNavigationGraph
import com.shreekaram.filepack.ui.theme.FilePackTheme
import com.shreekaram.filepack.viewmodels.FileViewModel
import dagger.hilt.android.AndroidEntryPoint


val LocalFileViewModel = compositionLocalOf<FileViewModel> {
    error("File viewmodel not set")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            FilePackTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navHostController = rememberNavController()
                    val fileViewModel = hiltViewModel<FileViewModel>()
                    CompositionLocalProvider(LocalFileViewModel provides fileViewModel) {
                        RootNavigationGraph(navController = navHostController)
                    }
                }
            }

        }
    }
}

