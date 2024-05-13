package com.panax.dialbutton.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import com.panax.dialbutton.control.DialController

/**
 * Dial item modifier
 *
 * Save the bounds of the item in the window
 */
@Composable
fun Modifier.dialItem(
    controller: DialController,
    index: Int,
): Modifier {
    return this then Modifier
        .onGloballyPositioned {
            controller.setTargetItemArea(
                index = index,
                targetArea = it.boundsInWindow()
            )
        }
}