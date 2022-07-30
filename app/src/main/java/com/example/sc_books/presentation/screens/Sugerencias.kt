package com.example.sc_books.presentation.screens

import android.widget.Toast
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
import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
//import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.dataStore
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.sc_books.datastore.Preferencias
import com.example.sc_books.firebase.models.Review
import com.example.sc_books.ui.theme.LB50_900
import com.example.sc_books.ui.theme.SCBooksTheme

import com.example.sc_books.ui.theme.bordePerfil
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

@Composable
fun Sugerencias(navController: NavHostController) {
    var postsSugerenciasList = listOf(
        Review(
            "1",
            "nombre Imagen",
            "@UsuarioLec",
            "Reseña",
            1,
            "Alicia en el Pais de las Maravillas",
            "Realmente me parece una historia fascinante, desde una perspectiva fantastica y muy divertida. En lo particulas me encanto en personaje de  el gato , ya que me parece un personaje muy interesante y sospechoso .....",
            "direccionImagenLibro"
        ),
        Review(
            "1",
            "nombre Imagen",
            "@UsuarioLec123",
            "Cita",
            1,
            "Alicia en el Pais de las Maravillas",
            "Realmente me parece una historia fascinante, desde una perspectiva fantastica y muy divertida .....",
            "direccionImagenLibro"
        ),
        Review(
            "1",
            "nombre Imagen",
            "@Comegoma",
            "Reseña",
            1,
            "Alicia en el Pais de las Maravillas",
            "Realmente me parece una historia fascinante, desde una perspectiva fantastica y muy divertida. En lo particulas me encanto en personaje de  el gato , ya que me parece un personaje muy interesante y sospechoso.....",
            "direccionImagenLibro"
        ),
        Review(
            "1",
            "nombre Imagen",
            "@Abc123",
            "Cita",
            1,
            "Alicia en el Pais de las Maravillas",
            "Realmente me parece una historia fascinante, desde una perspectiva fantastica y muy divertida. En lo particulas me encanto en personaje de  el gato , ya que me parece un personaje muy interesante y sospechoso .....",
            "direccionImagenLibro"
        ),
        Review(
            "1",
            "nombre Imagen",
            "@Usertttt",
            "Cita",
            1,
            "Alicia en el Pais de las Maravillas",
            "Realmente me parece una historia fascinante.",
            "direccionImagenLibro"
        ),
        Review(
            "1",
            "nombre Imagen",
            "@LikeLibros",
            "Reseña",
            1,
            "Alicia en el Pais de las Maravillas",
            "Realmente me parece una historia fascinante, desde una perspectiva fantastica y muy divertida. En lo particulas me encanto en personaje de  el gato , ya que me parece un personaje muy interesante y sospechoso .....",
            "direccionImagenLibro"
        ),

        )
    var listado = remember { mutableListOf<Resenia>() }
    var mostrar = remember { mutableStateOf(false) }
    var imagen = remember { mutableStateOf("") }
    val fav = remember{ mutableStateOf(false)}

    //DataStore
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = Preferencias(context)
    val dbf = FirebaseFirestore.getInstance()
    val tag2 = dataStore.getTag.collectAsState(initial = "").toString()
    val tag = remember{ mutableStateOf(tag2)}



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
            text = "@" + dataStore.getNombre.collectAsState(initial = "").value + " !!", //purse.name
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

        /*LazyColumn(
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
        }*/

        dbf.collection("resenias").limit(25).get()
            .addOnSuccessListener {
                var cont = 0;
                if (listado.size < it.size()) {
                    for (resen in it) {

                        //var imagen: String
                        dbf.collection("libros").document("book_id").get()
                            .addOnSuccessListener { document ->
                                if (document != null) {
                                    imagen.value = document.data?.get("imagen").toString()
                                } else {
                                    imagen.value =
                                        "https://edit.org/images/cat/portadas-libros-big-2019101610.jpg"
                                }

                            }
                        dbf.collection("favoritos").document(tag.value+resen.id).get()
                            .addOnCompleteListener{cor->
                                if (cor.isSuccessful){
                                    if (cor.getResult().exists()){
                                        fav.value = true
                                    }
                                }
                            }

                        listado.add(
                            cont, Resenia(
                                resen.data?.get("autor_email").toString(),
                                resen.data?.get("autor_tag").toString(),
                                resen.data?.get("autor_username").toString(),
                                resen.data?.get("libro").toString(),
                                resen.data?.get("resenia").toString(),
                                resen.data?.get("book_id").toString(),
                                imagen.value,
                                fav,
                                resen.id,
                                tag.value
                            )
                        )
                        cont++


                    }
                }
                mostrar.value = true
            }
        if (mostrar.value){
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                state = listState
            ) {
                Thread.sleep(2000)
                items(listado) { doc ->
                    if (doc != null) {
                        PurseCard(doc, index, navController)
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

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PurseCard(review: Resenia, index: Int, navController: NavHostController) {
    var isFavorite by remember { mutableStateOf(false) }
    val gson = Gson()
    var expandedState by remember { mutableStateOf(false) }
    val linesState by animateIntAsState(targetValue = if (expandedState) Int.MAX_VALUE else 3)
    //DataStore
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = Preferencias(context)
    val dbf = FirebaseFirestore.getInstance()
    val tag2 = dataStore.getTag.collectAsState(initial = "").value
    val email = dataStore.getEmail.collectAsState(initial = "").value
    val tag = remember{ mutableStateOf(tag2)}
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 90f else 0f
    )
    Card(
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
                                    text = "@" + review.autor_username + "#" + review.autor_tag,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = getTextColor(index)
                                )
                                Text(
                                    text = "reseña", //text = review.review_type_name,
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
                text = "#" + review.libro,
                color = LB50_900
            )

            Text(
                text = review.resenia,
                maxLines = if (expandedState) Int.MAX_VALUE else 3,


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

            Row(modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
            {

                Image(
                    painter = rememberAsyncImagePainter("http://books.google.com/books/content?id=z2hczgEACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api"),
                    contentDescription = "",
                    modifier = Modifier
                        .size(120.dp)
                        .align(alignment = Alignment.CenterVertically),
                )
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Card(
                    elevation = 0.dp,
                ) {

                    Row {
                        Icon(
                            Icons.Filled.Favorite,

                            contentDescription = "",


                            modifier = Modifier
                                .width(28.dp)
                                .clickable {
                                    review.favorito.value = !review.favorito.value
                                    if (review.favorito.value){
                                        dbf.collection("favoritos").document(review.tag_user+review.id).set(
                                            hashMapOf(
                                                "favorito" to true
                                            )
                                        )
                                    }
                                    else{
                                        //Toast.makeText(context,email,Toast.LENGTH_SHORT).show()
                                        dbf.collection("favoritos").document(review.tag_user+review.id).delete()
                                    }
                                },
                            tint = getTextColorFav(review.favorito.value),

                            )
                        Text(
                            text = "Me encanta",
                            modifier = Modifier
                                .padding(vertical = 5.dp),

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
                            }*/
                            fontSize = 12.sp,
                            color = LB50_900,

                            )
                    }
                }
                Card(
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
                                .padding(vertical = 5.dp),

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
                            }*/
                            fontSize = 12.sp,
                            color = LB50_900,

                            )
                    }
                }
                Card(
                    elevation = 0.dp,
                    onClick = {
                        navController.navigate("libro_resena_cita") {
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
                                .padding(vertical = 5.dp),

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
                            }*/
                            fontSize = 12.sp,
                            color = LB50_900,

                            )
                    }
                }
            }


        }

    }

}
/*
@Composable
    fun BookImage(
        imageLink: ImageLinks?,
        ActualImageModifier: Modifier,
        PlaceHolderModifier: Modifier
    ) {
        if (imageLink == null) {
            Image(
                painter = rememberAsyncImagePainter("https://placeholder.of.today/150x230/b5edd4/a62dee&text=Image+Not+Available"),
                contentDescription = "",
                modifier = PlaceHolderModifier,
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(
                    imageLink.thumbnail.replace(
                        "http://",
                        "https://"
                    )
                ),
                contentDescription = "",
                modifier = ActualImageModifier,
                contentScale = ContentScale.Fit
            )
        }
    }
 */

