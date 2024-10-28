package com.example.modern_practices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class SearchViewModel : ViewModel() {

    private val _queryText = MutableStateFlow("")
    val queryText = _queryText.asStateFlow()

    private val _artists = MutableStateFlow(artistList)
    val artists = queryText.combine(_artists) { query, artist ->
        if (query.isBlank()) artist
        else artist.filter { it.isMatchWithQuery(query) }
    }.stateIn( // To convert the combined flow into a StateFlow
        viewModelScope, // within the viewModelScope
        SharingStarted.WhileSubscribed(5000), // while staying active for 5s after the last subscriber is gone.
        _artists.value // initial value for the state
    )

    fun onQueryChanged(query: String) {
        _queryText.value = query
    }

}
