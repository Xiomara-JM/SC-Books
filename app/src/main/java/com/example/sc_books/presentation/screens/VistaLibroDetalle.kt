package com.example.sc_books.presentation.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import com.example.sc_books.model.Item
import com.example.sc_books.viewmodels.BookViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun VistaLibroDetalle(
    viewModel: BookViewModel,
    navHostController: NavController
) {
    val clickedBook by viewModel.clickedBook.collectAsState(initial = null)
    
    LazyColumn(
        modifier = Modifier
            .padding(top=10.dp, bottom=65.dp)
            /*.placeholder(visible = isLoading, highlight = PlaceholderHighlight.shimmer())*/
    ) {
        item {
            clickedBook?.let { item ->
                //if value is not null isLoading is false
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RectangleShape,
                ) {
                    Column {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp,0.dp,15.dp,10.dp),
                        ) {
                            TitleAndAuthor(item)
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(top = 30.dp, start = 8.dp)
                            ) {
                                InfoTable(
                                    title = "Calificacion",
                                    value = item.volumeInfo.averageRating.toString()
                                )
                                Spacer(modifier = Modifier.padding(10.dp))
                                InfoTable(
                                    title = "Paginas",
                                    value = item.volumeInfo.pageCount.toString()
                                )
                                Spacer(modifier = Modifier.padding(10.dp))
                                InfoTable(title = "Idioma", value = item.volumeInfo.language)
                                Spacer(modifier = Modifier.padding(10.dp))
                            }
                            Spacer(modifier = Modifier.padding(10.dp))
                        }
                        val html = item.volumeInfo.description ?: "No encontrada"
                        val description =
                            HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
                        InfoContent(title = "Descripcion", content = description.toString())
                        Spacer(modifier = Modifier.padding(10.dp))
                        val categories =
                            item.volumeInfo.categories?.joinToString("")
                                ?: "No especificada"
                        InfoContent(title = "Categorias", content = categories)
                        Spacer(modifier = Modifier.padding(10.dp))
                        InfoContent(title = "Editorial", content = item.volumeInfo.publisher)
                        Spacer(modifier = Modifier.padding(10.dp))
                        InfoContent(
                            title = "Fecha de publicacion",
                            content = item.volumeInfo.publishedDate
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TitleAndAuthor(it: Item) {
    val imageLink = it.volumeInfo.imageLinks
    val actualImageModifier = Modifier
        .width(150.dp)
        .height(250.dp)
        .shadow(
            elevation = 16.dp,
            clip = true,
            shape = RoundedCornerShape(16.dp)
        )
    val placeholderModifier = Modifier.size(200.dp)
    BookImage(
        imageLink = imageLink,
        ActualImageModifier = actualImageModifier,
        PlaceHolderModifier = placeholderModifier
    )
    Column(
        modifier = Modifier.padding(top = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = it.volumeInfo.title,
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
    val authors =
        if (it.volumeInfo.authors != null) it.volumeInfo.authors.joinToString(
            ","
        ) else "Autor Desconocido"

    Text(
        text = "Escrito por: $authors",
        style = MaterialTheme.typography.caption,
        modifier = Modifier
            .padding(top = 5.dp)
    )
}

@Composable
fun InfoTable(title: String, value: String) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.overline,
            modifier = Modifier.padding(2.dp),
        )
        Text(
            text = value,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun InfoContent(title: String, content: String) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(start = 8.dp),
        )
        Text(
            text = "\t$content",
            style = MaterialTheme.typography.caption,
            fontSize = 13.sp,
            modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 5.dp)
        )
    }
}