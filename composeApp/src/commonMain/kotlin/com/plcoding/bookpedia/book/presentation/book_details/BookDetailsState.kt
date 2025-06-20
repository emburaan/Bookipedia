package com.plcoding.bookpedia.book.presentation.book_details

import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.presentation.UiText

data class BookDetailsState (
    val isLoading:Boolean = true,
    val isFav:Boolean = false,
    val book: Book? = null,
    val errorMessage: UiText? = null
)