package com.example.sc_books.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import com.example.sc_books.ui.theme.LB50_400
import com.example.sc_books.ui.theme.LB50_900

@Composable
fun NuevoPost(
    navController: NavHostController,
    navSug: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PopupWindowDialog(navController)
        Text(
            text = "Nueva Publicacion",
            style = TextStyle(color = Color.Black, fontSize = 10.sp, fontWeight = FontWeight.Black)
        )
        Button(onClick = { navSug() }) {
            Text(text = "Mis publicaciones")
        }
    }
}

@Composable
fun PopupWindowDialog(navController: NavHostController) {
    var openDialog = remember { mutableStateOf(true)}
    var openNuevaResena = remember { mutableStateOf(false)}
    var openCitaGaleria = remember { mutableStateOf(false)}
    var openCitaCamara = remember { mutableStateOf(false)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            val popupWidth = 200.dp
            val popupHeight = 180.dp
            val cornerSize = 10.dp

            if(openDialog.value){
                Popup(
                    alignment = Alignment.Center,
                    properties = PopupProperties()
                ) {
                    Box(
                        modifier = Modifier
                            .size(popupWidth, popupHeight)
                            .padding(top = 5.dp)
                            .background(LB50_400, RoundedCornerShape(cornerSize))
                            .border(1.dp, LB50_900, RoundedCornerShape(cornerSize))
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ){
                            Text(
                                text ="Crear una Nueva Reseña",
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                                    .clickable {
                                        openDialog.value = !openDialog.value
                                        openNuevaResena.value = !openNuevaResena.value
                                    },
                                fontSize = 16.sp
                            )
                            Divider(modifier = Modifier.border(1.dp, LB50_900))
                            Text(
                                text = "Crear una Nueva Cita desde Galería",
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                                    .clickable {
                                        openDialog.value = !openDialog.value
                                        openCitaGaleria.value = !openCitaGaleria.value
                                    },
                                fontSize = 16.sp,

                            )
                            Divider(modifier = Modifier.border(1.dp, LB50_900))
                            Text(
                                text = "Crear una Nueva Cita con la Cámara",
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                                    .clickable {
                                        openDialog.value = !openDialog.value
                                        openCitaCamara.value = !openCitaCamara.value
                                    },
                                fontSize = 16.sp
                            )
                            Divider(modifier = Modifier.border(1.dp, LB50_900))
                            Text(
                                text = "SALIR",
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                                    .clickable {
                                        openDialog.value = !openDialog.value
                                        navController.popBackStack()
                                    },
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
            if(openNuevaResena.value)
            {
                nuevaResena()
            }
            if(openCitaGaleria.value)
            {
                citaGaleria()
            }
            if(openCitaCamara.value)
            {
                citaCamara()
            }
        }
    }
}

@Composable
fun nuevaResena() {
    Column(
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Text(
            text = "Crear una Nueva Reseña",
            modifier = Modifier
                .padding(vertical = 5.dp),
            fontSize = 16.sp
        )
    }
}

@Composable
fun citaGaleria() {
    Column(
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Text(
            text = "Crear una Nueva Cita desde Galería",
            modifier = Modifier
                .padding(vertical = 5.dp),
            fontSize = 16.sp
        )
    }
}

@Composable
fun citaCamara() {
    TextRead()
    /*Column(
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Text(
            text = "Crear una Nueva Cita desde Cámara",
            modifier = Modifier
                .padding(vertical = 5.dp),
            fontSize = 16.sp
        )
    }*/
}
