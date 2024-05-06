package com.panax.dialbutton.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.panax.dialbutton.component.ColumnDialControl
import com.panax.dialbutton.component.DialItem
import com.panax.dialbutton.control.rememberDialController

/**
 * Vertical dial
 *
 * @param modifier Modifier
 * @param itemSize items size, (0 <= itemSize)
 * @param controlIndex control position index, (0 <= index <= itemSize)
 * @param controlContent control composable
 * @param itemContent item composable
 * @param onItemSelect item select event, onDragEnd(true: touch up, false: touch move)
 */
@Composable
fun ColumnDial(
    modifier: Modifier = Modifier,
    itemSize: Int = 0,
    controlIndex: Int = 0,
    controlContent: @Composable (BoxScope.() -> Unit),
    itemContent: @Composable (BoxScope.(itemIndex: Int) -> Unit),
    onItemSelect: (itemIndex: Int?, onDragEnd: Boolean) -> Unit = { _, _ -> },
) {
    // valid value
    val validItemSize = itemSize.coerceAtLeast(0)
    val validControlIndex = controlIndex.coerceIn(0, validItemSize)

    val controller = rememberDialController()

    Column(
        modifier = modifier
            .onGloballyPositioned { controller.setLimitArea(it.boundsInWindow()) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        for (index in 0 until validItemSize + 1) {
            // control position
            if (index == validControlIndex) {
                ColumnDialControl(
                    onTargetSelect = onItemSelect,
                    controller = controller,
                    content = controlContent
                )
            }

            // item position
            if (index < validItemSize) {
                DialItem(
                    controller = controller,
                    index = index,
                    content = itemContent
                )
            }
        }
    }
}

@Preview(widthDp = 300, heightDp = 300)
@Composable
private fun ColumnDialPreview() {
    var selectIndex by remember { mutableIntStateOf(0) }

    ColumnDial(
        modifier = Modifier
            .wrapContentSize()
            .background(color = Color.DarkGray)
            .border(width = 2.dp, color = Color.Green)
            .padding(2.dp),
        itemSize = 3,
        controlIndex = 1,
        onItemSelect = { index, onDragEnd ->
            if (index != null) {
                selectIndex = index
            }
        },
        controlContent = {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(color = Color.Cyan)
                    .border(width = 2.dp, color = Color.Black),
                contentAlignment = Alignment.Center,
                content = { Text(text = selectIndex.toString()) }
            )
        },
        itemContent = { item ->
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(color = Color.LightGray)
                    .border(width = 2.dp, color = Color.Gray),
                contentAlignment = Alignment.Center,
                content = { Text(text = item.toString()) }
            )
        }
    )
}