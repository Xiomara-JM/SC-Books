package com.example.sc_books.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.Animatable
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
import androidx.compose.material.icons.filled.Search
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
import com.example.sc_books.firebase.models.Review
import com.example.sc_books.ui.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.gson.Gson
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun LibroResCita(navController: NavHostController) {

    val tabItems = listOf("Libro","Reseñas", "Cita")
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = Purple500,
            modifier = Modifier
                .padding(5.dp)
                .background(Color.Transparent)
                .clip(RoundedCornerShape(30.dp)),
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .pagerTabIndicatorOffset(
                            pagerState, tabPositions
                        )
                        .width(0.dp)
                        .height(0.dp)
                )
            }
        ) {
            tabItems.forEachIndexed { index, title ->

                val color = remember { Animatable(Purple700) }
                LaunchedEffect(pagerState.currentPage == index) {
                    color.animateTo(
                        if (pagerState.currentPage == index)
                            Color.White else Purple500
                    )
                }

                if(index==0){
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },

                        icon = { Icon(
                            Icons.Filled.MenuBook,
                            null,
                            tint = if (pagerState.currentPage == index) {
                                Purple700
                            } else {
                                Purple700
                            },



                            ) },

                        modifier = Modifier.background(
                            color = color.value,
                            shape = RoundedCornerShape(30.dp)
                        )
                    )
                }

                else {

                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = title,
                                style = if (pagerState.currentPage == index) {
                                    TextStyle(
                                        color = Purple700,
                                        fontSize = 18.sp
                                    )
                                } else {
                                    TextStyle(
                                        color = Purple700,
                                        fontSize = 16.sp
                                    )
                                }
                            )
                        },
                        modifier = Modifier.background(
                            color = color.value,
                            shape = RoundedCornerShape(30.dp)
                        )
                    )
                }




            }
        }

        HorizontalPager(
            count = tabItems.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) { page ->
            if (page == 0){
                Column (
                    modifier = Modifier
                        //.fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    PerfilLibro(navController)
                }

            }


            else if (page == 1){
                Column (
                    modifier = Modifier
                        //.fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Resenas(navController)
                }
            }
            else{
                Column (
                    modifier = Modifier
                        //.fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Citas(navController)
                }
            }

        }
    }


}


