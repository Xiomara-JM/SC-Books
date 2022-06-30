package com.example.sc_books.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.sc_books.navigation.Destinations.*
import com.example.sc_books.navigation.NavigationHost
import com.example.sc_books.presentation.components.BottomNavigationBar
import com.example.sc_books.presentation.components.TopFBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Preview
@Composable
fun MainScreen() {
    val navController= rememberNavController()
    val navigationItem = listOf(
        Sugerencias,
        Buscador,
        NuevoPost,
        MyPosts,
        Favoritos
    )

    Scaffold(
        topBar = { TopFBar()},
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                items = navigationItem)
        }
    ){
        NavigationHost(navController = navController)
    }
}




