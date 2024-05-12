package com.panax.dialbutton.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.panax.dialbutton.component.columnDialControl
import com.panax.dialbutton.component.dialItem
import com.panax.dialbutton.component.rowDialControl
import com.panax.dialbutton.control.DialController
import com.panax.dialbutton.control.rememberDialController


@Composable
fun Modifier.dialLayout(
    controller: DialController,
): Modifier {
    return this then Modifier
        .onGloballyPositioned { controller.setLimitArea(it.boundsInWindow()) }
}

@Preview(widthDp = 300, heightDp = 300)
@Composable
private fun ColumnDialPreview() {

    val controller = rememberDialController()
    var selectIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .dialLayout(controller = controller)
            .fillMaxSize()
            .background(color = Color.DarkGray)
            .border(width = 2.dp, color = Color.Green)
            .padding(2.dp),
    ) {
        for (index in 0 until 4) {
            if (index == 1) {
                Box(
                    modifier = Modifier
                        .columnDialControl(
                            controller = controller,
                            onTargetSelect = { index, onDragEnd ->
                                if (index != null) {
                                    selectIndex = index
                                }
                            }
                        )
                        .fillMaxWidth()
                        .weight(1f)
                        .background(color = Color.Cyan)
                        .border(width = 2.dp, color = Color.Black),
                    contentAlignment = Alignment.Center,
                    content = { Text(text = selectIndex.toString()) }
                )
            }
            Box(
                modifier = Modifier
                    .dialItem(controller = controller, index = index)
                    .fillMaxWidth()
                    .weight(1f)
                    .background(color = Color.LightGray)
                    .border(width = 2.dp, color = Color.Gray),
                contentAlignment = Alignment.Center,
                content = { Text(text = index.toString()) }
            )
        }
    }
}


@Preview
@Composable
private fun RowDialPreview() {

    val controller = rememberDialController()
    var selectIndex by remember { mutableIntStateOf(0) }

    Row(
        modifier = Modifier
            .dialLayout(controller = controller)
            .fillMaxSize()
            .background(color = Color.DarkGray)
            .border(width = 2.dp, color = Color.Green)
            .padding(2.dp),
    ) {
        for (index in 0 until 4) {
            if (index == 1) {
                Box(
                    modifier = Modifier
                        .rowDialControl(
                            controller = controller,
                            onTargetSelect = { index, onDragEnd ->
                                if (index != null) {
                                    selectIndex = index
                                }
                            }
                        )
                        .fillMaxHeight()
                        .weight(1f)
                        .background(color = Color.Cyan)
                        .border(width = 2.dp, color = Color.Black),
                    contentAlignment = Alignment.Center,
                    content = { Text(text = selectIndex.toString()) }
                )
            }
            Box(
                modifier = Modifier
                    .dialItem(controller = controller, index = index)
                    .fillMaxHeight()
                    .weight(1f)
                    .background(color = Color.LightGray)
                    .border(width = 2.dp, color = Color.Gray),
                contentAlignment = Alignment.Center,
                content = { Text(text = index.toString()) }
            )
        }
    }
}