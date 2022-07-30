package com.example.sc_books.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.Animatable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sc_books.R
import com.example.sc_books.datastore.Preferencias
import com.example.sc_books.ui.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

/*class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen()
        }
    }
}*/


@OptIn(ExperimentalPagerApi::class)
@Composable
fun LoginScreen(activity: Activity){
    val tabItems = listOf("Iniciar Sesión", "Crear una cuenta")
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
            if (page == 0){
                Column (
                    modifier = Modifier
                        //.fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    LoginForm(activity)
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
                    SignUpForm()
                }
            }

        }
    }



        
        /*Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black
                )
            )
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Purple700,
                    contentColor = Color.White),
                onClick = {
                    scope.launch {
                        dataStore.saveEstadoSesion(true)
                    }
                    mContext.startActivity(Intent(mContext, MainActivity::class.java))
                },
                modifier = Modifier
                    .width(220.dp)
                    .padding(top = 24.dp)
            ) {
                Text(text = "INICIAR")
            }
            Text(
                text = "... o puede ingresar sin usuario.",
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .clickable {
                        scope.launch {
                            dataStore.saveSkipSesion(true)
                        }
                        mContext.startActivity(Intent(mContext, MainActivity::class.java))
                    },
                fontSize = 12.sp,
                color = LB50_900,
                textDecoration = TextDecoration.Underline
            )
        }*/

}

@Composable
fun SignUpForm() {
    val checked = remember { mutableStateOf(true) }
    var nombres by rememberSaveable{mutableStateOf<String?>(null)}
    var apellidos by rememberSaveable{mutableStateOf<String?>(null)}
    var email by rememberSaveable{mutableStateOf<String?>(null)}
    var username by rememberSaveable{mutableStateOf<String?>(null)}
    var password by rememberSaveable{mutableStateOf<String?>(null)}

    val auth = Firebase.auth
    //DataStore
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = Preferencias(context)
    val dbf = FirebaseFirestore.getInstance()

    TextFieldWithIcons(
        name = "Nombres",
        placeholder = "Ingrese sus nombres",
        icon = Icons.Default.Create,
        keyboardType = KeyboardType.Text,
        getText = {nombres = it}
    )
    Spacer(modifier = Modifier.height(8.dp))
    TextFieldWithIcons(
        name = "Apellidos",
        placeholder = "Ingrese sus apellidos",
        icon = Icons.Default.Create,
        keyboardType = KeyboardType.Text,
        getText = {apellidos = it}
    )
    Spacer(modifier = Modifier.height(8.dp))
    TextFieldWithIcons(
        name = "Nombre de usuario",
        placeholder = "Ingrese un nombre de usuario",
        icon = Icons.Default.AccountBox,
        keyboardType = KeyboardType.Text,
        getText = {username = it}
    )
    Spacer(modifier = Modifier.height(8.dp))
    TextFieldWithIcons(
        name = "Correo Electrónico",
        placeholder = "Ingrese su Correo Electrónico",
        icon = Icons.Default.Email,
        keyboardType = KeyboardType.Email,
        getText = {email = it}
    )
    Spacer(modifier = Modifier.height(8.dp))
    PasswordTextField(getPassword = {password = it})
    LabelledCheckbox(
        checked = checked.value,
        onCheckedChange = { checked.value = it },
        label = "Aceptar términos y condiciones"
    )
    Spacer(modifier = Modifier.height(8.dp))
    Button(onClick = {
        if (nombres.isNullOrEmpty() || apellidos.isNullOrEmpty() ||
            username.isNullOrEmpty() || email.isNullOrEmpty() ||
            password.isNullOrEmpty() ) {
            showAlert(context, 1)
        }
        else{
            dbf.collection("usuarios").document(email?:"").get()
                .addOnCompleteListener{
                    if (it.isSuccessful){
                        if (!it.getResult().exists()){
                            auth.createUserWithEmailAndPassword(email?:"", password?:"")
                                .addOnCompleteListener{ task ->
                                    if (task.isSuccessful) {
                                        FirebaseFirestore.getInstance().collection("usuarios").get()
                                            .addOnSuccessListener { creados ->
                                                val cantidad = creados.size()
                                                val tag = (cantidad + 100000).toString()
                                                scope.launch {
                                                    dataStore.saveEstadoSesion(true)
                                                    dataStore.saveNombre(username?:"")
                                                    dataStore.saveEmail(email?:"")
                                                    dataStore.saveTag(tag)
                                                }
                                                dbf.collection("usuarios").document(email?:"").set(
                                                    hashMapOf(
                                                        "nombre" to nombres,
                                                        "apellidos" to apellidos,
                                                        "email" to email,
                                                        "username" to username,
                                                        "tag" to tag,
                                                        "tipo" to "basico"
                                                    )
                                                )
                                            }
                                        showAlert(context, 4)
                                        //context.startActivity(Intent(context, MainActivity::class.java))
                                    } else {
                                        showAlert(context, 2)
                                    }
                                }
                        }
                        else{
                            showAlert(context, 3)
                        }
                    }
                    else{
                        showAlert(context, 2)
                    }
                }


        }
    },
        elevation =  ButtonDefaults.elevation(
            defaultElevation = 10.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp
        ),
        border = BorderStroke(4.dp, Purple700),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black, backgroundColor = Purple500)
    ) {
        Image(
            painterResource(id = R.drawable.ic_signup),
            contentDescription = "Inicio de sesión",
            modifier = Modifier.size(20.dp)
        )
        Text(text = "Registrarse", Modifier.padding(start = 10.dp))
    }
}