@Composable
fun PerfilLibro(navController: NavHostController) {
    Column() {
        Text(text = "TITULO DEL LIBRO ")
    }

}
@Composable
fun Resenas(navController: NavHostController ) {
    var resenas = listOf(
        Review("1","nombre Imagen","@UsuarioLec","Reseña",1,"Mismo nombre de libro","Realmente me parece una historia fascinante, desde una perspectiva fantastica y muy divertida. En lo particulas me encanto en personaje de  el gato , ya que me parece un personaje muy interesante y sospechoso .....","direccionImagenLibro"),
        Review("1","nombre Imagen","@UsuarioLec123","Reseña",1,"Mismo nombre de libro","Realmente me parece una historia fascinante, desde una perspectiva fantastica y muy divertida .....","direccionImagenLibro"),
        Review("1","nombre Imagen","@Comegoma","Reseña",1,"Mismo nombre de libro","Realmente me parece una historia fascinante, desde una perspectiva fantastica y muy divertida. En lo particulas me encanto en personaje de  el gato , ya que me parece un personaje muy interesante y sospechoso.....","direccionImagenLibro"),
        Review("1","nombre Imagen","@Abc123","Reseña",1,"Mismo nombre de libro","Realmente me parece una historia fascinante, desde una perspectiva fantastica y muy divertida. En lo particulas me encanto en personaje de  el gato , ya que me parece un personaje muy interesante y sospechoso .....","direccionImagenLibro"),
        Review("1","nombre Imagen","@Usertttt","Reseña",1,"Mismo nombre de libro","Realmente me parece una historia fascinante.","direccionImagenLibro"),
        Review("1","nombre Imagen","@LikeLibros","Reseña",1,"Mismo nombre de libro","Realmente me parece una historia fascinante, desde una perspectiva fantastica y muy divertida. En lo particulas me encanto en personaje de  el gato , ya que me parece un personaje muy interesante y sospechoso .....","direccionImagenLibro"),

        )

    var index = 0
    val listStateR = rememberLazyListState()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        state = listStateR
    ) {
        items(resenas) {
                resena ->
            if (resena != null) {
                ResenaCard(resena, index, navController)
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


@Composable
fun Citas(navController: NavHostController ) {
    var citas = listOf(
        Review("1","nombre Imagen","@UsuarioLec","Cita",1,"Mismo nombre de libro","Realmente me parece una historia fascinante, desde una perspectiva fantastica y muy divertida. En lo particulas me encanto en personaje de  el gato , ya que me parece un personaje muy interesante y sospechoso .....","direccionImagenLibro"),
        Review("1","nombre Imagen","@UsuarioLec123","Cita",1,"Mismo nombre de libro","Realmente me parece una historia fascinante, desde una perspectiva fantastica y muy divertida .....","direccionImagenLibro"),
        Review("1","nombre Imagen","@Comegoma","Cita",1,"Mismo nombre de libro","Realmente me parece una historia fascinante, desde una perspectiva fantastica y muy divertida. En lo particulas me encanto en personaje de  el gato , ya que me parece un personaje muy interesante y sospechoso.....","direccionImagenLibro"),
        Review("1","nombre Imagen","@Abc123","Cita",1,"Mismo nombre de libro","Realmente me parece una historia fascinante, desde una perspectiva fantastica y muy divertida. En lo particulas me encanto en personaje de  el gato , ya que me parece un personaje muy interesante y sospechoso .....","direccionImagenLibro"),
        Review("1","nombre Imagen","@Usertttt","Cita",1,"Mismo nombre de libro","Realmente me parece una historia fascinante.","direccionImagenLibro"),
        Review("1","nombre Imagen","@LikeLibros","Cita",1,"Mismo nombre de libro","Realmente me parece una historia fascinante, desde una perspectiva fantastica y muy divertida. En lo particulas me encanto en personaje de  el gato , ya que me parece un personaje muy interesante y sospechoso .....","direccionImagenLibro"),

        )

    var index = 0
    val listStateR = rememberLazyListState()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        state = listStateR
    ) {
        items(citas) {
                cita ->
            if (cita != null) {
                ResenaCard(cita, index, navController)
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


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ResenaCard (review: Review, index: Int, navController: NavHostController) {
    var isFavorite by remember { mutableStateOf(false) }
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

                            }

                        }
                    }

                }
            }

            Text(
                text = "#"+ review.book_name,
                color= LB50_900
            )

            Text(
                text= review.review_content,
                maxLines = if(expandedState) Int.MAX_VALUE else 3,


                )

            Text(
                text = "Ver nás...",
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .clickable {
                        expandedState = !expandedState


                    },
                fontSize = 12.sp,
                color = LB50_900,
                textDecoration = TextDecoration.Underline
            )




            Row(modifier=Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Card (
                    elevation = 0.dp,



                    ) {

                    Row {
                        Icon(
                            Icons.Filled.Favorite,

                            contentDescription = "",


                            modifier = Modifier
                                .width(28.dp)
                                .clickable {
                                    isFavorite = !isFavorite


                                },
                            tint = getTextColorFav(isFavorite),

                            )


                        Text(
                            text = "Me encanta",
                            modifier = Modifier
                                .padding(vertical = 5.dp)

                            /*.clickable {
                                navController.navigate("comentarios_posts") {
                                    navController.graph.startDestinationRoute?.let { screen_route ->
                                        popUpTo(screen_route) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }*/,
                            fontSize = 12.sp,
                            color = LB50_900,

                            )
                    }
                }
                Card (
                    elevation = 0.dp,
                    onClick = {

                        navController.navigate("comentarios_posts") {
                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }



                ) {

                    Row {
                        Icon(
                            painterResource(id = R.drawable.ic_baseline_comment_24),

                            contentDescription = "",
                            modifier = Modifier.width(28.dp),
                            tint = Color.Black
                        )
                        Text(
                            text = "Comentar",
                            modifier = Modifier
                                .padding(vertical = 5.dp)

                            /*.clickable {
                                navController.navigate("comentarios_posts") {
                                    navController.graph.startDestinationRoute?.let { screen_route ->
                                        popUpTo(screen_route) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }*/,
                            fontSize = 12.sp,
                            color = LB50_900,

                            )
                    }
                }
                Card (
                    elevation = 0.dp,
                    onClick = {

                        navController.navigate("comentarios_posts") {
                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }



                ) {

                    Row {
                        Icon(
                            Icons.Filled.MenuBook,
                            contentDescription = "",
                            modifier = Modifier.width(28.dp),
                            tint = Color.Black
                        )
                        Text(
                            text = "Ver Libro",
                            modifier = Modifier
                                .padding(vertical = 5.dp)

                            /*.clickable {
                                navController.navigate("comentarios_posts") {
                                    navController.graph.startDestinationRoute?.let { screen_route ->
                                        popUpTo(screen_route) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }*/,
                            fontSize = 12.sp,
                            color = LB50_900,

                            )
                    }
                }
            }




        }

    }


}


@Preview
@Composable
fun  PreviewComents(){
    SCBooksTheme {
        LibroResCita(navController = NavHostController(
            LocalContext.current))
    }
}