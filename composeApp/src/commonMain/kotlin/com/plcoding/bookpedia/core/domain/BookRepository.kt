package com.plcoding.bookpedia.core.domain

import com.plcoding.bookpedia.book.domain.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBook(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookWorkId: String): Result<String?, DataError>

    fun getFavouritesBooks(): Flow<List<Book>>
    fun isBookFavourite(id: String): Flow<Boolean>
    suspend fun markAsFavourite(book: Book): EmptyResult<DataError.Local>
    suspend fun deleteAsFavourite(id: String)

}