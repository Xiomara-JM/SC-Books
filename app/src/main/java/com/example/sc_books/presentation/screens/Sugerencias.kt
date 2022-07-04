package com.example.sc_books.presentation.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sc_books.firebase.models.Review
import com.google.gson.Gson

@Composable
fun Sugerencias(navController: NavHostController){
    var postsSugerenciasList = listOf(
        Review("1",0,"Cumpleaños","Es para Juan", "sin nombre",20),
        Review("1",0,"Chanchita","Para un juguete", "sin nombre",20),
        Review("1",0,"Cuota TV","Lorem ipsum Lorem ipsum Lorem ipsum", "sin nombre",20),
        Review("1",0,"Campaña navideña","Lorem ipsum Lorem ipsum", "sin nombre",20),
        Review("1",0,"Ahorro Laptop","Lorem ipsum Lorem ipsum", "sin nombre",20),
        Review("1",0,"Ahorro cumpleaños Mamá","Lorem ipsum Lorem ipsum", "sin nombre",20),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(top = 48.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡¡ Que bueno tenerte por\n" +
                    "aquí",
            fontWeight = FontWeight.Bold,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
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
            }
        }

    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PurseCard(purse: Review, index: Int, navController: NavHostController) {
    val gson = Gson()
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 90f else 0f
    )
    Card (
        // elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
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
            0 -> Color(255, 240, 222)
            1 -> Color(226, 244, 240)
            else -> Color(255, 227, 233)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
            // .padding(end = 8.dp)
        ) {
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .alpha(ContentAlpha.medium)
                    .rotate(rotationState)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "",
                    tint = getTextColor(index)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, end = 12.dp, bottom = 12.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text(
                            text = purse.name,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = getTextColor(index)
                        )
                        Text(
                            text = purse.description,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 14.sp,
                            color = getTextColor(index)
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
                if (expandedState) {
                    Text(
                        text = "TOTAL: ${purse.sub_total}",
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
                                navController.navigate("deposit".plus("/${gson.toJson(purse)}")) {
                                    navController.graph.startDestinationRoute?.let { screen_route ->
                                        popUpTo(screen_route) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
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
        /* Row(modifier = Modifier.padding(all = 8.dp)) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .width(52.dp)
                    .height(52.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = CustomGray)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_celebration),
                    contentDescription = "",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Cuota cumpleaños Juan ",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.subtitle1
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_celebration),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, MaterialTheme.colors.primary, CircleShape)
                )
            }
        } */
    }
}

fun getTextColor(index: Int): Color {
    return when (index % 3) {
        0 -> Color(222, 188, 149)
        1 -> Color(222, 188, 149)
        else -> Color(222, 188, 149)
    }
}