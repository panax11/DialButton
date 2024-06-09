package com.panax.dialbuttton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        RowSample(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        ColumnSample(
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth()
        )
    }
}

/**
 * Row dial sample
 */
@Composable
private fun RowSample(modifier: Modifier) {
    val controller = rememberDialController()

    val items = listOf("A", "B", "C", "D", "E")

    var isDragEnd: Boolean by remember { mutableStateOf(false) }
    var selectValue: String by remember { mutableStateOf("") }

    Row(
        modifier = modifier.dialLayout(controller = controller),
    ) {
        for (index in items.indices) {
            if (index == 1) {
                SampleController(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .rowDialControl(
                            controller = controller,
                            onTargetSelect = { i, onDragEnd ->
                                selectValue = i?.let { items[it] } ?: ""
                                isDragEnd = onDragEnd
                            }
                        ),
                    text = selectValue,
                    onDragEnd = isDragEnd
                )
            }
            SampleItem(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .dialItem(controller = controller, index = index),
                text = items[index]
            )
        }
    }
}

/**
 * Column dial sample
 */
@Composable
private fun ColumnSample(modifier: Modifier) {
    val controller = rememberDialController()

    val items = listOf("1", "2", "3", "4", "5")

    var isDragEnd: Boolean by remember { mutableStateOf(false) }
    var selectValue: String by remember { mutableStateOf("") }

    Column(
        modifier = modifier.dialLayout(controller = controller),
    ) {
        for (index in items.indices) {
            if (index == 1) {
                SampleController(
                    modifier = Modifier
                        .weight(1f)
                        .width(50.dp)
                        .columnDialControl(
                            controller = controller,
                            onTargetSelect = { i, onDragEnd ->
                                selectValue = i?.let { items[it] } ?: ""
                                isDragEnd = onDragEnd
                            }
                        ),
                    text = selectValue,
                    onDragEnd = isDragEnd
                )
            }
            SampleItem(
                modifier = Modifier
                    .weight(1f)
                    .width(50.dp)
                    .dialItem(controller = controller, index = index),
                text = items[index]
            )
        }
    }
}

/**
 * Sample screen preview
 */
@Preview(showBackground = true, widthDp = 360, heightDp = 360)
@Composable
private fun SamplePreview() {
    SampleScreen(modifier = Modifier.fillMaxSize())
}

/**
 * Row dial sample preview
 */
@Preview(showBackground = true, widthDp = 360)
@Composable
private fun RowSamplePreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        RowSample(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}

/**
 * Column dial sample preview
 */
@Preview(showBackground = true, heightDp = 360)
@Composable
private fun ColumnSamplePreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ColumnSample(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .wrapContentWidth()
        )
    }
}