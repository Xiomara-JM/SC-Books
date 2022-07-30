package com.example.sc_books.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sc_books.datastore.Preferencias
import com.example.sc_books.ui.theme.bordePerfil
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun Favoritos(navController: NavHostController){

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
    val tagResenia = dataStore.getTag.collectAsState(initial = "").value
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

        if (tagResenia!= ""){
            dbf.collection("favoritosResena").whereEqualTo("tag", tagResenia).get()
                .addOnSuccessListener {
                    //var cont = 0;
                    if (listadoResenas.size < it.size()) {
                        for (resen in it) {
                            dbf.collection("resenias")
                                .document(resen.data?.get("reviewId").toString()).get()
                                .addOnCompleteListener { resena ->
                                    listadoResenas.add(
                                        contRes.value, Resenia(
                                            resena.result.data?.get("autor_email").toString(),
                                            resena.result.data?.get("autor_tag").toString(),
                                            resena.result.data?.get("autor_username").toString(),
                                            resena.result.data?.get("libro").toString(),
                                            resena.result.data?.get("resenia").toString(),
                                            resena.result.data?.get("book_id").toString(),
                                            resena.result.id,
                                            tagResenia,
                                            "Reseña"
                                        )
                                    )
                                }
                            contRes.value++

                        }
                        mostrarResenas.value = true
                    }
                    mostrarResenas.value = true

                }
        }

        if (tagCita!= ""){
            dbf.collection("favoritosCita").whereEqualTo("tag", tagCita).get()
                .addOnSuccessListener {
                    //var cont = 0;
                    if (listadoCitas.size < it.size()) {
                        for (resen in it) {
                            dbf.collection("citas")
                                .document(resen.data?.get("reviewId").toString()).get()
                                .addOnCompleteListener { resena ->
                                    listadoCitas.add(
                                        contCit.value, Resenia(
                                            resena.result.data?.get("autor_email").toString(),
                                            resena.result.data?.get("autor_tag").toString(),
                                            resena.result.data?.get("autor_username").toString(),
                                            resena.result.data?.get("libro").toString(),
                                            resena.result.data?.get("texto").toString(),
                                            resena.result.data?.get("book_id").toString(),
                                            resena.result.id,
                                            tagCita,
                                            "Cita"
                                        )
                                    )
                                }
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