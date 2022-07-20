package com.example.sc_books.repository

import com.example.sc_books.datasource.RestDataSource
import com.example.sc_books.model.BookList
import com.google.android.datatransport.runtime.dagger.Binds
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


interface BookRepository {
    suspend fun getBooks(query: String): RetroState<BookList>
}

class BookRepositoryImp @Inject constructor(
    private val dataSource: RestDataSource
) : BookRepository {

    @Binds
    override suspend fun getBooks(query: String): RetroState<BookList> {
        return try {
            RetroState.Success(data = dataSource.getBooks(query = query))
        } catch (e: Exception) {
            RetroState.Failure(message = e.message)
        }
    }
}

    /*suspend fun getBooksFromId(id: String): RetroState<Item> {
        return try {
            RetroState.Success(data = iretrofit.getBookFromId(id = id))
        } catch (e: Exception) {
            RetroState.Failure(message = e.message)
        }
    }*/

