package com.example.bt_tuan6.navigation

sealed class Screens(val screen: String) {
    object Home : Screens("home")
    object Search : Screens("search")
    object Notification : Screens("notification")
    object Profile : Screens("profile")
    object AddTask : Screens("addtask")
}