package com.panax.dialbuttton

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.panax.dialbuttton.ui.theme.ControlBackgroundOff
import com.panax.dialbuttton.ui.theme.ControlBackgroundOn
import com.panax.dialbuttton.ui.theme.ControlBorder
import com.panax.dialbuttton.ui.theme.ControlText
import com.panax.dialbuttton.ui.theme.ItemBackground
import com.panax.dialbuttton.ui.theme.ItemBorder
import com.panax.dialbuttton.ui.theme.ItemText

/**
 * Sample dial item
 */
@Composable
internal fun SampleItem(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(
        modifier = modifier
            .background(color = ItemBackground)
            .border(width = 2.dp, color = ItemBorder),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text, color = ItemText)
    }
}

/**
 * Sample dial controller
 */
@Composable
internal fun SampleController(
    modifier: Modifier = Modifier,
    text: String,
    onDragEnd: Boolean
) {
    Box(
        modifier = modifier
            .background(color = if (onDragEnd) ControlBackgroundOn else ControlBackgroundOff)
            .border(width = 2.dp, color = ControlBorder),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text, color = ControlText)
    }
}