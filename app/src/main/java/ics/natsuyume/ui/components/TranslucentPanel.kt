
package ics.natsuyume.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun TranslucentPanel(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(
        topStart = 24.dp,
        topEnd = 24.dp
    ),
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = MaterialTheme.colorScheme.surfaceContainer
            .copy(alpha = 0.80f)
    ) {
        Box {
            content()
        }
    }
}
