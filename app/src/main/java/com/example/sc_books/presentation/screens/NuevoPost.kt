package com.example.sc_books.presentation.screens

import android.content.Intent
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.animation.Animatable

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.R
import com.example.sc_books.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

import com.example.sc_books.presentation.MainActivity
import com.example.sc_books.presentation.screens.Buscador
import com.example.sc_books.ui.theme.*
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.key.Key.Companion.Sleep
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.core.content.FileProvider
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import com.example.sc_books.datastore.Preferencias
import com.example.sc_books.presentation.showAlert
import com.example.sc_books.ui.theme.*

import com.example.sc_books.viewmodels.BookViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.io.File

import java.io.IOException


@Composable
fun NuevoPost(
    navController: NavHostController,
    navSug: () -> Unit
) {
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

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PopupWindowDialog(navController: NavHostController) {
    var openDialog = remember { mutableStateOf(true) }
    var openNuevaResena = remember { mutableStateOf(false) }
    var openCitaGaleria = remember { mutableStateOf(false) }
    var openCitaCamara = remember { mutableStateOf(false) }

    val tabItems = listOf("Nueva reseña", "Nueva Cita")
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
        HorizontalPager(
            count = tabItems.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .background(Purple200)
        ) { page ->
            if (page == 0) {
                Column(
                    modifier = Modifier
                        //.fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    nuevaResena()
                }

            } else {
                Column(
                    modifier = Modifier
                        //.fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    nuevaCita(hiltViewModel())
                }
            }

        }
    }


    /*Column(
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
                nuevaCita()
            }
        }
    }*/
}