fun showAlert(context: Context, i: Int) {
    if (i == 1){
        Toast.makeText(context, "Debe llenar todos los campos.", Toast.LENGTH_SHORT).show()
    }
    if (i == 2){
        Toast.makeText(context, "Se ha producido un error en el registro. Intente de nuevo.", Toast.LENGTH_SHORT).show()
    }
    if (i == 3){
        Toast.makeText(context, "Email ya registrado.", Toast.LENGTH_SHORT).show()
    }
    if (i == 4){
        Toast.makeText(context, "Registro exitoso. Se inició la sesión.", Toast.LENGTH_SHORT).show()
    }
    if (i == 5){
        Toast.makeText(context, "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show()
    }
    if (i == 6){
        Toast.makeText(context, "No se ha podido iniciar sesión.", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun LoginForm(activity: Activity) {
    //Form
    var email by rememberSaveable{mutableStateOf<String?>(null)}
    var password by rememberSaveable{mutableStateOf<String?>(null)}

    val auth = Firebase.auth
    //DataStore
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = Preferencias(context)
    val dbf = FirebaseFirestore.getInstance()

    TextFieldWithIcons(
        name = "Correo Electrónico",
        placeholder = "Ingrese su Correo Electrónico",
        icon = Icons.Default.Email,
        keyboardType = KeyboardType.Email,
        getText = {email = it}
    )
    Spacer(modifier = Modifier.height(8.dp))
    PasswordTextField(getPassword = {password = it})
    Spacer(modifier = Modifier.height(8.dp))
    Button(onClick = {
        if (email.isNullOrEmpty() || password.isNullOrEmpty() ) {
            showAlert(context, 1)
        }
        else {
            auth.signInWithEmailAndPassword(email ?: "", password ?: "")
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        FirebaseFirestore.getInstance().collection("usuarios")
                            .document(email?:"").get()
                            .addOnCompleteListener { usuario->
                                val tag = usuario.result.data?.get("tag").toString()
                                val username = usuario.result.data?.get("username").toString()
                                scope.launch {
                                    dataStore.saveEstadoSesion(true)
                                    dataStore.saveNombre(username)
                                    dataStore.saveEmail(email?:"")
                                    dataStore.saveTag(tag)
                                }
                                showAlert(context, 5)
                                //context.startActivity(Intent(context, MainActivity::class.java))
                            }
                    } else {
                        showAlert(context, 6)
                    }
                }
        }
    },
        elevation =  ButtonDefaults.elevation(
            defaultElevation = 10.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp
        ),
        border = BorderStroke(4.dp, Purple700),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black, backgroundColor = Purple500)
    ) {
        Image(
            painterResource(id = R.drawable.ic_login),
            contentDescription = "Inicio de sesión",
            modifier = Modifier.size(20.dp)
        )
        Text(text = "Iniciar Sesión", Modifier.padding(start = 10.dp))
    }
    Spacer(modifier = Modifier.height(8.dp))
    //Configuración
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(stringResource(R.string.default_web_client_id))
        .requestEmail()
        .build()
    Button(onClick = {
        val googleClient = GoogleSignIn.getClient(activity, gso)
        googleClient.signOut()
        val signInIntent: Intent = googleClient.signInIntent
        activity.startActivityForResult(signInIntent, 1)
    },
        elevation =  ButtonDefaults.elevation(
            defaultElevation = 10.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp
        ),
        border = BorderStroke(4.dp, Purple700),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black, backgroundColor = Purple500)
    ) {
        Image(
            painterResource(id = R.drawable.ic_google),
            contentDescription = "Inicio con Google",
            modifier = Modifier.size(20.dp)
        )
        Text(text = "Acceder con cuenta de Google", Modifier.padding(start = 10.dp))
    }
    /*Text(
        text = "... o puede ingresar sin usuario.",
        modifier = Modifier
            .padding(vertical = 5.dp)
            .clickable {
                scope.launch {
                    dataStore.saveSkipSesion(true)
                }
                context.startActivity(Intent(context, MainActivity::class.java))
            },
        fontSize = 12.sp,
        color = LB50_900,
        textDecoration = TextDecoration.Underline
    )*/
}

