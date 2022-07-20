package com.example.sc_books.datasource


import com.example.sc_books.model.BookList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestDataSource {
    /*
     * Get Books from Network Request
     * @return [BookList]
     */

    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 30
    ): BookList
}
