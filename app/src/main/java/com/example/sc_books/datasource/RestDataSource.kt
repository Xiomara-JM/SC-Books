package com.example.sc_books.datasource


import com.example.sc_books.model.BookList
import com.example.sc_books.model.Item
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

    /*
     * Get Books from ID
     *
     * @param id Book ID
     * @return [Item]
     */
    @GET("volumes/{id}")
    suspend fun getBookFromId(@Path("id") id: String): Item
}
