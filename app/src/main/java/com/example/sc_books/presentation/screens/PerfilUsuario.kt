package com.example.sc_books.presentation.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.R
import com.example.sc_books.R
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sc_books.ui.theme.Purple700
import com.google.android.gms.tasks.Task


@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun PerfilUsuario(navController: NavHostController) {

    val mContext = LocalContext.current

Column {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .weight(weight = 1f, fill = false)

    ) {
        Box {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
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
                    label = { Text(text = "Algun label") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                val inputPhoneState =
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
                )

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
                    colors = ButtonDefaults.buttonColors(backgroundColor = Purple700)
                ) {
                    Text(text = "Guardar Cambios")
                }


                Row(
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


                val inputPwdState = remember { mutableStateOf(TextFieldValue()) }
                OutlinedTextField(
                    value = inputPwdState.value,
                    onValueChange = { inputPwdState.value = it },
                    label = { Text(text = "otro Label de nueva clave era") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )

                val inputPwdRepeatState = remember { mutableStateOf(TextFieldValue()) }
                OutlinedTextField(
                    value = inputPwdRepeatState.value,
                    onValueChange = { inputPwdRepeatState.value = it },
                    label = { Text(text = "label de repite clave") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp)
                )


                Button(
                    onClick = {
                        /*
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
                        */
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Purple700)
                ) {
                    Text(text = "Actualizar Clave")
                }

            }
        }
    }
}
}