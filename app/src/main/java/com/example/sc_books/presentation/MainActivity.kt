package com.example.sc_books.presentation

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.example.sc_books.navigation.Destinations.*
import com.example.sc_books.navigation.NavigationHost
import com.example.sc_books.presentation.components.BottomNavigationBar
import com.example.sc_books.presentation.components.TopFBar
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.sc_books.datastore.Preferencias

/*private const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST = 34
private fun foregroundPermissionApproved(context: Context): Boolean {
    return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
        context, Manifest.permission.CAMERA
    )
}

private fun requestForegroundPermission(context: Context){
    val provideRationale = foregroundPermissionApproved(context)

    if (provideRationale) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.CAMERA), REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST
        )
    } else {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.CAMERA), REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST
        )
    }
}*/

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //requestForegroundPermission(this@MainActivity)
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val dataStore = Preferencias(context)
    val skipLogin = dataStore.getSkipSesion.collectAsState(initial = false).value
    val estadoSesion = dataStore.getEstadoSesion.collectAsState(initial = false).value
    val openLogin = remember { mutableStateOf(true) }
    if (openLogin.value){
        if (skipLogin || estadoSesion){
            openLogin.value = !openLogin.value
        }
        else {
            LoginScreen()
        }
    }
    if (!openLogin.value) {
        BottomNavigation(openLogin)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomNavigation(openLogin: MutableState<Boolean>) {
    val navController= rememberNavController()
    val navigationItem = listOf(
        Sugerencias,
        Buscador,
        NuevoPost,
        MyPosts,
        Favoritos
    )

    Scaffold(
        topBar = { TopFBar(openLogin)},
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                items = navigationItem)
        }
    ){
        NavigationHost(navController = navController)
    }
}




