package com.example.modern_practices.font

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.modern_practices.R
import com.example.modern_practices.ui.theme.ModernPracticesTheme

@Composable
fun FontSelectionMenu(
    modifier: Modifier = Modifier,
    selectedFont: FontSizePrefs = FontSizePrefs.DEFAULT,
    onFontChosen: (FontSizePrefs) -> Unit,
) {
    ModernPracticesTheme(fontSizePrefs = FontSizePrefs.DEFAULT) {
        Column(
            modifier = modifier
                .width(285.dp)
                .padding(15.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = stringResource(id = R.string.font_size),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondary)
                    .height(40.dp)
                    .padding(4.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                FontSizePrefs.entries.forEachIndexed { index, fontSizePrefs ->
                    FontChoiceItem(
                        isSelected = selectedFont == fontSizePrefs,
                        fontSizePrefs = fontSizePrefs,
                        onFontChosen = onFontChosen
                    )
                    if (index < FontSizePrefs.entries.size - 1) {
                        Divider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.secondaryContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.FontChoiceItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    fontSizePrefs: FontSizePrefs,
    onFontChosen: (FontSizePrefs) -> Unit
) {
    Box(
        modifier = Modifier
            .height(32.dp)
            .weight(1F)
            .background(if (isSelected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.secondary)
            .clickable { onFontChosen(fontSizePrefs) }
            .padding(bottom = 4.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Icon(
            painter = painterResource(id = fontSizePrefs.iconRes),
            contentDescription = stringResource(id = R.string.select_font_size, fontSizePrefs.key),
            tint = if (isSelected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSecondary,
            modifier = modifier,
        )
    }
}
