package com.shreekaram.filepack.navigation

sealed class Route(var id: String, var title: String) {
    object Home : Route("home", "Home")
    object Files : Route("files", "Files")
    object Recent : Route("recent", "Recent")
    object Tag : Route("tag", "Tag")

    object Search : Route("search", "Search")
    object Settings : Route("settings", "Settings")
    object FolderSpace : Route("folder_space", "FolderSpace")
}