
package ics.natsuyume.ui.screens.nowplaying.widgets

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ics.natsuyume.R

enum class PlaybackSource {
    Artist,
    Album,
    Playlist,
    Queue
}

@Composable
fun NowPlayingHeader(
    source: PlaybackSource,
    sourceName: String,
    onCollapse: () -> Unit,
    onQueueClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = with(animatedVisibilityScope) {
            modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .animateEnterExit(
                    enter = fadeIn(
                        animationSpec = tween(
                            durationMillis = 350,
                            delayMillis = 100
                        )
                    ) + slideInVertically(
                        animationSpec = tween(
                            durationMillis = 350,
                            delayMillis = 100
                        ),
                        initialOffsetY = { -it / 3 }
                    ),
                    exit = fadeOut(
                        animationSpec = tween(
                            durationMillis = 200
                        )
                    ) + slideOutVertically(
                        animationSpec = tween(
                            durationMillis = 200
                        ),
                        targetOffsetY = { -it / 3 }
                    )
                )
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = onCollapse,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_keyboard_arrow_down_24),
                contentDescription = "Collapse Now Playing",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "From ${source.name}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = sourceName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        IconButton(
            onClick = onQueueClick,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_queue_music_24),
                contentDescription = "Open queue",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
