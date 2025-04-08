package com.example.bt_tuan6.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bt_tuan6.R
import com.example.bt_tuan6.model.TaskData
import com.example.bt_tuan6.navigation.Screens
import com.example.bt_tuan6.ui.theme.Blue
import com.example.bt_tuan6.viewmodel.MainViewModel
import androidx.compose.ui.text.font.FontWeight
import com.example.bt_tuan6.ui.screens.HomeScreen
import com.example.bt_tuan6.ui.screens.AddTaskScreen
import com.example.bt_tuan6.ui.screens.PlaceholderScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val selectedIcon = remember { mutableStateOf(Icons.Default.Home) }
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = currentBackStackEntry.value?.destination?.route
    val isAddScreen = currentScreen == Screens.AddTask.screen

    Scaffold(
        topBar = {
            if (!isAddScreen && selectedIcon.value == Icons.Default.Home) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { }) {
                        Image(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = "Back",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    Text("List", style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = Blue
                    ))
                    IconButton(onClick = { navController.navigate(Screens.AddTask.screen) }) {
                        Image(
                            painter = painterResource(id = R.drawable.add),
                            contentDescription = "Add",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .offset(y = 50.dp)
            ) {
                IconButton(
                    onClick = { navController.navigate(Screens.AddTask.screen) },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.add_1),
                        contentDescription = "Add",
                        modifier = Modifier.size(180.dp)
                    )
                }
            }
        },
        bottomBar = {
            BottomAppBar(containerColor = Color.Transparent) {
                Card(
                    modifier = Modifier.fillMaxHeight(),
                    shape = RoundedCornerShape(50.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        listOf(
                            Icons.Default.Home to Screens.Home,
                            Icons.Default.DateRange to Screens.Search,
                            Icons.Default.Notifications to Screens.Notification,
                            Icons.Default.Settings to Screens.Profile
                        ).forEach { (icon, screen) ->
                            IconButton(
                                onClick = {
                                    selectedIcon.value = icon
                                    navController.navigate(screen.screen) {
                                        popUpTo(0)
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    icon,
                                    contentDescription = null,
                                    modifier = Modifier.size(26.dp),
                                    tint = if (selectedIcon.value == icon) Blue else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.Home.screen,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screens.Home.screen) { HomeScreen(viewModel) }
            composable(Screens.Search.screen) { PlaceholderScreen("Search") }
            composable(Screens.Notification.screen) { PlaceholderScreen("Notifications") }
            composable(Screens.Profile.screen) { PlaceholderScreen("Profile") }
            composable(Screens.AddTask.screen) { AddTaskScreen(viewModel) { navController.popBackStack() } }
        }
    }
}