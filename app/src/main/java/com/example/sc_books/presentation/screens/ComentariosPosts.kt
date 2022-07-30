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
import com.example.sc_books.firebase.models.Comentario
import com.example.sc_books.firebase.models.Review
import com.example.sc_books.ui.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.gson.Gson
import kotlinx.coroutines.launch


@Composable
fun ComentariosPosts(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(top = 24.dp, start = 24.dp, end = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(weight = 1f, fill = false)
        ) {
            Card(
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

                )
            {
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
                                            text = "@NombreUnsuario", //review.user_name
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,

                                        )
                                        Text(
                                            text = "reseña o cita",//review.review_type_name
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
                        text = "#"+ "review.book_name",
                        color= LB50_900
                    )

                    Text(
                        text= "Realmente me parece una historia fascinante, desde una perspectiva fantastica y muy divertida. En lo particulas me encanto en personaje de  el gato , ya que me parece un personaje muy interesante y sospechoso",

                        )
                }
            }
            Row() {
                Text(
                    text = "Comentarios ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
                Text(
                    text = " 14", //numero de comentarios
                    fontSize = 16.sp,
                )
            }




        }


    }
}

