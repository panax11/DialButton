package com.panax.dialbutton.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import com.panax.dialbutton.control.DialController

/**
 * Dial item
 *
 * @param controller Dial controller
 * @param index item index
 * @param content item content
 */
@Composable
fun DialItem(
    controller: DialController,
    index: Int,
    content: @Composable (BoxScope.(itemIndex: Int) -> Unit),
) {
    Box(
        modifier = Modifier
            .onGloballyPositioned {
                controller.setTargetItemArea(
                    index = index,
                    targetArea = it.boundsInWindow()
                )
            },
        content = { content(index) }
    )
}