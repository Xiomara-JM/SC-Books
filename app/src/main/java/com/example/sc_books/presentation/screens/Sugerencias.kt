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

import com.example.sc_books.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
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
import com.example.sc_books.firebase.models.Review
import com.example.sc_books.presentation.MainActivity
import com.example.sc_books.ui.theme.LB50_900
import com.example.sc_books.ui.theme.SCBooksTheme

import com.example.sc_books.ui.theme.bordePerfil
import com.google.gson.Gson

@Composable
fun Sugerencias(navController: NavHostController){
    var postsSugerenciasList = listOf(
        Review("1","nombre Imagen","@UsuarioLec","Reseña",1,"Alicia en el Pais de las Maravillas","Realmente me parece una historia fascinante, desde una perspectiva fantastica y muy divertida. En lo particulas me encanto en personaje de  el gato , ya que me parece un personaje muy interesante y sospechoso .....","direccionImagenLibro"),
        Review("1","nombre Imagen","@UsuarioLec123","Cita",1,"Alicia en el Pais de las Maravillas","Realmente me parece una historia fascinante, desde una perspectiva fantastica y muy divertida .....","direccionImagenLibro"),
        Review("1","nombre Imagen","@Comegoma","Reseña",1,"Alicia en el Pais de las Maravillas","Realmente me parece una historia fascinante, desde una perspectiva fantastica y muy divertida. En lo particulas me encanto en personaje de  el gato , ya que me parece un personaje muy interesante y sospechoso.....","direccionImagenLibro"),
        Review("1","nombre Imagen","@Abc123","Cita",1,"Alicia en el Pais de las Maravillas","Realmente me parece una historia fascinante, desde una perspectiva fantastica y muy divertida. En lo particulas me encanto en personaje de  el gato , ya que me parece un personaje muy interesante y sospechoso .....","direccionImagenLibro"),
        Review("1","nombre Imagen","@Usertttt","Cita",1,"Alicia en el Pais de las Maravillas","Realmente me parece una historia fascinante.","direccionImagenLibro"),
        Review("1","nombre Imagen","@LikeLibros","Reseña",1,"Alicia en el Pais de las Maravillas","Realmente me parece una historia fascinante, desde una perspectiva fantastica y muy divertida. En lo particulas me encanto en personaje de  el gato , ya que me parece un personaje muy interesante y sospechoso .....","direccionImagenLibro"),

    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(top = 24.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡¡ Que bueno tenerte por aquí",
            fontWeight = FontWeight.Bold,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            modifier = Modifier.width(260.dp)
        )
        Text(
            text = "@nombreDeUsuario !!", //purse.name
            fontWeight = FontWeight.Bold,
            color = if (isSystemInDarkTheme()) Color.White else MaterialTheme.colors.primaryVariant,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            modifier = Modifier.width(260.dp)
        )


        var index = 0
        val listState = rememberLazyListState()

        /* when (depositViewModel.loadingSpinner.value) {
            true -> CircularProgressIndicator()
            false -> Unit
        } */

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            state = listState
        ) {
            items(postsSugerenciasList) { deposit ->
                if (deposit != null) {
                    PurseCard(deposit, index, navController)
                    index += 1
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


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PurseCard(review: Review, index: Int, navController: NavHostController) {
    val gson = Gson()
    var expandedState by remember { mutableStateOf(false) }
    val linesState by animateIntAsState(targetValue = if (expandedState) Int.MAX_VALUE else 3)

    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 90f else 0f
    )
    Card (
        elevation = 0.dp,
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
        onClick = {
            expandedState = !expandedState
        },
        backgroundColor = when (index % 3) {
            0 -> Color.White
            1 -> Color.White
            else -> Color.White
        }
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

/*            IconButton(
                onClick = { },
                modifier = Modifier
                    .alpha(ContentAlpha.medium)
                    .rotate(rotationState)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chevron_right),
                    contentDescription = "",
                    tint = getTextColor(index)
                )
            } */



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
                                    text = review.review_type_name,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 14.sp,
                                    color = bordePerfil
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
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "",
                                    modifier = Modifier.width(28.dp),
                                    tint = Color.Black
                                )
                            }

                        }
                    }
                    if (expandedState) {
                        Text(
                            text = "TOTAL",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = getTextColor(index),
                            modifier = Modifier.padding(top = 12.dp, bottom = 16.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = {
                                    /*
                                navController.navigate("deposit".plus("/${gson.toJson(purse)}")) {
                                    navController.graph.startDestinationRoute?.let { screen_route ->
                                        popUpTo(screen_route) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }*/
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                            ) {
                                Text(
                                    text = "Depositar",
                                    color = getTextColor(index)
                                )
                            }
                            Button(
                                onClick = { /*TODO*/ },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                            ) {
                                Text(
                                    text = "Compartir código",
                                    color = getTextColor(index)
                                )
                            }
                        }
                    }
                }
            }

            Text(
                text = "#"+ review.book_name,
                color=LB50_900
            )

            Text(
                text= review.review_content,


            )

            Text(
                text = "Ver nás...",
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .clickable {

                    },
                fontSize = 12.sp,
                color = LB50_900,
                textDecoration = TextDecoration.Underline
            )


        }

    }
}

fun getTextColor(index: Int): Color {
    return when (index % 3) {
        0 -> Color.Black
        1 -> Color.Black
        else -> Color.Black
    }
}

@Preview
@Composable
fun  PreviewComponent(){
    SCBooksTheme {
        com.example.sc_books.presentation.screens.Sugerencias(navController = NavHostController(
            LocalContext.current))
    }
}