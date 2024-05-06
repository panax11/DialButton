package com.panax.dialbutton.control

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/**
 * remember dial controller state, for compose
 *
 * @param initValue controller offset init value
 * @param animationSpec controller dial animation, default spring animation
 *
 */
@Composable
fun rememberDialState(
    initValue: Float = 0f,
    animationSpec: AnimationSpec<Float> = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMedium
    )
) = remember {
    DialStateImpl(initValue = initValue, animationSpec = animationSpec)
}

/**
 * dial controller state
 */
@Stable
interface DialState {

    /** controller offset  value */
    val value: Float

    /**
     * add value to controller offset
     *
     * @param scope
     * @param value
     */
    fun addValue(scope: CoroutineScope, value: Float)

    /**
     * reset controller offset 0
     *
     * @param scope
     */
    fun resetValue(scope: CoroutineScope)
}

/**
 * dial controller state implementation
 *
 * @property initValue controller offset init value
 * @property animationSpec controller dial animation
 */
@Stable
class DialStateImpl(
    private val initValue: Float,
    private val animationSpec: AnimationSpec<Float>
) : DialState {

    /** controller offset value */
    private var _value = Animatable(initValue)
    override val value: Float
        get() = _value.value


    /**
     * add value to offset, no animation
     *
     * @param scope
     * @param value
     */
    override fun addValue(scope: CoroutineScope, value: Float) {
        scope.launch {
            _value.snapTo(targetValue = _value.value + value)
        }
    }

    /**
     * reset offset, use animation
     *
     * @param scope
     */
    override fun resetValue(scope: CoroutineScope) {
        scope.launch {
            _value.animateTo(targetValue = initValue, animationSpec = animationSpec)
        }
    }
}
