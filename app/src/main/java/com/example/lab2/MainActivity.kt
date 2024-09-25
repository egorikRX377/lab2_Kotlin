package com.example.lab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        }
    }
}

@Composable
fun ListsScreen(navController: NavController) {
    var listText by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = listText,
            onValueChange = { listText = it },
            label = { Text("Введите текст") },
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        )
        Text(
            text = "Вы ввели: $listText",
            modifier = Modifier.padding(top = 16.dp)
        )
        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.padding(top = 16.dp)
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