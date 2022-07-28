package com.example.sc_books.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.navigation.compose.rememberNavController
import com.example.sc_books.navigation.Destinations.*
import com.example.sc_books.navigation.NavigationHost
import com.example.sc_books.presentation.components.BottomNavigationBar
import com.example.sc_books.presentation.components.TopFBar
import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import com.example.sc_books.datastore.Preferencias
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            MainScreen(this@MainActivity)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            Log.d("datas", task.toString())
            try {
                val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
                if (account != null){
                    //DataStore
                    //val scope = rememberCoroutineScope()
                    val dataStore = Preferencias(this)

                    FirebaseFirestore.getInstance().collection("usuarios")
                        .document(account.email?:"").get()
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                if (!it.getResult().exists()) {
                                    FirebaseFirestore.getInstance().collection("usuarios").get()
                                        .addOnSuccessListener { creados ->
                                            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                                            FirebaseAuth.getInstance().signInWithCredential(credential)
                                                .addOnCompleteListener{auth->
                                                    if (auth.isSuccessful){
                                                        val cantidad = creados.size()
                                                        val tag = (cantidad + 100000).toString()
                                                        val nombreCompleto = account.displayName?:""
                                                        val delim = " "
                                                        val list = nombreCompleto.split(delim)
                                                        val username = list[0]
                                                        lifecycleScope.launch{
                                                            dataStore.saveEstadoSesion(true)
                                                            dataStore.saveNombre(username)
                                                            dataStore.saveEmail(account.email?:"")
                                                            dataStore.saveTag(tag)
                                                        }
                                                        FirebaseFirestore.getInstance().collection("usuarios")
                                                            .document(account.email?:"").set(
                                                                hashMapOf(
                                                                    "nombre" to account.displayName,
                                                                    "apellidos" to "",
                                                                    "email" to account.email,
                                                                    "username" to username,
                                                                    "tag" to tag,
                                                                    "tipo" to "google"
                                                                )
                                                            )
                                                        showAlert(this, 4)
                                                    }
                                                }
                                        }
                                }
                                else {
                                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                                    if (it.result.data?.get("tipo") == "google"){
                                        FirebaseAuth.getInstance().signInWithCredential(credential)
                                            .addOnCompleteListener { auth ->
                                                if (auth.isSuccessful){
                                                    val tag = it.result.data?.get("tag").toString()
                                                    val username = it.result.data?.get("username").toString()
                                                    val email = account.email
                                                    lifecycleScope.launch{
                                                        dataStore.saveEstadoSesion(true)
                                                        dataStore.saveNombre(username)
                                                        dataStore.saveEmail(email?:"")
                                                        dataStore.saveTag(tag)
                                                    }
                                                    showAlert(this, 5)
                                                }
                                            }
                                    }
                                    else {
                                        showAlert(this, 3)
                                    }

                                }

                            }
                        }
                }
            }
            catch (e: ApiException){
                showAlert(this, 6)
            }


        }
    }

}

@Composable
fun MainScreen(activity: MainActivity) {
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
            LoginScreen(activity)
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




