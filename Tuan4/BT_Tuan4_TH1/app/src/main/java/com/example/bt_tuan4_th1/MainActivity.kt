package com.example.bt_tuan4_th1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.draw.clip
import androidx.navigation.NavHostController
import androidx.navigation.compose.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "root") {
        composable("root") { RootScreen(navController) }
        composable("list") { ListScreen(navController) }
        composable("detail/{quote}") { backStackEntry ->
            DetailScreen(navController, backStackEntry.arguments?.getString("quote") ?: "")
        }
    }
}

// Màn hình chính
@Composable
fun RootScreen(navController: NavHostController?) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.quote_background),
            contentDescription = "Logo",
            modifier = Modifier.size(160.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Navigation", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "is a framework that simplifies the implementation of navigation between different Ul components (activities, fragments, or composables) in an app.",
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController?.navigate("list") },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text("PUSH", color = Color.White)
        }
    }
}

// Màn hình danh sách trích dẫn (LazyColumn)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavHostController?) {
    val quotes = (1..5).map { "0$it | The only way to do great work is to love what you do." } +
            "1.000.000 | The only way to do great work is to love what you do."

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("LazyColumn", color = Color.Blue, textAlign = TextAlign.Center) },
                navigationIcon = { BackButton { navController?.popBackStack() } }
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(quotes) { quote ->
                ListItem(quote) { navController?.navigate("detail/${quote}") }
            }
        }
    }
}

// Thành phần hiển thị mỗi trích dẫn trong danh sách
@Composable
fun ListItem(quote: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD7EBFF))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = quote, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(8.dp))
            NextButton(onClick)
        }
    }
}

// Màn hình chi tiết
@Composable
fun DetailScreen(navController: NavHostController?, quote: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tiêu đề và nút Back
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            BackButton { navController?.popBackStack() }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Detail",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nội dung trích dẫn
        Text(
            text = quote,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Hình ảnh
        Image(
            painter = painterResource(id = R.drawable.detail),
            contentDescription = "Detail Background",
            modifier = Modifier
                .fillMaxWidth()
                .height(550.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Nút quay về màn hình gốc
        Button(
            onClick = { navController?.navigate("root") },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text("BACK TO ROOT", color = Color.White)
        }
    }
}

// Nút Back có bo góc và nền xanh dương
@Composable
fun BackButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF42A5F5))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.arrow_left),
            contentDescription = "Back",
            modifier = Modifier.size(24.dp)
        )
    }
}

// Nút Next có bo góc và nền đen
@Composable
fun NextButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Black)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.arrow_right),
            contentDescription = "Next",
            modifier = Modifier.size(24.dp)
        )
    }
}

// Xem trước trong Android Studio Preview
@Preview(showBackground = true)
@Composable
fun PreviewRootScreen() {
    RootScreen(null)
}

@Preview(showBackground = true)
@Composable
fun PreviewListScreen() {
    ListScreen(null)
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailScreen() {
    DetailScreen(null, "The only way to do great work is to love what you do.")
}
