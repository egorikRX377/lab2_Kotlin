package com.example.lab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.saveable.rememberSaveable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("lists") { ListsScreen(navController) }
        composable("image") { ImageScreen(navController) }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    var fullName by rememberSaveable { mutableStateOf("") }
    var textValue by rememberSaveable { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF2196F3)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = fullName,
                modifier = Modifier.padding(16.dp),
                fontSize = 48.sp
            )

            TextField(
                value = textValue,
                onValueChange = { textValue = it },
                label = { Text(text = "Введите ФИО") },
                placeholder = { Text(text = "") },
                modifier = Modifier.padding(4.dp)
            )

            Button(
                onClick = {
                    fullName = textValue
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Показать ФИО")
            }

            Button(
                onClick = {
                    fullName = ""
                    textValue = ""
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Очистить")
            }

            Button(
                onClick = {
                    navController.navigate("lists")
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Перейти к спискам")
            }

            Button(
                onClick = {
                    navController.navigate("image")
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Перейти к изображению")
            }
        }
    }
}

@Composable
fun ListsScreen(navController: NavController) {
    val groups = listOf("Группа 1", "Группа 2", "Группа 3")
    val students = listOf("Иванов Иван", "Петров Петр", "Сидоров Сидор")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
    ) {
        item {
            Text(
                text = "Группа",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        items(groups) { group ->
            Text(
                text = group,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        item {
            Text(
                text = "ФИО",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        items(students) { student ->
            Text(
                text = student,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        item {
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Назад")
            }
        }
    }
}

@Composable
fun ImageScreen(navController: NavController) {
    val infiniteTransition = rememberInfiniteTransition()
    val animatedOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 200f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.bsuir),
            contentDescription = null,
            modifier = Modifier
                .offset(x = animatedOffset.dp)
                .size(200.dp)
        )

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(text = "Назад")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    MyApp()
}