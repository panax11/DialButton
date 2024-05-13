package com.panax.dialbutton.control

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Rect

/**
 * Dial controller for compose
 */
@Composable
fun rememberDialController() = remember {
    DialController()
}

/**
 * Dial controller
 */
class DialController {

    /** Controller init position */
    private var controlInitPos: Rect? = null

    /** Controller current position */
    private var controlPos = mutableStateOf(Rect(0f, 0f, 0f, 0f))

    /** Controller limit area */
    private var limitArea = Rect(0f, 0f, 0f, 0f)

    /** Target item areas */
    private val targetItemAreas: MutableMap<Int, Rect> = mutableMapOf()

    /** Current target index */
    var targetIndex: Int? = null


    /**
     * Set controller move area
     *
     * @param limitArea move area
     */
    fun setLimitArea(limitArea: Rect) {
        this.limitArea = limitArea
    }

    /**
     * Set controller current position
     *
     * @param controlPos controller current position
     */
    fun setControlPos(controlPos: Rect) {
        if (controlInitPos == null) {
            controlInitPos = controlPos
        }

        this.controlPos.value = controlPos

        updateTargetIndex()
    }

    /**
     * Set target item area
     *
     * @param index target item index
     * @param targetArea target item area
     */
    fun setTargetItemArea(index: Int, targetArea: Rect) {
        targetItemAreas[index] = targetArea
    }

    /**
     * Check controller in target area
     *
     * @return true: in, false: out
     */
    fun checkTargetSelect(): Boolean {
        return controlInitPos
            ?.contains(controlPos.value.center)
            ?.not() ?: false
    }

    /**
     * Drag controller
     *
     * @param amount drag amount
     *
     * @return drag amount
     */
    fun toXDragAmount(amount: Float): Float {
        val newLeft = controlPos.value.left + amount
        val newRight = controlPos.value.right + amount
        val limitLeft = limitArea.left
        val limitRight = limitArea.right

        return if (newRight > limitRight) {
            limitRight - controlPos.value.right
        } else if (newLeft < limitLeft) {
            limitLeft - controlPos.value.left
        } else {
            amount
        }
    }

    /**
     * Drag controller
     *
     * @param amount drag amount
     *
     * @return drag amount
     */
    fun toYDragAmount(amount: Float): Float {
        val newTop = controlPos.value.top + amount
        val newBottom = controlPos.value.bottom + amount
        val limitTop = limitArea.top
        val limitBottom = limitArea.bottom

        return if (newBottom > limitBottom) {
            limitBottom - controlPos.value.bottom
        } else if (newTop < limitTop) {
            limitTop - controlPos.value.top
        } else {
            amount
        }
    }

    /**
     * Update current  target index
     */
    private fun updateTargetIndex() {
        this.targetIndex = targetItemAreas
            .filter { it.value.contains(controlPos.value.center) }
            .keys
            .firstOrNull()
    }

    /**
     * Get controller center position
     *
     * @return controller center position
     */
    fun getCenter() = controlPos.value.center
}