package com.panax.dialbutton.component

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import com.panax.dialbutton.control.DialController
import com.panax.dialbutton.control.rememberDialState
import kotlinx.coroutines.coroutineScope

@Composable
fun Modifier.columnDialControl(
    controller: DialController,
    onTargetSelect: (index: Int?, onDragEnd: Boolean) -> Unit,
): Modifier {
    return this then Modifier
        .dialControl(
            controller = controller,
            isHorizontal = false,
            onTargetSelect = onTargetSelect
        )
}


@Composable
fun Modifier.rowDialControl(
    controller: DialController,
    onTargetSelect: (index: Int?, onDragEnd: Boolean) -> Unit,
): Modifier {
    return this then Modifier
        .dialControl(
            controller = controller,
            isHorizontal = true,
            onTargetSelect = onTargetSelect
        )
}


@Composable
private fun Modifier.dialControl(
    controller: DialController,
    isHorizontal: Boolean,
    onTargetSelect: (index: Int?, onDragEnd: Boolean) -> Unit,
): Modifier {

    val dialState = rememberDialState()

    return this then Modifier
        .zIndex(1f)
        .offset { IntOffset(dialState.value.toInt(), 0) }
        .onGloballyPositioned { controller.setControlPos(it.boundsInWindow()) }
        .pointerInput(Unit) {
            coroutineScope {
                detectDragGestures(
                    onDragStart = { controller.targetIndex = null },
                    onDragCancel = { dialState.resetValue(this) },
                    onDragEnd = {
                        dialState.resetValue(this)

                        if (controller.checkTargetSelect()) {
                            onTargetSelect(controller.targetIndex!!, true)
                        } else {
                            controller.targetIndex = null
                            onTargetSelect(null, false)
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()

                        val newDragAmount = if (isHorizontal) {
                            controller.toXDragAmount(dragAmount.x)
                        } else {
                            controller.toYDragAmount(dragAmount.y)
                        }
                        dialState.addValue(this, newDragAmount)

                        controller.targetIndex?.let {
                            onTargetSelect(it, false)
                        }
                    }
                )
            }
        }
}