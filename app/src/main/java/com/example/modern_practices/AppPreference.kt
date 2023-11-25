package com.example.modern_practices

import android.content.SharedPreferences
import com.example.modern_practices.font.FontSizePrefs

class AppPreference(private val sharedPreferences: SharedPreferences) {

    private val editor = sharedPreferences.edit()

    fun getFontSizePref(): String? =
        sharedPreferences.getString(Keys.FONT_SIZE_PREFS, FontSizePrefs.DEFAULT.key)

    fun setFontSizePref(fontChoiceKey: String) {
        editor.putString(Keys.FONT_SIZE_PREFS, fontChoiceKey).apply()
    }

    companion object {
        object Keys {
            const val FONT_SIZE_PREFS = "fontSizePrefs"
        }
    }
}