fun getTextColorFav(index: Boolean): Color {
    if (index == false)
        return Color.Gray
    else
        return Color.Red

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
fun PreviewComponent() {
    SCBooksTheme {
        com.example.sc_books.presentation.screens.Sugerencias(
            navController = NavHostController(
                LocalContext.current
            )
        )
    }
}

@Composable
fun LinearProgressIndicatorSample() {
    var progress by remember { mutableStateOf(0.1f) }
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(Modifier.height(30.dp))
        Text("LinearProgressIndicator with undefined progress")
        LinearProgressIndicator()
        Spacer(Modifier.height(30.dp))
        Text("LinearProgressIndicator with progress set by buttons")
        LinearProgressIndicator(progress = animatedProgress)
        Spacer(Modifier.height(30.dp))
        OutlinedButton(
            onClick = {
                if (progress < 1f) progress += 0.1f
            }
        ) {
            Text("Increase")
        }

        OutlinedButton(
            onClick = {
                if (progress > 0f) progress -= 0.1f
            }
        ) {
            Text("Decrease")
        }
    }
}

data class Resenia(
    val autor_email: String,
    val autor_tag: String,
    val autor_username: String,
    val libro: String,
    val resenia: String,
    val book_id: String,
    val imagen: String,
    val favorito: MutableState<Boolean>,
    val id: String,
    val tag_user: String
)