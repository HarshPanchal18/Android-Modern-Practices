package com.example.modern_practices

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun PickArtist(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel(),
) {

    // .collectAsState() - collects the values from a StateFlow or Flow and converts them into a Compose State.
    // This allows Compose to automatically recompose the UI when the data changes.
    val searchQuery by viewModel.queryText.collectAsState()
    val artists by viewModel.artists.collectAsState()
    val selectedArtistIndices = rememberSaveable { mutableStateOf(emptySet<Int>()) }

    Column {

        SearchField(
            searchQuery = searchQuery,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            onQueryChanged = viewModel::onQueryChanged
        )

        LazyVerticalGrid(
            state = rememberLazyGridState(),
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(3.dp),
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(artists) { index, artist ->
                ArtistView(artist = artist, position = index) { position ->
                    selectedArtistIndices.value =
                        if (artist.isSelected.value) selectedArtistIndices.value.plus(position)
                        else selectedArtistIndices.value.minus(position)
                }
            }
        }
    }
}

@Composable
fun ArtistView(
    artist: Artist,
    position: Int,
    onArtistClick: (Int) -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 15.dp)
            .clickable(
                enabled = true,
                onClickLabel = "",
                role = null,
                onClick = {
                    artist.toggleSelection()
                    onArtistClick(position)
                }
            )
    ) {

        Box {
            Image(
                painter = rememberAsyncImagePainter(
                    model = artist.img,
                    placeholder = painterResource(R.drawable.baseline_airplanemode_active_24),
                ),
                contentDescription = artist.name,
                modifier = Modifier
                    .aspectRatio(1F)
                    .clip(CircleShape)
                    .size(100.dp)
                    .border(
                        width = if (artist.isSelected.value) 3.dp else 0.dp,
                        color = if (artist.isSelected.value) Color.Green else Color.Transparent,
                        shape = CircleShape
                    ),
                contentScale = ContentScale.Crop
            )

            if (artist.isSelected.value) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(32.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    tint = Color.Blue
                )
            }
        }

        Text(
            text = artist.name,
            color = if (artist.isSelected.value) Color.Green else Color.Black,
            modifier = Modifier.padding(top = 5.dp),
            fontWeight = if (artist.isSelected.value) FontWeight.ExtraBold else FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )

    }
}
