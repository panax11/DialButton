package com.panax.dialbuttton

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import com.panax.dialbutton.ui.ColumnDial
import com.panax.dialbutton.ui.RowDial
import kotlin.random.Random


/**
 * Sample screen
 */
@Composable
fun SampleScreen(modifier: Modifier = Modifier) {
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
        RowDial(
            modifier = Modifier
                .height(100.dp),
            itemSize = rowItems.size,
            controlIndex = Random.nextInt(rowItems.size + 1),
            controlContent = {
                SampleController(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(50.dp),
                    text = selectValueRow,
                    onDragEnd = isDragEndRow
                )
            },
            itemContent = {
                SampleItem(
                    modifier = Modifier
                        .width(50.dp)
                        .fillMaxHeight(),
                    text = rowItems[it]
                )
            },
            onItemSelect = { index, idDragEnd ->
                selectValueRow = index?.let{ rowItems[it] } ?: ""
                isDragEndRow = idDragEnd
            }
        )

        ColumnDial(
            modifier = Modifier
                .width(100.dp),
            itemSize = columnItems.size,
            controlIndex = Random.nextInt(columnItems.size + 1),
            controlContent = {
                SampleController(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    text = selectValueColumn,
                    onDragEnd = isDragEndColumn
                )
            },
            itemContent = {
                SampleItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    text = columnItems[it]
                )
            },
            onItemSelect = { index, idDragEnd ->
                selectValueColumn = index?.let{ columnItems[it] } ?: ""
                isDragEndColumn = idDragEnd
            }
        )
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