
package ics.natsuyume.ui.widgets

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun MusicWaveform(
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(
        Color(0xFFA78BDA),
        Color(0xFFC49AD8),
        Color(0xFFE6A6C9),
        Color(0xFF92B8E7),
        Color(0xFF77C9C3)
    ),
    isPlaying: Boolean = true
) {
    // Animate the waves' height.
    val amplitude = animateFloatAsState(
        targetValue = if (isPlaying) 1f else 0.08f,
        animationSpec = tween(
            durationMillis = 700,
            easing = FastOutSlowInEasing
        ),
        label = "WaveAmplitude"
    )

    // Animate their visibility.
    val opacity = animateFloatAsState(
        targetValue = if (isPlaying) 1f else 0.15f,
        animationSpec = tween(
            durationMillis = 600
        ),
        label = "WaveOpacity"
    )

    // Preserve the current animation phase when paused.
    val phase = remember {
        Animatable(0f)
    }

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            while (true) {
                phase.animateTo(
                    targetValue = phase.value + (2f * PI.toFloat()),
                    animationSpec = tween(
                        durationMillis = 4000,
                        easing = LinearEasing
                    )
                )
            }
        }
    }

    // Read animated values during drawing, avoiding
    // unnecessary composition updates on every frame.
    val phaseState = phase.asState()

    Canvas(
        modifier = modifier
    ) {
        val width = size.width
        val height = size.height
        val centerY = height / 2f

        val currentAmplitude = amplitude.value
        val currentOpacity = opacity.value
        val currentPhase = phaseState.value

        val amplitudes = listOf(
            0.45f,
            0.65f,
            0.85f,
            1.10f,
            1.40f
        )

        val frequencies = listOf(
            1.15f,
            1.45f,
            1.75f,
            2.05f,
            2.35f
        )

        val speeds = listOf(
            0.45f,
            0.75f,
            1.00f,
            1.35f,
            1.75f
        )

        val offsets = listOf(
            0f,
            1.15f,
            2.65f,
            4.00f,
            5.20f
        )

        val opacities = listOf(
            0.05f,
            0.10f,
            0.25f,
            0.30f,
            0.45f
        )

        colors.take(5).indices.reversed().forEach { index ->
            val color = colors[index]
            val baseAmplitude =
                height * 0.25f * amplitudes[index]

            val frequency = frequencies[index]
            val offset = offsets[index]
            val layerPhase = currentPhase * speeds[index]

            val path = Path().apply {
                moveTo(0f, centerY)

                for (x in 0..width.toInt() step 4) {
                    val progress = x / width

                    val y = centerY +
                            baseAmplitude *
                            currentAmplitude *
                            sin(
                                progress * frequency * 2f * PI +
                                        layerPhase + offset
                            ).toFloat()

                    lineTo(x.toFloat(), y)
                }

                lineTo(width, height)
                lineTo(0f, height)
                close()
            }

            drawPath(
                path = path,
                color = color.copy(
                    alpha = opacities[index] * currentOpacity
                )
            )
        }
    }
}
