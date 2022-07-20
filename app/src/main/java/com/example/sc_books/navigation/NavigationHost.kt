package com.example.sc_books.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.sc_books.navigation.Destinations.*
import com.example.sc_books.presentation.screens.*
import com.example.sc_books.ui.theme.SCBooksTheme

@Composable
fun NavigationHost(
    navController: NavHostController,
    /*darkMode: MutableState<Boolean>*/
) {
    NavHost(navController = navController, startDestination = Sugerencias.route) {
        composable(Sugerencias.route) {
            Sugerencias(navController)
        }

        composable(Buscador.route) {
            Buscador()
        }

        composable(NuevoPost.route) {
            NuevoPost(navController
            ) { /*newText ->*/
                navController.navigate(MyPosts.route)
            }
        }

        composable(MyPosts.route) {
            MyPosts()
        }
        composable(Favoritos.route) {
            Favoritos()
        }

    }
}

@Preview
@Composable
fun  PreviewComponent(){
    SCBooksTheme {
       com.example.sc_books.presentation.screens.Sugerencias(navController = NavHostController(
           LocalContext.current))
    }
}