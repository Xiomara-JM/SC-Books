package com.example.sc_books.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    //DataStore
    val mContext = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = Preferencias(mContext)

    val tabItems = listOf("Tab1", "Tab2")
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
                Text(
                    text = "Login",
                    modifier = Modifier.padding(50.dp),
                    color = Color.White
                )
            }
            else{
                Text(
                    text = "SignUp",
                    modifier = Modifier.padding(50.dp),
                    color = Color.White
                )
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
fun DefaultPreview2() {
    SCBooksTheme {
        LoginScreen()
    }
}