@Composable
fun TextFieldWithIcons(
    name: String,
    placeholder: String,
    icon: ImageVector,
    keyboardType: KeyboardType,
    getText:(String?) -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue(""))}
    OutlinedTextField(
        value = text,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        leadingIcon = { Icon(imageVector = icon, contentDescription = "icono de campo") },
        //trailingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
        onValueChange = {
            text = it
        },
        label = { Text(text = name) },
        placeholder = { Text(text = placeholder) }
    )
    getText(text.text)
}

@Composable
fun PasswordTextField(getPassword: (String?) -> Unit) {
    val focusManager = LocalFocusManager.current
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val showPassword = remember { mutableStateOf(false) }
    getPassword(text.text)
    OutlinedTextField(
        value = text, 
        onValueChange = {
            text = it
        },
        leadingIcon = { Icon(imageVector = Icons.Default.Password, contentDescription = "Contraseña")},
        label = { Text(text = "Contraseña")},
        placeholder = { Text(text = "Ingrese su contraseña")},
        keyboardOptions = KeyboardOptions.Default.copy(
            autoCorrect = true,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        singleLine = true,
        visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val (icon, iconColor) = if (showPassword.value) {
                Pair(
                    Icons.Filled.Visibility,
                    colorResource(id = R.color.purple_500)
                )
            } else {
                Pair(
                    Icons.Filled.VisibilityOff,
                    colorResource(id = R.color.purple_700)
                )
            }
            IconButton(onClick = { showPassword.value = !showPassword.value }) {
                Icon(
                    icon,
                    contentDescription = "Visibilidad",
                    tint = iconColor
                )
            }
        }
    )
}


@Composable
fun LabelledCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: CheckboxColors = CheckboxDefaults.colors()
) {
    Row(
        modifier = modifier.height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = colors
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = label,
            modifier = Modifier
                .padding(vertical = 5.dp)
                .clickable {
                    /* */
                },
            color = LB50_900,
            textDecoration = TextDecoration.Underline
        )
    }
}




