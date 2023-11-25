package com.example.modern_practices

import androidx.lifecycle.ViewModel
import com.example.modern_practices.font.FontSizePrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel constructor(private val prefs: AppPreference) : ViewModel() {

    private val _fontSizeChoice =
        MutableStateFlow(FontSizePrefs.getFontPrefFromKey(prefs.getFontSizePref()))
    val fontSizeChoice: StateFlow<FontSizePrefs> = _fontSizeChoice

    fun setFontSize(fontSizePrefs: FontSizePrefs) {
        _fontSizeChoice.value = fontSizePrefs
        prefs.setFontSizePref(fontSizePrefs.key)
    }
}
