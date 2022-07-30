package com.example.sc_books.presentation.screens

import android.content.Intent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.R


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import com.example.sc_books.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.rounded.ClearAll
//import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.sc_books.firebase.models.Comentario
import com.example.sc_books.firebase.models.Review
import com.example.sc_books.model.ImageLinks
import com.example.sc_books.presentation.MainActivity
import com.example.sc_books.ui.theme.LB50_900
import com.example.sc_books.ui.theme.Purple200
import com.example.sc_books.ui.theme.SCBooksTheme

import com.example.sc_books.ui.theme.bordePerfil
import com.google.gson.Gson
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ComentariosPosts(navController: NavHostController) {
    var comentariosList = listOf(
        Comentario(
            "1",
            "nombre Imagen",
            "@UsuarioLec",
            "Comentrio Comentrio Comentrio Comentrio Comentrio Comentrio Reseña"
        ),
        Comentario(
            "1",
            "nombre Imagen",
            "@UsuarioLec",
            "Comentrio Comentrio Comentrio Comentrio Comentrio Comentrio Reseña"
        ),
        Comentario(
            "1",
            "nombre Imagen",
            "@UsuarioLec",
            "Comentrio Comentrio Comentrio Comentrio Comentrio Comentrio Reseña"
        ),
                Comentario(
                "1",
        "nombre Imagen",
        "@UsuarioLec",
                    "Comentrio Comentrio Comentrio Comentrio Comentrio Comentrio Reseña"
    ),
    Comentario(
        "1",
        "nombre Imagen",
        "@UsuarioLec",
        "Reseña"
    ),
    Comentario(
        "1",
        "nombre Imagen",
        "@UsuarioLec",
        "Reseña"
    )
    )



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(top = 24.dp, bottom=48.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column() {
            Card(
                elevation = 0.dp,
                modifier = Modifier
                    .fillMaxWidth(),
                //.padding(vertical = 8.dp)

                shape = RoundedCornerShape(16.dp),

                backgroundColor = Color.White


            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 10.dp,
                            //end = 10.dp,
                            bottom = 10.dp
                        )
                ) {


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                        // .padding(end = 8.dp)
                    ) {

                        Column(

                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                //horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    modifier = Modifier
                                        .border(
                                            24.dp,
                                            shape = CircleShape,
                                            color = Color.White.copy(alpha = 0.5f)
                                        )
                                        .padding(end = 1.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_launcher_foreground), //Foto de perfil de usuario
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .border(1.5.dp, bordePerfil, CircleShape)

                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween


                                ) {
                                    Column {
                                        Text(
                                            text = "USUARIO CREADOR",
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                        )

                                    }

                                    Box(
                                        modifier = Modifier
                                            .border(
                                                24.dp,
                                                shape = CircleShape,
                                                color = Color.White.copy(alpha = 0.5f)
                                            )
                                            .padding(all = 4.dp)
                                    ) {

                                    }

                                }
                            }

                        }
                    }


                    Text(
                        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum"
                    )


                }
            }
        }
        Row{
            Text(
                text= "Comentarios ",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
            Text(
                text= " 14",
                fontSize = 16.sp,
            )
        }



        Divider(
            color = Color.LightGray ,
            thickness = 2.dp,
            modifier = Modifier.fillMaxWidth()
        )



        var index = 0
        val listState = rememberLazyListState()

        /* when (depositViewModel.loadingSpinner.value) {
        true -> CircularProgressIndicator()
        false -> Unit
    } */
        val inputComentario = remember { mutableStateOf(TextFieldValue()) }

            OutlinedTextField(
                value = inputComentario.value,
                onValueChange = { inputComentario.value = it },
                textStyle = TextStyle(fontSize = 14.sp),
                /* colors = TextFieldDefaults.textFieldColors(
                 focusedIndicatorColor = Color.Black ,
                 unfocusedIndicatorColor = Color.Red,
                 backgroundColor = Color.Blue,
                 cursorColor = Color.DarkGray
             ) */

                trailingIcon = {
                    IconButton(onClick = {
                        //Se agraga comentario
                    }) {
                        Icon(Icons.Filled.Send , contentDescription = "")
                    }
                },

                modifier = Modifier
                    .padding(10.dp)
                    .background(Color(0xFFE7F1F1), RoundedCornerShape(16.dp))
                    .fillMaxWidth(),
                /*placeholder = { Text(text = "Escriba su busqueda") },*/
                label = {
                    Text(text = "Escribe tu comentario",
                        color = Purple200
                    )
                },

                )


        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            state = listState
        ) {

            items(comentariosList) {
                    deposit ->
                if (deposit != null) {

                    ComentarioCard(deposit, index, navController)
                    index += 1
                }

                Divider(
                    color = Color.White,
                    thickness = 5.dp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ComentarioCard(review: Comentario, index: Int, navController: NavHostController) {
    var isFavorite by remember { mutableStateOf(false) }
    val gson = Gson()
    var expandedState by remember { mutableStateOf(false) }
    val linesState by animateIntAsState(targetValue = if (expandedState) Int.MAX_VALUE else 3)

    Card (
        elevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            //.padding(vertical = 8.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = RoundedCornerShape(16.dp),

        backgroundColor = Color(247, 250, 249, 255)


    ) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 10.dp,
                //end = 10.dp,
                bottom = 10.dp
            )){


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                // .padding(end = 8.dp)
            ) {

                Column(

                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        //horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .border(
                                    24.dp,
                                    shape = CircleShape,
                                    color = Color.White.copy(alpha = 0.5f)
                                )
                                .padding(end = 1.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_foreground), //Foto de perfil de usuario
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .border(1.5.dp, bordePerfil, CircleShape)

                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween


                        ) {
                            Column {
                                Text(
                                    text = review.user_name,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = getTextColor(index)
                                )

                                Text(
                                    text= review.comentario_content,
                                    )


                            }

                            Box(
                                modifier = Modifier
                                    .border(
                                        24.dp,
                                        shape = CircleShape,
                                        color = Color.White.copy(alpha = 0.5f)
                                    )
                                    .padding(all = 4.dp)
                            ) {

                            }

                        }
                    }

                }
            }










        }

    }


}
