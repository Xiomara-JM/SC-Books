package com.example.sc_books.presentation.screens

import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.R
import com.example.sc_books.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
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
    val scrollState = rememberScrollState()
    Column (
        modifier = Modifier.padding(bottom = 30.dp)
            ){
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(weight = 1f, fill = false)

            //modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Text(
                text = "Escribe el título del libro",
                fontSize = 16.sp,
            )
            val inputTituloLibro = remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = inputTituloLibro.value,
                onValueChange = { inputTituloLibro.value = it },
                //label = { Text(text = "Título del Libro") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                trailingIcon = {
                    Icon(
                        painterResource(id = R.drawable.ic_baseline_search_24),
                        contentDescription = ""
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Text(
                text = "Subir fotografía desde galeria",
                fontSize = 16.sp,
            )

            OutlinedButton(
                onClick = {

                    //mContext.startActivity(Intent(mContext, RegisterActivity::class.java))
                },
                contentPadding = PaddingValues(horizontal = 48.dp),
                modifier = Modifier.width(220.dp),
            ) {

                Icon(imageVector = Icons.Default.ImageSearch, contentDescription = null)
            }
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),

                contentDescription = null,
                Modifier
                    .width(80.dp)
                    .padding(bottom = 32.dp, top = 32.dp),
                alignment = Alignment.Center,
            )
            Text(
                text = "Texto en claro (Cita)",
                fontSize = 16.sp,
            )
            val inputTextCita = remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = inputTextCita.value,
                onValueChange = { inputTextCita.value = it },
                //label = { Text(text = "Texto en claro (Cita)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                maxLines = 3
            )

            Text(
                text = "Realiza el primer comentario",
                fontSize = 16.sp,
            )
            val inputComentario = remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = inputComentario.value,
                onValueChange = { inputComentario.value = it },
                //label = { Text(text = "Texto en claro (Cita)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 3
            )
            Button(
                onClick = {
                    //mContext.startActivity(Intent(mContext, MainActivity::class.java))
                },
                modifier = Modifier
                    .width(220.dp)
                    .padding(top = 24.dp)
            ) {
                Text(text = "Subir Post de Cita")
            }





            /*val inputNameState = remember { mutableStateOf(TextFieldValue()) }
        TextField(
            value = inputNameState.value,
            onValueChange = { inputNameState.value = it },
            /* label = { Text(text = "S/",
                      // fontSize = 24.sp,
                      textAlign = TextAlign.Left,
                )}, */
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .width(200.dp)
                .height(100.dp)
                .padding(bottom = 24.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent
            ),
            singleLine = true,
            trailingIcon = {
                Icon(painterResource(id = R.drawable.ic_baseline_search_24 ), contentDescription = "")
            },
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colors.onSurface,
                fontSize = 40.sp,
                textAlign = TextAlign.Center
            )
        )*/
        }
    }
}

@Composable
fun citaCamara() {
    Column(
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Text(
            text = "Crear una Nueva Cita desde Cámara",
            modifier = Modifier
                .padding(vertical = 5.dp),
            fontSize = 16.sp
        )
    }
}
