package com.example.modern_practices

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

class SearchViewModel : ViewModel() {

    // Only the ViewModel is allowed to modify the search query, while still making it available for observation as a read-only state within a Compose screen.
    var searchQuery by mutableStateOf("")
        private set

    private val moviesFlow = flowOf(
        listOf(
            Movie(id = 1, name = "Inception", rating = 8.8),
            Movie(id = 2, name = "The Prestige", rating = 8.5),
            Movie(id = 3, name = "Interstellar", rating = 8.7)
        )
    )

    // PRODUCING_SEARCH_RESULT
    val searchResult: StateFlow<List<Movie>> = snapshotFlow { searchQuery }
        // Obtain mast recently emitted values from each flow
        .combine(moviesFlow) { searchQuery, movies ->
            when {
                searchQuery.isNotEmpty() -> movies.filter { movie ->
                    movie.name.contains(searchQuery, ignoreCase = true) ||
                            movie.rating.toString().contains(searchQuery)
                }

                else -> movies // Do not filter on empty searchQuery
            }
        }.stateIn( // To transform the search results into a state value
            scope = viewModelScope,
            initialValue = emptyList(),
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000)
        )

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
    }
}