@Composable
fun MyDialog(onClose: () -> Unit, viewModel: BookViewModel) {
    var readOnly by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier.fillMaxHeight(0.85f)
        ) {
            Box(
                contentAlignment = Alignment.TopCenter
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(11.dp, 5.dp, 10.dp, 0.dp)
                ) {
                    Text(
                        text = "Seleccione un libro",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Icon(
                        imageVector = Icons.Filled.Cancel,
                        contentDescription = "",
                        tint = colorResource(android.R.color.darker_gray),
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                            .clickable(onClick = {
                                viewModel.itemId.value = ""
                                onClose()
                            })
                    )
                }
                Box(modifier = Modifier.padding(15.dp, 35.dp, 15.dp, 60.dp)) {
                    DisplayResults(viewModel, readOnly)
                }
            }
            Box(
                modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 10.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = onClose,
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Purple700,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Text(text = "Aceptar")
                }
            }

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun nuevaResena(
    viewModel: BookViewModel = hiltViewModel()
) {
    //DataStore
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = Preferencias(context)
    val dbf = FirebaseFirestore.getInstance()
    val email = dataStore.getEmail.collectAsState(initial = "").value
    val username = dataStore.getNombre.collectAsState(initial = "").value
    val tag = dataStore.getTag.collectAsState(initial = "").value

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = "Crear una Nueva Reseña",
            modifier = Modifier
                .padding(15.dp)
                .align(alignment = CenterHorizontally),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = Purple700,
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Escribe el título del libro",
            fontSize = 14.sp
        )

        val txtFieldError = remember { mutableStateOf("") }
        var showDialog by remember { mutableStateOf(false) }
        val (query, onValueChange) = remember { mutableStateOf("") }
        val (author, setAuthor) = remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current

        if (showDialog) {
            if (query == "") {
            } else {
                Log.d("rawrwa", query)
                viewModel.getBooks(query)
                MyDialog(onClose = { showDialog = false }, viewModel)
            }
        }

        val bookId by viewModel.itemId.collectAsState("")
        viewModel.getBook(bookId)
        val book by viewModel.clickedBook.collectAsState(initial = null)
        if (!showDialog) {
            LaunchedEffect(bookId) {
                book?.let { it ->
                    onValueChange(it.volumeInfo.title)
                    setAuthor(
                        if (it.volumeInfo.authors != null) {
                            it.volumeInfo.authors.joinToString(",")
                        } else ("")
                    )
                }
            }
            /*viewModel.resetAll()*/
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = query,
                onValueChange = onValueChange,
                textStyle = TextStyle(fontSize = 14.sp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = Color.Transparent,
                    cursorColor = Color.DarkGray
                ),
                modifier = Modifier
                    .fillMaxWidth(0.78f)
                    /*.border(
                        BorderStroke(
                            width = 2.dp,
                            color = colorResource(
                                id = if (txtFieldError.value.isEmpty()) android.R.color.holo_green_light
                                    else android.R.color.holo_red_dark
                            )
                        ),
                        shape = RoundedCornerShape(50)
                    )*/
                    .background(Color(0xFFE7F1F1), RoundedCornerShape(10.dp)),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onSearch = {
                    keyboardController?.hide()
                }),
                singleLine = true,
                /*keyboardActions = KeyboardActions(onSearch = {

                })*/
            )
            Button(
                onClick = {
                    if (query.isEmpty()) {
                        showDialog = false
                        txtFieldError.value = "Campo obligatorio"
                        return@Button
                    } else {
                        showDialog = true
                    }
                    /*onValueChange(query)*/
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Purple700
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .padding(start = 7.dp),
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Autor del libro",
            fontSize = 14.sp
        )
        TextField(
            value = author,
            onValueChange = setAuthor,
            textStyle = TextStyle(fontSize = 14.sp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                cursorColor = Color.DarkGray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE7F1F1), RoundedCornerShape(10.dp)),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            /*keyboardActions = KeyboardActions(onSearch = {
                searchBook()
            })*/
        )


        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Reseña del libro",
            fontSize = 14.sp,
        )
        val inputTextCita = remember { mutableStateOf(TextFieldValue()) }
        OutlinedTextField(
            value = inputTextCita.value,
            onValueChange = { inputTextCita.value = it },
            //label = { Text(text = "Texto en claro (Cita)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color(0xFFE7F1F1), RoundedCornerShape(16.dp)),
            maxLines = 5,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                cursorColor = Color.DarkGray
            )
        )
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {
                if (query.isNullOrEmpty() || inputTextCita.value.text.isNullOrEmpty()) {
                    showAlert(context, 1)
                } else {
                    dbf.collection("libros").document(bookId).get()
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                if (!it.getResult().exists()) {
                                    dbf.collection("libros").document(bookId).set(
                                        hashMapOf(
                                            "nombre" to query.replace('/','-'),
                                            "info" to "información del libro",
                                            "book_id" to bookId,
                                            "imagen" to book?.volumeInfo?.imageLinks?.thumbnail
                                        )
                                    )
                                }
                                dbf.collection("resenias").document().set(
                                    hashMapOf(
                                        "libro" to query.replace('/','-'),
                                        "resenia" to inputTextCita.value.text,
                                        "autor_email" to email,
                                        "autor_username" to username,
                                        "autor_tag" to tag,
                                        "book_id" to bookId
                                    )
                                )
                            }
                        }
                }

                Log.d("Verificar", "$query  y ${inputTextCita.value.text}")
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Purple700,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(top = 9.dp)
                .fillMaxWidth(0.9f)
                .align(alignment = CenterHorizontally)
                .height(45.dp),
        ) {
            Text(text = "Subir Publicacion")
        }
    }
}

