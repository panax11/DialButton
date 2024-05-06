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
 * Vertical dial control
 *
 * @param modifier Modifier
 * @param onTargetSelect item select event
 * default: null
 * onDragEnd(true: touch up, false: touch move)
 */
@Composable
internal fun ColumnDialControl(
    modifier: Modifier = Modifier,
    controller: DialController,
    onTargetSelect: (index: Int?, onDragEnd: Boolean) -> Unit,
    content: @Composable (BoxScope.() -> Unit)
) {
    val dialState = rememberDialState()

    Box(
        modifier = Modifier
            .zIndex(1f)
            .offset { IntOffset(0, dialState.value.toInt()) }
            .onGloballyPositioned { controller.setControlPos(it.boundsInWindow()) }
            .pointerInput(Unit) {
                coroutineScope {
                    detectDragGestures(
                        onDragCancel = { dialState.resetValue(this) },
                        onDragStart = { controller.targetIndex = null },
                        onDragEnd = {
                            dialState.resetValue(this)

                            if (controller.checkTargetSelect()) {
                                controller.targetIndex?.let {
                                    onTargetSelect(it, true)
                                }
                            }else {
                                controller.targetIndex = null
                                onTargetSelect(null, false)
                            }
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()

                            val newDragAmountY = controller.toYDragAmount(dragAmount.y)
                            dialState.addValue(this, newDragAmountY)

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
