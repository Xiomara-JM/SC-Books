package com.example.sc_books.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Rutas(
    val route: String, //id destinos
) {
    object Login: Rutas("Login")
    object Main: Rutas("Main")
}