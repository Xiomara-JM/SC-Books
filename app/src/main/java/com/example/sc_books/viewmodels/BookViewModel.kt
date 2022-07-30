package com.example.sc_books.viewmodels


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sc_books.model.BookList
import com.example.sc_books.model.Item
import com.example.sc_books.model.VolumeInfo
import com.example.sc_books.repository.BookRepository
import com.example.sc_books.repository.RetroState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val bookRepositoryImp: BookRepository,
    private val bookRepositoryItem: BookRepository
) : ViewModel() {
    private val _retroState: MutableStateFlow<RetroState<BookList>> = MutableStateFlow(RetroState.PlaceHolder())
    val retroState: Flow<RetroState<BookList>> = _retroState
    private val _isError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isError: Flow<Boolean> = _isError
    private val _bookList: MutableStateFlow<BookList?> = MutableStateFlow(null)
    val bookList: Flow<BookList?> = _bookList
    val itemId: MutableStateFlow<String> = MutableStateFlow("")

    private val _clickBook: MutableStateFlow<Item?> = MutableStateFlow(null)
    val clickedBook: Flow<Item?> = _clickBook
    private val _retroStateItem: MutableStateFlow<RetroState<Item>> = MutableStateFlow(RetroState.PlaceHolder())
    val retroStateItem: Flow<RetroState<Item>> = _retroStateItem
    private val _isErrorItem: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isErrorItem: Flow<Boolean> = _isErrorItem

    fun resetAll() {
        _bookList.value = null
        _isError.value = false
        _retroState.value = RetroState.PlaceHolder()
        _isErrorItem.value = false
    }

    fun resetItemId(){
        itemId.value = ""
    }

    fun getItemId(): String {
        return itemId.value
    }

    fun getBooks(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _retroState.value = RetroState.Loading()
            when (val result = bookRepositoryImp.getBooks(query = query)){
                is RetroState.Failure -> {
                    _isError.value = true
                    _retroState.value = RetroState.Failure(message = result.message)
                }
                is RetroState.Success -> {
                    /*Log.d("algo", result.data.toString())*/
                    _bookList.value = result.data
                    _isError.value = false
                    _retroState.value = RetroState.Success(data = result.data)
                }
                else -> {
                }
            }
        }
    }

    fun getBook(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _retroStateItem.value = RetroState.Loading()
            when (val result = bookRepositoryItem.getBooksFromId(id = id)) {
                is RetroState.Failure -> {
                    _isErrorItem.value = true
                    _retroStateItem.value = RetroState.Failure(message = result.message)
                }
                is RetroState.Success -> {
                    _isErrorItem.value = false
                    _clickBook.value = result.data
                    _retroStateItem.value = RetroState.Success(data = result.data)
                }
                else -> {}
            }
        }
    }
    /*private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val isLoading: LiveData<Boolean> get() = _isLoading*/
}

/*
 * A ViewModel to store the book which is clicked
 */
/*

@HiltViewModel
class ClickBookViewModel @Inject constructor(
    private val retroRepository: BookRepository
) : ViewModel() {
    private val _retroState: MutableStateFlow<RetroState<Item>> =
        MutableStateFlow(RetroState.PlaceHolder())
    val retroState: Flow<RetroState<Item>> = _retroState
    private val _isError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isError: Flow<Boolean> = _isError
    private val _clickBook: MutableStateFlow<Item?> = MutableStateFlow(null)
    val clickedBook: Flow<Item?> = _clickBook

     * Handle value change of [_clickBook]
     * @param id


    fun onValueChange(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _retroState.value = RetroState.Loading()
            when (val result = retroRepository.getBooksFromId(id = id)) {
                is RetroState.Failure -> {
                    _isError.value = true
                    _retroState.value = RetroState.Failure(message = result.message)
                }
                is RetroState.Success -> {
                    _isError.value = false
                    _clickBook.value = result.data
                    _retroState.value = RetroState.Success(data = result.data)
                }
                else -> {}
            }
        }
    }
}

*/
