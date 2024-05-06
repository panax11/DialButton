package com.panax.dialbutton.component

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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


/**
 * Row dial control
 *
 * @param modifier Modifier
 * @param onTargetSelect item select event
 * default: -1
 * onDragEnd(true: touch up, false: touch move)
 */
@Composable
internal fun RowDialControl(
    modifier: Modifier = Modifier,
    controller: DialController,
    onTargetSelect: (index: Int?, onDragEnd: Boolean) -> Unit,
    content: @Composable (RowScope.() -> Unit)
) {
    val dialState = rememberDialState()

    Row(
        modifier = Modifier
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
                            }else {
                                controller.targetIndex = null
                                onTargetSelect(null, false)
                            }
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()

                            val newDragAmountX = controller.toXDragAmount(dragAmount.x)
                            dialState.addValue(this, newDragAmountX)

                            controller.targetIndex?.let {
                                onTargetSelect(it, false)
                            }
                        }
                    )
                }
            }
            .then(modifier),
        content = content
    )
}