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
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sc_books.datastore.Preferencias
import dagger.hilt.android.AndroidEntryPoint

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

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //requestForegroundPermission(this@MainActivity)
            MainScreen()
        }
    }
}
/*
@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun NavigationGraph(navController: NavHostController, activity: MainActivity) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            HomeScreen(navController)
        }
        composable("join") {
            JoinScreen(navController)
        }
        composable("add_purse") {
            AddPurseScreen(navController)
        }
        composable("list_deposits/{purseJson}") {
            Deposits(navController, it.arguments?.getString("purseJson"))
        }
        composable("deposit/{purseJson}") {
            DepositScreen(navController, it.arguments?.getString("purseJson"))
        }
        composable("profile") {
            ProfileScreen(navController = navController)
        }
        composable("customize") {
            CustomizeScreen(navController = navController, activity.fontFamily, activity.theme, activity.language) {
                    fontFamily: String, theme: String, language: String ->
                activity.updateCustomizeItems(fontFamily, theme, language)
            }
        }
    }
}

 */

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


@OptIn(ExperimentalComposeUiApi::class)
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
        topBar = { TopFBar(openLogin,navController)},
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                items = navigationItem)
        }
    ){
        NavigationHost(navController = navController)
    }
}




