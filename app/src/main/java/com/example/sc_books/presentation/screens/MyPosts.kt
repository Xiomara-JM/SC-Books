package com.example.sc_books.presentation.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.sc_books.R
import com.example.sc_books.datastore.Preferencias
import com.example.sc_books.navigation.Destinations
import com.example.sc_books.ui.theme.LB50_900
import com.example.sc_books.ui.theme.SCBooksTheme
import com.example.sc_books.ui.theme.bordePerfil
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

@Composable
fun MyPosts(navController: NavHostController){

    var listadoResenas = remember { mutableListOf<Resenia>() }
    var listadoCitas = remember { mutableListOf<Resenia>() }
    var mostrarResenas = remember { mutableStateOf(false) }
    var mostrarCitas = remember { mutableStateOf(false) }
    var contRes = remember { mutableStateOf(0) }
    var contCit = remember { mutableStateOf(0) }

    //DataStore
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = Preferencias(context)
    val dbf = FirebaseFirestore.getInstance()
    val tagResena = dataStore.getTag.collectAsState(initial = "").value
    val tagCita = dataStore.getTag.collectAsState(initial = "").value



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(top = 24.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var indexResena = 0
        var indexCita = 0
        val listStateResenia = rememberLazyListState()
        val listStateCita = rememberLazyListState()

        if (tagResena!= ""){
            dbf.collection("resenias").whereEqualTo("autor_tag", tagResena).get()
                .addOnSuccessListener {
                    //var cont = 0;
                    if (listadoResenas.size < it.size()) {
                        for (resen in it) {

                            listadoResenas.add(
                                contRes.value, Resenia(
                                    resen.data?.get("autor_email").toString(),
                                    resen.data?.get("autor_tag").toString(),
                                    resen.data?.get("autor_username").toString(),
                                    resen.data?.get("libro").toString(),
                                    resen.data?.get("resenia").toString(),
                                    resen.data?.get("book_id").toString(),
                                    resen.id,
                                    tagResena,
                                    "Reseña"
                                )
                            )
                            contRes.value++

                        }
                        mostrarResenas.value = true
                    }
                    mostrarResenas.value = true

                }
        }

        if (tagCita!= ""){
            dbf.collection("citas").whereEqualTo("autor_tag", tagCita).get()
                .addOnSuccessListener {
                    //var cont = 0;
                    if (listadoCitas.size < it.size()) {
                        for (cita in it) {

                            listadoCitas.add(
                                contCit.value, Resenia(
                                    cita.data?.get("autor_email").toString(),
                                    cita.data?.get("autor_tag").toString(),
                                    cita.data?.get("autor_username").toString(),
                                    cita.data?.get("libro").toString(),
                                    cita.data?.get("texto").toString(),
                                    cita.data?.get("book_id").toString(),
                                    cita.id,
                                    tagCita,
                                    "Cita"
                                )
                            )
                            contCit.value++

                        }
                        mostrarCitas.value = true
                    }
                    mostrarCitas.value = true

                }
        }
        if (mostrarCitas.value && mostrarResenas.value) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                state = listStateCita
            ) {
                items(listadoCitas) { doc ->
                    if (doc != null) {
                        ResenaCard(doc, indexCita, navController, "Cita")
                        indexCita += 1
                    }
                    Divider(
                        color = bordePerfil,
                        thickness = 5.dp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                items(listadoResenas) { doc ->
                    if (doc != null) {
                        ResenaCard(doc, indexResena, navController, "Reseña")
                        indexResena += 1
                    }
                    Divider(
                        color = bordePerfil,
                        thickness = 5.dp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

    }

}
