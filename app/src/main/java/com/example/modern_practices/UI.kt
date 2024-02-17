package com.example.modern_practices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.modern_practices.ui.SearchScreen

@Composable
fun SearchScreenMain(viewModel: SearchViewModel) {

    val searchResults by viewModel.searchResult.collectAsStateWithLifecycle()

    SearchScreen(
        searchQuery = viewModel.searchQuery,
        searchResults = searchResults,
        onSearchQueryChange = { viewModel.onSearchQueryChange(it) }
    )

}

@Composable
fun MovieListItem(
    movie: Movie,
    // modifier: Modifier = Modifier
) {
    Text(text = movie.name, fontSize = 18.sp)
    Text(text = movie.rating.toString(), fontSize = 16.sp)
}

@Composable
fun MovieListEmptyState(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "No movies found",
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = "Try adjusting your search",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