@Composable
fun nuevaCita(
    viewModel: BookViewModel
) {
    //DataStore
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = Preferencias(context)
    val dbf = FirebaseFirestore.getInstance()
    val email = dataStore.getEmail.collectAsState(initial = "").value
    val username = dataStore.getNombre.collectAsState(initial = "").value
    val tag = dataStore.getTag.collectAsState(initial = "").value

    var imagenBitmap by rememberSaveable { mutableStateOf<Bitmap?>(null) }
    var imagenUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var escaneo by rememberSaveable { mutableStateOf("") }
    var inputTextCita = remember { mutableStateOf(TextFieldValue()) }
    val mostrar = remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.padding(bottom = 30.dp)
    ) {
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
            /*val inputTituloLibro = remember { mutableStateOf(TextFieldValue()) }
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
            )*/

            val txtFieldError = remember { mutableStateOf("") }
            var showDialog by remember { mutableStateOf(false) }
            val (query, onValueChange) = remember { mutableStateOf("") }
            val (author, setAuthor) = remember { mutableStateOf("") }

            if (showDialog) {
                if (query == "") {
                } else {
                    viewModel.getBooks(query)
                    MyDialog(onClose = { showDialog = false }, viewModel)
                }
            }

            val bookId by viewModel.itemId.collectAsState("")
            viewModel.getBook(bookId)
            val book by viewModel.clickedBook.collectAsState(initial = null)
            if (!showDialog) {
                LaunchedEffect(bookId) {
                    book?.let { it ->
                        onValueChange(it.volumeInfo.title)
                        setAuthor(
                            if (it.volumeInfo.authors != null) {
                                it.volumeInfo.authors.joinToString(",")
                            } else ("")
                        )
                    }
                }
                viewModel.resetAll()
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = query,
                    onValueChange = onValueChange,
                    textStyle = TextStyle(fontSize = 14.sp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        backgroundColor = Color.Transparent,
                        cursorColor = Color.DarkGray
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.78f)
                        /*.border(
                            BorderStroke(
                                width = 2.dp,
                                color = colorResource(
                                    id = if (txtFieldError.value.isEmpty()) android.R.color.holo_green_light
                                        else android.R.color.holo_red_dark
                                )
                            ),
                            shape = RoundedCornerShape(50)
                        )*/
                        .background(Color(0xFFE7F1F1), RoundedCornerShape(10.dp)),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    /*keyboardActions = KeyboardActions(onSearch = {
                        searchBook()
                    })*/
                )
                Button(
                    onClick = {
                        if (query.isEmpty()) {
                            showDialog = false
                            txtFieldError.value = "Campo obligatorio"
                            return@Button
                        } else {
                            showDialog = true
                        }
                        /*onValueChange(query)*/
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Purple700
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .padding(start = 7.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Autor del libro",
                fontSize = 14.sp
            )
            TextField(
                value = author,
                onValueChange = setAuthor,
                textStyle = TextStyle(fontSize = 14.sp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = Color.Transparent,
                    cursorColor = Color.DarkGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE7F1F1), RoundedCornerShape(10.dp)),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                /*keyboardActions = KeyboardActions(onSearch = {
                    searchBook()
                })*/
            )


            Text(
                text = "Elija subir desde  galería o hacer una captura.",
                fontSize = 16.sp,
            )

            /*OutlinedButton(
                onClick = {

                    //mContext.startActivity(Intent(mContext, RegisterActivity::class.java))
                },
                contentPadding = PaddingValues(horizontal = 48.dp),
                modifier = Modifier.width(220.dp),
            ) {

                Icon(imageVector = Icons.Default.ImageSearch, contentDescription = null)
            }*/

            if (mostrar.value) {
                imagenUri?.let {
                    if (Build.VERSION.SDK_INT < 28) {
                        imagenBitmap = MediaStore.Images
                            .Media.getBitmap(context.contentResolver, it)

                    } else {
                        val source = ImageDecoder
                            .createSource(context.contentResolver, it)
                        imagenBitmap = ImageDecoder.decodeBitmap(source)
                    }

                    imagenBitmap?.let { btm ->
                        Image(
                            bitmap = btm.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.size(300.dp)
                        )
                    }
                }
            }


            /*TomarImagen(
                onImageCapture = { imagenBitmap = it },
                onUriCapture = { imagenUri = it })*/
            mayorCalidad(
                context = context,
                onUriCapture = { imagenUri = it },
                escribir = { inputTextCita.value = it })

            /*Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                Modifier
                    .width(80.dp)
                    .padding(bottom = 32.dp, top = 32.dp),
                alignment = Alignment.Center,
            )*/
            //EscanearImagen(imagenUri, escribir = { inputTextCita.value = it })
            Text(
                text = "Texto en claro (Cita)",
                fontSize = 16.sp,
            )
            //val inputTextCita = remember { mutableStateOf(TextFieldValue()) }
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
                    if (query.isNullOrEmpty() || inputTextCita.value.text.isNullOrEmpty() || author.isNullOrEmpty()) {
                        showAlert(context, 1)
                    } else {
                        dbf.collection("libros").document(bookId).get()
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    if (!it.getResult().exists()) {
                                        dbf.collection("libros").document(bookId).set(
                                            hashMapOf(
                                                "nombre" to query.replace('/','-'),
                                                "autor" to author,
                                                "book_id" to bookId
                                            )
                                        )
                                    }
                                    dbf.collection("citas").document(inputTextCita.value.text).get()
                                        .addOnCompleteListener { cita ->
                                            if (cita.isSuccessful) {
                                                if (!cita.getResult().exists()) {
                                                    dbf.collection("citas")
                                                        .document(inputTextCita.value.text).set(
                                                            hashMapOf(
                                                                "texto" to inputTextCita.value.text,
                                                                "libro" to query.replace('/','-'),
                                                                "autor" to author,
                                                                "book_id" to bookId
                                                            )
                                                        )
                                                }
                                                dbf.collection("comentarios").document().set(
                                                    hashMapOf(
                                                        "texto" to inputTextCita.value.text,
                                                        "libro" to query.replace('/','-'),
                                                        "autor" to author,
                                                        "comentario" to inputComentario.value.text,
                                                        "autor_email" to email,
                                                        "autor_username" to username,
                                                        "autor_tag" to tag,
                                                        "book_id" to bookId
                                                    )
                                                )
                                            }
                                        }
                                }
                            }
                    }
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
fun mayorCalidad(
    context: Context,
    onUriCapture: (Uri?) -> Unit,
    escribir: (TextFieldValue) -> Unit
) {
    val mCheckedState = remember { mutableStateOf(true) }
    var inputTextCita = remember { mutableStateOf(TextFieldValue()) }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val camaraOn = remember { mutableStateOf(false) }
    val editable = remember { mutableStateOf(false) }
    val galleryOn = remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    val resultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            if (galleryOn.value) {
                galleryLauncher.launch("image/*")
            }
            if (camaraOn.value) {
                imageUri = FileProvider.getUriForFile(
                    context,
                    context.packageName + ".provider",
                    File(context.filesDir, "picFromCamera")
                )
                resultLauncher.launch(imageUri)
            }
            Toast.makeText(context, "Permiso concedido!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permiso denegado!", Toast.LENGTH_SHORT).show()
        }
    }
    Row() {


        Button(onClick = {
            camaraOn.value = true
            galleryOn.value = false
            mCheckedState.value = false
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context, Manifest.permission.CAMERA
                ) -> {
                    imageUri = FileProvider.getUriForFile(
                        context,
                        context.packageName + ".provider",
                        File(context.filesDir, "picFromCamera")
                    )
                    resultLauncher.launch(imageUri)
                }
                else -> {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }
        }) {
            Text(text = "Capturar foto")
        }
        Button(onClick = {
            camaraOn.value = false
            galleryOn.value = true
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context, Manifest.permission.READ_EXTERNAL_STORAGE
                ) -> {
                    galleryLauncher.launch("image/*")
                }
                else -> {
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }) {
            Text(text = "Galeria")
        }
    }
    Row() {

        Button(onClick = {
            editable.value = true
            mCheckedState.value = false
            mCheckedState.value = true
            mCheckedState.value = false
        }) {
            Text(text = "Analizar")
        }
        if (editable.value) {
            Text(text = "Editar: ")
            Switch(checked = mCheckedState.value, onCheckedChange = { mCheckedState.value = it })
        }

    }

    EscanearImagen(imagenUri = imageUri, escribir = { inputTextCita.value = it })
    if (!mCheckedState.value) {
        escribir(TextFieldValue(inputTextCita.value.text))
        //Log.d("hola", inputTextCita.value.text)
    }


}


@Composable
fun EscanearImagen(imagenUri: Uri?, escribir: (TextFieldValue) -> Unit) {
    val context = LocalContext.current
    //val recognizer = remember(mutableStateOf(TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)))
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    imagenUri?.let { uri ->


        val image: InputImage
        try {
            image = InputImage.fromFilePath(context, uri)
            val result = recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    escribir(TextFieldValue(visionText.text))
                }
                .addOnFailureListener { e ->
                    Log.d("Estado", e.message.toString())
                }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}


