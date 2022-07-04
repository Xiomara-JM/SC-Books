package com.example.sc_books.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sc_books.ui.theme.LB50_900
import com.example.sc_books.ui.theme.Purple700
import com.example.sc_books.ui.theme.SCBooksTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen()
        }
    }
}

@Preview
@Composable
fun LoginScreen(){
    val mContext = LocalContext.current
    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize().padding(5.dp),
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
                        mContext.startActivity(Intent(mContext, MainActivity::class.java))
                    },
                fontSize = 12.sp,
                color = LB50_900,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Composable
fun DefaultPreview2() {
    SCBooksTheme {
        LoginScreen()
    }
}




