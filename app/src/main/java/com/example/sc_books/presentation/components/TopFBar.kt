package com.example.sc_books.presentation.components

import android.util.Log
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.sc_books.datastore.Preferencias
import com.example.sc_books.navigation.Destinations
import com.example.sc_books.presentation.LoginScreen
import com.example.sc_books.ui.theme.CustomViolet
import com.example.sc_books.ui.theme.Purple500
import kotlinx.coroutines.launch


@Composable
fun TopFBar(
    openLogin: MutableState<Boolean>, navController:NavHostController){
    val mContext = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = Preferencias(mContext)
    val estadoSesion = dataStore.getEstadoSesion.collectAsState(initial = false).value
    val skipSesion = dataStore.getSkipSesion.collectAsState(initial = false).value
    TopAppBar(
        title = { Text(
            text = "S&C Books",
            fontSize = 20.sp,

        ) },
        actions = {
            IconButton(onClick = {
                navController.navigate("perfil_usuario") {
                    navController.graph.startDestinationRoute?.let { screen_route ->
                        popUpTo(screen_route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
            ){
                Icon(Icons.Default.Person, "Perfil")
            }
            IconButton(onClick = {
                if (estadoSesion){
                    scope.launch {
                        dataStore.saveEstadoSesion(false)
                        openLogin.value = !openLogin.value
                    }
                }
            }
            ){
                Icon(Icons.Default.Logout, "Salir")
            }
        },
        backgroundColor = CustomViolet,
        contentColor = Color.White
    )
}