package com.example.sc_books.presentation

import android.content.Intent
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
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
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
@Preview
@Composable
fun LoginScreen(){


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
                    LoginForm()
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

    TextFieldWithIcons(
        name = "Nombres",
        placeholder = "Ingrese sus nombres",
        icon = Icons.Default.Create,
        keyboardType = KeyboardType.Text
    )
    Spacer(modifier = Modifier.height(8.dp))
    TextFieldWithIcons(
        name = "Apellidos",
        placeholder = "Ingrese sus apellidos",
        icon = Icons.Default.Create,
        keyboardType = KeyboardType.Text
    )
    Spacer(modifier = Modifier.height(8.dp))
    TextFieldWithIcons(
        name = "Nombre de usuario",
        placeholder = "Ingrese un nombre de usuario",
        icon = Icons.Default.AccountBox,
        keyboardType = KeyboardType.Text
    )
    Spacer(modifier = Modifier.height(8.dp))
    TextFieldWithIcons(
        name = "Correo Electrónico",
        placeholder = "Ingrese su Correo Electrónico",
        icon = Icons.Default.Email,
        keyboardType = KeyboardType.Email
    )
    Spacer(modifier = Modifier.height(8.dp))
    PasswordTextField()
    LabelledCheckbox(
        checked = checked.value,
        onCheckedChange = { checked.value = it },
        label = "Aceptar términos y condiciones"
    )
    Spacer(modifier = Modifier.height(8.dp))
    Button(onClick = {
        //your onclick code here
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
        Text(text = "Iniciar Sesión", Modifier.padding(start = 10.dp))
    }
}

@Composable
fun LoginForm() {
    //DataStore
    val mContext = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = Preferencias(mContext)

    TextFieldWithIcons(
        name = "Correo Electrónico",
        placeholder = "Ingrese su Correo Electrónico",
        icon = Icons.Default.Email,
        keyboardType = KeyboardType.Email
    )
    Spacer(modifier = Modifier.height(8.dp))
    PasswordTextField()
    Spacer(modifier = Modifier.height(8.dp))
    Button(onClick = {
        scope.launch {
            dataStore.saveEstadoSesion(true)
        }
        mContext.startActivity(Intent(mContext, MainActivity::class.java))
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
    Button(onClick = {
        //your onclick code here
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
}

@Composable
fun TextFieldWithIcons(
    name: String,
    placeholder: String,
    icon: ImageVector,
    keyboardType: KeyboardType
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
}

@Composable
fun PasswordTextField() {
    val focusManager = LocalFocusManager.current
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val showPassword = remember { mutableStateOf(false) }
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

@Composable
fun DefaultPreview2() {
    SCBooksTheme {
        LoginScreen()
    }
}




