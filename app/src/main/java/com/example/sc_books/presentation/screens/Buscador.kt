package com.example.sc_books.presentation.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sc_books.viewmodels.BookViewModel
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.sc_books.model.ImageLinks
import com.example.sc_books.model.Item
import com.example.sc_books.repository.RetroState
import com.example.sc_books.ui.theme.Purple200
import com.example.sc_books.viewmodels.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.ClearAll
import androidx.compose.runtime.*
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.tooling.preview.Preview


@ExperimentalComposeUiApi
@Composable
fun Buscador(
    ViewModel: BookViewModel,
    searchViewModel: SearchViewModel
){
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.padding(bottom = 60.dp)
    ) {
        Scaffold(
            topBar = {
                SearchView(ViewModel, searchViewModel)
            },
            modifier = Modifier.padding(bottom = 5.dp),
            /*bottomBar = { BottomBar(navHostController) }*/
        ){
            Column(modifier = Modifier.padding(it)) {
                DisplayResults(ViewModel)
            }
        }
    }
}


@ExperimentalComposeUiApi
@Composable
fun SearchView(
    viewModel: BookViewModel = hiltViewModel(),
    searchViewModel: SearchViewModel
){
    val focusManager= LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope { Dispatchers.IO }
    val text by searchViewModel.text.collectAsState(initial = TextFieldValue(""))
    val bookList by viewModel.bookList.collectAsState(initial = null)
    var leadingIcon by remember { mutableStateOf(Icons.Filled.Search) }
    val focusRequester = FocusRequester()
    /*val (query, onValueChange) = remember { mutableStateOf("") }*/

    /*val options = listOf("Titulo", "Autor", "Categoria")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            value = selectedOptionText,
            onValueChange = { selectedOptionText = it },
            label = { Text("Label") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        // filter options based on text field value
        val filteringOptions =
            options.filter { it.contains(selectedOptionText, ignoreCase = true) }
        if (filteringOptions.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                filteringOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false
                        }
                    ) {
                        Text(text = selectionOption)
                    }
                }
            }
        }
    }*/

    Column(
        modifier = Modifier.padding(top = 20.dp)
    ) {
        Text(
            text = "Escribe el título del libro",
            fontSize = 14.sp,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(10.dp)
        )
        TextField(
            value = text,
            onValueChange = { value ->
                searchViewModel.onValueChange(value)
            },
            textStyle = TextStyle(fontSize = 17.sp),
            leadingIcon = {
                IconButton(onClick = {
                    if (leadingIcon == Icons.Filled.Clear && text.text.isNotEmpty())
                        searchViewModel.onValueChange(TextFieldValue(""))
                    else if (leadingIcon == Icons.Filled.Clear && text.text.isEmpty())
                        focusManager.clearFocus(true)
                    else if (leadingIcon == Icons.Filled.Search && text.text.isEmpty()) {
                        focusRequester.requestFocus()
                        keyboardController?.show()
                    }
                }) {
                    Icon(leadingIcon, contentDescription = "")
                }
            },
            modifier = Modifier
                .focusRequester(focusRequester)
                .onFocusEvent { state ->
                    leadingIcon = if (state.isFocused) {
                        Icons.Filled.Clear
                    } else {
                        Icons.Filled.Search
                    }
                }
                .padding(10.dp)
                .background(Color(0xFFE7F1F1), RoundedCornerShape(16.dp))
                .fillMaxWidth(),
            /*placeholder = { Text(text = "Escriba su busqueda") },*/
            label = {
                Text(text = "Buscar un libro",
                    color = Purple200
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                cursorColor = Color.DarkGray
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions (
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            trailingIcon = {
                bookList?.let { bookList ->
                    bookList.items?.let {
                        IconButton(onClick = {
                            viewModel.resetAll()
                        }) {
                            Icon(Icons.Rounded.ClearAll, contentDescription = "Clear all Button")
                        }
                    }
                }
            },
            keyboardActions = KeyboardActions(onSearch = {
                focusManager.clearFocus(true)
                keyboardController?.hide()
                scope.launch{
                    viewModel.getBooks(text.text)
                }
                /*searchBook()*/
            }),
        )
        /*Button(
           onClick = {
               //mContext.startActivity(Intent(mContext, MainActivity::class.java))
           },
           colors = ButtonDefaults.buttonColors(
               backgroundColor = Purple700,
               contentColor = Color.White),
           modifier = Modifier
               .width(220.dp)
               .padding(24.dp)
               .align(alignment = Alignment.CenterHorizontally),

       ) {
           Text(text = "Aplicar")
       }
       SimpleComposable()*/
    }
}

@Composable
fun DisplayResults(
    ViewModel: BookViewModel
) {
    val state = rememberLazyListState()
    val bookList by ViewModel.bookList.collectAsState(initial = null)
    val isError by ViewModel.isError.collectAsState(initial = false)
    val retroState by ViewModel.retroState.collectAsState(RetroState.PlaceHolder())

    bookList?.let { bookResults ->
        bookResults.items?.let { items ->
            LazyColumn(state = state, verticalArrangement = Arrangement.spacedBy(5.dp)) {
                items(items) { item ->
                    SearchResults(item)
                }
            }
        }
        if ((retroState is RetroState.Success && bookList?.items.isNullOrEmpty()) || isError) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No se encotraron resultados para esta busqueda",
                    fontSize = 25.sp,
                    style = MaterialTheme.typography.overline,
                    modifier = Modifier.padding(start=50.dp, end=50.dp, bottom=50.dp)
                )
            }
        }
    }
}

@Composable
fun SearchResults(item: Item) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            /* .clickable(onClick = {
                 navHostController.navigate(route = BookNavScreens.DetailView.withArgs(item.id))
             })*/
            .padding(
                start = 16.dp,
                top = 8.dp,
                end = 16.dp,
                bottom = 8.dp
            )
    ) {
        Row(horizontalArrangement = Arrangement.End) {
            Icon(
                Icons.Default.StarRate,
                contentDescription = null,
                modifier = Modifier.size(15.dp),
                tint = Color.Red
            )
            Text(
                text = item.volumeInfo.averageRating.toString(),
                style = MaterialTheme.typography.caption
            )
        }
        Row {
            val imageLink = item.volumeInfo.imageLinks
            BookImage(
                imageLink = imageLink,
                ActualImageModifier = Modifier.size(100.dp).align(alignment = Alignment.CenterVertically),
                PlaceHolderModifier = Modifier.size(100.dp).align(alignment = Alignment.CenterVertically),
            )
            Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                Text(
                    text = item.volumeInfo.title,
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold
                )

                val authors = if (item.volumeInfo.authors != null)
                    item.volumeInfo.authors.joinToString(",")
                else
                    "Autor desconocido"
                Text(
                    text = authors,
                    color = Color(0xff1e88e5),
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

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

/*
@Composable
fun prueba(
    query: String,
    viewModel: BookViewModel = hiltViewModel()
){
    Button(
        onClick = {
            viewModel.getBooks(query)
        }
    ){
    }
}
*/

