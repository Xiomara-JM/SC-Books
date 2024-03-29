package com.example.sc_books.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

import com.example.sc_books.navigation.Destinations.*
import com.example.sc_books.presentation.screens.*
import com.example.sc_books.ui.theme.SCBooksTheme
import com.example.sc_books.viewmodels.BookViewModel
import com.example.sc_books.viewmodels.SearchViewModel
import com.example.sc_books.presentation.screens.LibroResCita


@ExperimentalComposeUiApi
@Composable
fun NavigationHost(
    navController: NavHostController,
    ViewModel: BookViewModel = hiltViewModel(),
    searchViewModel: SearchViewModel = viewModel()
    /*darkMode: MutableState<Boolean>*/
) {
    NavHost(navController = navController, startDestination = Sugerencias.route) {
        composable(Sugerencias.route) {
            Sugerencias(navController)
        }

        composable(Buscador.route) {
            Buscador(
                ViewModel = ViewModel,
                searchViewModel = searchViewModel,
                navController = navController
            )
        }

        composable(NuevoPost.route) {
            NuevoPost(navController
            ) { /*newText ->*/
                navController.navigate(MyPosts.route)
            }
        }

        composable(MyPosts.route) {
            MyPosts(navController)
        }
        composable(Favoritos.route) {
            Favoritos(navController = navController)
        }

        composable("comentarios_posts") {
            ComentariosPosts(navController)
        }

        composable("perfil_usuario") {
            PerfilUsuario(navController)
        }

        /*composable("libro_resena_cita") {
            LibroResCita(newText, navController)
        }*/

        composable(
            route = InfoLibro.route,
            arguments = listOf(navArgument("newText"){defaultValue = "R_m3DgAAQBAJ"})
        ) { navBackStackEntry->
            var newText = navBackStackEntry.arguments?.getString("newText")
            requireNotNull(newText)
            LibroResCita(newText, navController)
        }

        composable("VistaLibroDetalle") {
            VistaLibroDetalle(ViewModel,navController)
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