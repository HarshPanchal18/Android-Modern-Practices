package com.example.modern_practices

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.util.fastAny

data class Artist(
    val img: Int,
    val name: String,
    var isSelected: MutableState<Boolean>,
) {
    fun toggleSelection() {
        isSelected.value = !isSelected.value
    }

    fun isMatchWithQuery(query: String): Boolean {
        val matchResult = listOf(name, "${name.first()}")
        return matchResult.fastAny { it.contains(other = query, ignoreCase = true) }
    }
}

val artistList = listOf(
    Artist(
        img = R.drawable.android_logo,
        name = "User1",
        isSelected = mutableStateOf(false)
    ),
    Artist(
        img = R.drawable.android_logo,
        name = "User2",
        isSelected = mutableStateOf(false)
    ),
    Artist(
        img = R.drawable.android_logo,
        name = "User3",
        isSelected = mutableStateOf(false)
    ),
    Artist(
        img = R.drawable.android_logo,
        name = "User4",
        isSelected = mutableStateOf(false)
    ),
    Artist(
        img = R.drawable.android_logo,
        name = "User5",
        isSelected = mutableStateOf(false)
    ),
    Artist(
        img = R.drawable.android_logo,
        name = "User6",
        isSelected = mutableStateOf(false)
    ),
)
