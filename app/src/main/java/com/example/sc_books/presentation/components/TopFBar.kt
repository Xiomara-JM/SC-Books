package com.example.sc_books.presentation.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.sc_books.ui.theme.CustomViolet
import com.example.sc_books.ui.theme.Purple500


@Composable
fun TopFBar(){
    TopAppBar(
        title = { Text(
            text = "S&C Books",
            fontSize = 20.sp,

        ) },
        actions = {
            IconButton(onClick = { /*TODO*/ }
            ){
                Icon(Icons.Default.Person, "Perfil")
            }
            IconButton(onClick = { /*TODO*/ }
            ){
                Icon(Icons.Default.Logout, "Salir")
            }
        },
        backgroundColor = CustomViolet,
        contentColor = Color.White
    )
}