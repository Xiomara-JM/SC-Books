package com.example.sc_books.viewmodels


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sc_books.model.BookList
import com.example.sc_books.model.Item
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
    private val bookRepositoryImp: BookRepository
) : ViewModel() {
    private val _retroState: MutableStateFlow<RetroState<BookList>> = MutableStateFlow(RetroState.PlaceHolder())
    val retroState: Flow<RetroState<BookList>> = _retroState
    private val _isError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isError: Flow<Boolean> = _isError
    private val _bookList: MutableStateFlow<BookList?> = MutableStateFlow(null)
    val bookList: Flow<BookList?> = _bookList

    fun resetAll() {
        _bookList.value = null
        _isError.value = false
        _retroState.value = RetroState.PlaceHolder()
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
                    Log.d("algo", result.data.toString())
                    _bookList.value = result.data
                    _isError.value = false
                    _retroState.value = RetroState.Success(data = result.data)
                }
                else -> {
                }
            }
        }
    }

    /*private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val isLoading: LiveData<Boolean> get() = _isLoading*/
}

