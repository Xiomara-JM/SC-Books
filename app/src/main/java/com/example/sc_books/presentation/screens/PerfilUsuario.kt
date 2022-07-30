package com.example.sc_books.presentation.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.layout.R
import com.example.sc_books.R
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sc_books.ui.theme.Purple700
import com.google.android.gms.tasks.Task


@Composable
fun PerfilUsuario(navController: NavHostController) {

    val mContext = LocalContext.current

    Column {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(weight = 1f, fill = false),
            verticalArrangement = Arrangement.Center

        ) {
            Box {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f)
                        .padding(top = 70.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Mi Perfil",
                        color = Color.Black,
                        fontSize = 14.sp, //36 2
                        fontWeight = FontWeight.W800,
                        textAlign = TextAlign.Center,

                        )
                    Row {
                        Text(
                            text = "user.fullname",
                            color = Color.Black,
                            fontSize = 22.sp, //32
                            fontWeight = FontWeight.W700,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .padding(bottom = 65.dp)

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 32.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_google),

                        contentDescription = null,
                        Modifier
                            .width(120.dp)
                            .padding(bottom = 32.dp, top = 32.dp),
                        alignment = Alignment.Center,
                    )

                    val inputNameState =
                        remember { mutableStateOf(TextFieldValue("@NombreUser")) }
                    OutlinedTextField(
                        value = inputNameState.value,
                        onValueChange = { inputNameState.value = it },
                        label = { Text(text = "Nombre de usuario") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 30.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedLabelColor = Purple700,
                            //textColor = headingColor,
                            unfocusedLabelColor = Purple700,
                            unfocusedIndicatorColor = Purple700,
                            focusedIndicatorColor = Purple700
                        ),
                    )
                    /*val inputPhoneState =
                        remember { mutableStateOf(TextFieldValue("ejemplo@unsa.edu")) }
                    OutlinedTextField(
                        value = inputPhoneState.value,
                        onValueChange = { inputPhoneState.value = it },
                        label = { Text(text = "Algun label2") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null

                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 15.dp)
                    )*/

                    Button(
                        onClick = {
                            //user.fullname = inputNameState.value.text
                            //user.email = inputPhoneState.value.text
                            //userViewModel.updateUser(user)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Purple700,
                            contentColor = Color.White)
                    ) {
                        Text(text = "Guardar Cambios")
                    }
                }


                    /*Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .width(220.dp)
                            .padding(vertical = 8.dp)
                    ) {
                        Divider(
                            color = Purple700,
                            thickness = 1.dp,

                            modifier = Modifier.width(40.dp)
                        )
                        Text(
                            text = "Actualizar Contraseña",
                            fontSize = 12.sp,
                            color = Purple700
                        )
                        Divider(
                            color = Purple700,
                            thickness = 1.dp,
                            modifier = Modifier.width(40.dp)
                        )
                    }

                    var password by rememberSaveable { mutableStateOf("") }
                    var passwordVisible by rememberSaveable { mutableStateOf(false) }
                    val keyboardController = LocalSoftwareKeyboardController.current

                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(text = "Contraseña actual") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword,
                            imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            keyboardController?.hide()
                        }),
                        visualTransformation = if (passwordVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff

                            // Please provide localized description for accessibility services
                            val description = if (passwordVisible) "Hide password" else "Show password"

                            IconButton(onClick = {passwordVisible = !passwordVisible}){
                                Icon(imageVector  = image, description)
                            }
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedLabelColor = Purple700,
                            *//*textColor = headingColor,*//*
                            unfocusedLabelColor = Purple700,
                            unfocusedIndicatorColor = Purple700,
                            focusedIndicatorColor = Purple700
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )

                    var newPassword by rememberSaveable { mutableStateOf("") }
                    var newPasswordVisible by rememberSaveable { mutableStateOf(false) }

                    TextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("Nueva Contraseña") },
                        visualTransformation = if (newPasswordVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword,
                            imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            keyboardController?.hide()
                        }),
                        trailingIcon = {
                            val image = if (newPasswordVisible)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff

                            // Please provide localized description for accessibility services
                            val description = if (newPasswordVisible) "Hide password" else "Show password"

                            IconButton(onClick = {newPasswordVisible = !newPasswordVisible}){
                                Icon(imageVector  = image, description)
                            }
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedLabelColor = Purple700,
                            *//*textColor = headingColor,*//*
                            unfocusedLabelColor = Purple700,
                            unfocusedIndicatorColor = Purple700,
                            focusedIndicatorColor = Purple700
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                    )

                    Button(
                        onClick = {
                            *//*
                            if (inputPwdState.value.text.isNotEmpty() && inputPwdRepeatState.value.text.isNotEmpty()) {
                                val auth: Task<Void> = AuthService.firebaseReauthenticationWithCredential(
                                    user.email,
                                    inputPwdState.value.text
                                )!!
                                auth.addOnCompleteListener{ task ->
                                    if (task.isSuccessful) {
                                        AuthService.firebaseGetCurrentUser()?.updatePassword(inputPwdRepeatState.value.text)
                                        Toast.makeText(mContext, "User Password Updated", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(mContext, "Password Updated Failed", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }else{
                                Toast.makeText(mContext, "Password must not be empty.", Toast.LENGTH_SHORT).show()
                            }
                            *//*
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Purple700,
                            contentColor = Color.White)
                    ) {
                        Text(text = "Actualizar Clave")
                    }

                }*/
            }
        }
    }
}