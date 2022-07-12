
package com.example.sc_books.presentation.screens

import android.Manifest
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
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.R
import com.example.sc_books.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.sc_books.ui.theme.LB50_400
import com.example.sc_books.ui.theme.LB50_900
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.IOException


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
                                fontSize = 16.sp,
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

    var imagenBitmap by rememberSaveable{ mutableStateOf<Bitmap?>(null)}
    var imagenUri by rememberSaveable{ mutableStateOf<Uri?>(null)}
    var escaneo by rememberSaveable{mutableStateOf("")}
    var inputTextCita = remember{ mutableStateOf(TextFieldValue()) }

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
            TomarImagen(onImageCapture = {imagenBitmap = it}, onUriCapture = {imagenUri = it})
            /*Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),

                contentDescription = null,
                Modifier
                    .width(80.dp)
                    .padding(bottom = 32.dp, top = 32.dp),
                alignment = Alignment.Center,
            )*/
            EscanearImagen(imagenBitmap,  imagenUri, escribir = {inputTextCita.value = it})
            Text(
                text = "Texto en claro (Cita)",
                fontSize = 16.sp,
            )
            //val inputTextCita = remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                //value = inputTextCita.value,
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
fun EscanearImagen(imagenBitmap: Bitmap?, imagenUri: Uri?, escribir: (TextFieldValue) -> Unit) {
    val context = LocalContext.current
    //val recognizer = remember(mutableStateOf(TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)))
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    imagenBitmap?.let { btm ->
        val image = InputImage.fromBitmap(btm, 0)

        val result = recognizer.process(image)
            .addOnSuccessListener { visionText ->
                escribir(TextFieldValue(visionText.text))
                //Log.d("Estado", visionText.text)
                //Toast.makeText(context, "Ya se verificó! ${visionText.text} .", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.d("Estado", e.message.toString())
                //Toast.makeText(context, "Aún no!", Toast.LENGTH_SHORT).show()
            }
    }
    imagenUri?.let { uri ->
        var btm by  remember {
            mutableStateOf<Bitmap?>(null)
        }
        if (Build.VERSION.SDK_INT < 28) {
            btm = MediaStore.Images
                .Media.getBitmap(context.contentResolver,uri)

        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver,uri)
            btm = ImageDecoder.decodeBitmap(source)
        }

        btm?.let {  bitmaImage ->
            Image(
                bitmap = bitmaImage.asImageBitmap(),
                contentDescription =null,
                modifier = Modifier.size(400.dp)
            )
        }

        //val image = InputImage.fromBitmap(btm, 0)
        val image: InputImage
        try {
            image = InputImage.fromFilePath(context, uri)
            val result = recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    escribir(TextFieldValue(visionText.text))
                    //Log.d("Estado", visionText.text)
                    //Toast.makeText(context, "Ya se verificó! ${visionText.text} .", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.d("Estado", e.message.toString())
                    //Toast.makeText(context, "Aún no!", Toast.LENGTH_SHORT).show()
                }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

@Composable
fun citaCamara() {
    //TextRead()
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

@Composable
fun TomarImagen(onImageCapture: (Bitmap?) -> Unit, onUriCapture: (Uri?) -> Unit) {

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    var bitmapDos by  remember {
        mutableStateOf<Bitmap?>(null)
    }

    val camaraOn = remember{mutableStateOf(false)}
    val galleryOn = remember { mutableStateOf(false)}

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        bitmapDos = null
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { btm: Bitmap? ->
        imageUri = null
        bitmapDos = btm
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            if (galleryOn.value){
                galleryLauncher.launch("image/*")
            }
            if (camaraOn.value){
                cameraLauncher.launch()
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
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context, Manifest.permission.CAMERA
                ) -> {
                    cameraLauncher.launch()
                }
                else -> {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }
        }) {
            Text(text = "Camara")
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

    if (galleryOn.value){
        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmapDos = MediaStore.Images
                    .Media.getBitmap(context.contentResolver,it)

            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver,it)
                bitmapDos = ImageDecoder.decodeBitmap(source)
            }

            onUriCapture(it)
            onImageCapture(null)
            bitmapDos?.let {  btm ->
                Image(
                    bitmap = btm.asImageBitmap(),
                    contentDescription =null,
                    modifier = Modifier.size(400.dp)
                )
            }
        }
    }

    if (camaraOn.value) {
        bitmapDos?.let { btm ->
            Image(
                bitmap = btm.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.size(400.dp)
            )
            onImageCapture(btm)
            onUriCapture(null)
        }
    }

}



