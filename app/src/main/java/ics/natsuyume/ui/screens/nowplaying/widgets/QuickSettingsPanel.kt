
package ics.natsuyume.ui.screens.nowplaying.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ics.natsuyume.R

data class QuickSettingItem(
    val iconRes: Int,
    val contentDescription: String,
    val onClick: () -> Unit
)


@Composable
fun QuickSettingsPanel(
    onLyricsClick: () -> Unit = {},
    onEqualizerClick: () -> Unit = {},
    onSongInfoClick: () -> Unit = {},
    onSleepTimerClick: () -> Unit = {},
    onABRepeatClick: () -> Unit = {},
    onPlaybackSpeedClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val items = listOf(
        QuickSettingItem(
            R.drawable.baseline_text_fields_24,
            "Lyrics",
            onLyricsClick
        ),
        QuickSettingItem(
            R.drawable.baseline_equalizer_24,
            "Equalizer",
            onEqualizerClick
        ),
        QuickSettingItem(
            R.drawable.baseline_info_24,
            "Song information",
            onSongInfoClick
        ),
        QuickSettingItem(
            R.drawable.baseline_timer_24,
            "Sleep timer",
            onSleepTimerClick
        ),
        QuickSettingItem(
            R.drawable.baseline_repeat_24,
            "A-B repeat",
            onABRepeatClick
        ),
        QuickSettingItem(
            R.drawable.baseline_speed_24,
            "Playback speed and pitch",
            onPlaybackSpeedClick
        )
    )

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(
            topStart = 32.dp,
            topEnd = 32.dp
        ),
        color = MaterialTheme.colorScheme.surfaceContainerHigh
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "More playback controls",
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 4.dp)
            )

            Column(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 24.dp
                ),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items.chunked(4).forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        rowItems.forEach { item ->
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(64.dp),
                                shape = RoundedCornerShape(16.dp),
                                color = MaterialTheme.colorScheme.surfaceContainerHighest,
                                onClick = item.onClick
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(item.iconRes),
                                        contentDescription = item.contentDescription,
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }

                        repeat(4 - rowItems.size) {
                            Spacer(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(64.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}