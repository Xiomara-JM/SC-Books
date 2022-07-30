package com.example.sc_books.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destinations(
    val route: String, //id destinos
    val title: String,
    val icon: ImageVector
) {
    object Sugerencias: Destinations("Sugerencias", "Sugerencias", Icons.Filled.MenuBook)
    object Buscador: Destinations("Buscador", "Buscador", Icons.Filled.Search)
    object NuevoPost: Destinations("Nuevopost", "Publicar", Icons.Filled.AddCircle)
    object MyPosts: Destinations("MyPosts", "Biblioteca", Icons.Filled.LibraryBooks)
    object Favoritos: Destinations("Favoritos", "Favoritos", Icons.Filled.Favorite)

    object InfoLibro: Destinations("libro_resena_cita/?newText={newText}", "Información", Icons.Filled.Info){
        fun createRoute(newText: String) = "libro_resena_cita/?newText=$newText"
    }
}
