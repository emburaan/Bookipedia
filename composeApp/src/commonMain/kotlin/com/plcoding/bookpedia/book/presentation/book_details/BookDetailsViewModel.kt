package com.plcoding.bookpedia.book.presentation.book_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.plcoding.bookpedia.app.Route
import com.plcoding.bookpedia.core.domain.BookRepository
import com.plcoding.bookpedia.core.domain.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailsViewModel(
    private val bookRepository: BookRepository,
    private val saveStateHandle: SavedStateHandle
) : ViewModel() {
    private val bookId = saveStateHandle.toRoute<Route.BookDetails>().id
    private val _state = MutableStateFlow(BookDetailsState())
    val state = _state.onStart {
        fetchBookDescription()
        observeFavouriteStatus()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _state.value
    )

    fun onAction(action: BookDetailsAction) {
        when (action) {
            is BookDetailsAction.OnSelectedBookChange -> {
                _state.update {
                    it.copy(
                        book = action.book
                    )
                }
            }

            is BookDetailsAction.OnFavouriteClick -> {
                viewModelScope.launch {
                    if (state.value.isFav) {
                        bookRepository.deleteAsFavourite(bookId)
                    } else {
                        state.value.book?.let { bookRepository.markAsFavourite(it) }
                    }
                }
            }

            else -> Unit
        }
    }

    private fun observeFavouriteStatus() {
        bookRepository.isBookFavourite(bookId)
            .onEach { isFav ->
                _state.update {
                    it.copy(
                        isFav = isFav
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun fetchBookDescription() {
        viewModelScope.launch {
            val bookId = saveStateHandle.toRoute<Route.BookDetails>().id
            bookRepository
                .getBookDescription(bookId)
                .onSuccess { description ->
                    _state.update {
                        it.copy(
                            book = it.book?.copy(descriptions = description),
                            isLoading = false
                        )
                    }
                }
        }

    }
}