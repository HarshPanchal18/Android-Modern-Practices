package com.example.modern_practices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

// Identifies a ViewModel for construction injection.
// This ViewModel will be available for creation by the dagger's `HiltViewModelFactory`
// and can be retrieved by default in an Activity or Fragment annotated with `AndroidEntryPoint`.
// The HiltViewModel containing a constructor annotated with Inject will have its dependencies defined in the constructor parameters injected by Dagger's Hilt.
@HiltViewModel
class MainViewModel @Inject constructor(
    private val myPreferencesDataStore: MyPreferencesDataStore,
) : ViewModel() {
    val isCompleted: Flow<Boolean> = myPreferencesDataStore.taskStateFlow.map { it.isCompleted }

    fun updateIsCompleted(isCompleted: Boolean) {
        viewModelScope.launch {
            myPreferencesDataStore.updateIsCompleted(isCompleted)
        }
    }

    fun updatePriority(priority: Priority) {
        viewModelScope.launch {
            myPreferencesDataStore.updatePriority(priority)
        }
    }
}
