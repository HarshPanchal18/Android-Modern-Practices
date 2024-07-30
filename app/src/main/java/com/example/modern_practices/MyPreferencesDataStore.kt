package com.example.modern_practices

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

val Context.myPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

enum class Priority(val color: Color) {
    High(Color.Red),
    Medium(Color.Yellow),
    Low(Color.Blue),
}

data class TaskStatus(val isCompleted: Boolean, val priority: Priority)

@Singleton
class MyPreferencesDataStore @Inject constructor(
    @ApplicationContext context: Context,
) {
    private val myPreferencesDataStore = context.myPreferencesDataStore

    private object PreferencesKey {
        val IS_COMPLETED_KEY = booleanPreferencesKey("is_completed")
        val PRIORITY_KEY = stringPreferencesKey("priority")
    }

    val taskStateFlow: Flow<Unit> = myPreferencesDataStore.data
        .catch { exception ->
            if (exception is IOException)
                emit(emptyPreferences())
            else
                throw exception
        }.map { prefs ->
            val isCompleted = prefs[PreferencesKey.IS_COMPLETED_KEY] ?: false
            val priority = Priority.valueOf(prefs[PreferencesKey.PRIORITY_KEY] ?: Priority.Low.name)

            TaskStatus(isCompleted, priority)
        }

    suspend fun updateIsCompleted(isCompleted: Boolean) {
        myPreferencesDataStore.edit { prefs ->
            prefs[PreferencesKey.IS_COMPLETED_KEY] = isCompleted
        }
    }

    suspend fun updatePriority(priority: Priority) {
        myPreferencesDataStore.edit { prefs ->
            prefs[PreferencesKey.PRIORITY_KEY] = priority.name
        }
    }
}
