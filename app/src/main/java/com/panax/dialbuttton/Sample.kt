package com.panax.dialbuttton

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.panax.dialbutton.component.columnDialControl
import com.panax.dialbutton.component.dialItem
import com.panax.dialbutton.component.rowDialControl
import com.panax.dialbutton.control.rememberDialController
import com.panax.dialbutton.ui.dialLayout


/**
 * Sample screen
 */
@Composable
fun SampleScreen(modifier: Modifier = Modifier) {
    val columnController = rememberDialController()
    val rowController = rememberDialController()

    val rowItems = listOf("A", "B", "C", "D", "E")
    val columnItems = listOf("1", "2", "3", "4", "5")

    var isDragEndRow: Boolean by remember { mutableStateOf(false) }
    var isDragEndColumn: Boolean by remember { mutableStateOf(false) }
    var selectValueRow: String by remember { mutableStateOf("") }
    var selectValueColumn: String by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = Color.DarkGray)
                .border(width = 2.dp, color = Color.Green)
                .padding(2.dp)
                .dialLayout(controller = rowController),
        ) {
            for (index in rowItems.indices) {
                if (index == 1) {
                    SampleController(
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .rowDialControl(
                                controller = rowController,
                                onTargetSelect = { i, onDragEnd ->
                                    selectValueRow = i?.let{ rowItems[it] } ?: ""
                                    isDragEndRow = onDragEnd
                                }
                            ),
                        text = selectValueRow,
                        onDragEnd = isDragEndRow
                    )
                }
                SampleItem(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .dialItem(controller = rowController, index = index),
                    text = rowItems[index]
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray)
                .border(width = 2.dp, color = Color.Green)
                .padding(2.dp)
                .dialLayout(controller = columnController),
        ) {
            for (index in columnItems.indices) {
                if (index == 1) {
                    SampleController(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .columnDialControl(
                                controller = columnController,
                                onTargetSelect = { i, onDragEnd ->
                                    selectValueColumn = i?.let{ columnItems[it] } ?: ""
                                    isDragEndColumn = onDragEnd
                                }
                            ),
                        text = selectValueColumn,
                        onDragEnd = isDragEndColumn
                    )
                }
                SampleItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .dialItem(controller = columnController, index = index),
                    text = columnItems[index]
                )
            }
        }
    }
}

@Composable
private fun SampleItem(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(
        modifier = modifier
            .background(color = Color.LightGray)
            .border(width = 2.dp, color = Color.Gray),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text, color = Color.Magenta)
    }
}

@Composable
private fun SampleController(
    modifier: Modifier = Modifier,
    text: String,
    onDragEnd: Boolean
) {
    Box(
        modifier = modifier
            .background(color = Color.Black)
            .border(width = 2.dp, color = Color.Blue),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text, color = if (onDragEnd) Color.Cyan else Color.White)
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun SamplePreview() {
    SampleScreen(modifier = Modifier.fillMaxSize())
}