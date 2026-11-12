package ics.natsuyume.ui.screens.nowplaying.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import ics.natsuyume.R

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PlaybackControlsSheet(
    animatedVisibilityScope: AnimatedVisibilityScope,
    isQuickSettingsExpanded: Boolean,
    onFavoriteClick: () -> Unit = {},
    onAddToPlaylistClick: () -> Unit = {},
    onExpandClick: () -> Unit = {},
    onRepeatClick: () -> Unit = {},
    onShuffleClick: () -> Unit = {},
    onPreviousClick: () -> Unit = {},
    onRewindClick: () -> Unit = {},
    onPlayPauseClick: () -> Unit = {},
    onFastForwardClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isFavorite by rememberSaveable {
        mutableStateOf(false)
    }

    var isPlaying by rememberSaveable {
        mutableStateOf(false)
    }

    val seekBarSpacing by animateDpAsState(
        targetValue = if (isQuickSettingsExpanded) 16.dp else 28.dp,
        label = "seekBarSpacing"
    )

    val playbackControlsSpacing by animateDpAsState(
        targetValue = if (isQuickSettingsExpanded) 12.dp else 28.dp,
        label = "playbackControlsSpacing"
    )

    val sheetVerticalPadding by animateDpAsState(
        targetValue = if (isQuickSettingsExpanded) 16.dp else 24.dp,
        label = "sheetVerticalPadding"
    )

    val rotation = remember { Animatable(0f) }

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            while (true) {
                rotation.animateTo(
                    targetValue = rotation.value + 360f,
                    animationSpec = tween(
                        durationMillis = 6000,
                        easing = LinearEasing
                    )
                )
            }
        }
    }

    Surface(
        modifier = with(animatedVisibilityScope) {
            modifier
                .fillMaxWidth()
                .animateEnterExit(
                    enter = fadeIn(
                        animationSpec = tween(
                            durationMillis = 450,
                            delayMillis = 180
                        )
                    ) + slideInVertically(
                        animationSpec = tween(
                            durationMillis = 450,
                            delayMillis = 180
                        ),
                        initialOffsetY = { it }
                    ),
                    exit = fadeOut(
                        animationSpec = tween(
                            durationMillis = 250
                        )
                    ) + slideOutVertically(
                        animationSpec = tween(
                            durationMillis = 250
                        ),
                        targetOffsetY = { it }
                    )
                )
        },
        shape = RoundedCornerShape(
            topStart = 48.dp,
            topEnd = 48.dp
        ),
        color = MaterialTheme.colorScheme.surfaceContainer
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 24.dp,
                        vertical = sheetVerticalPadding
                    )
            ) {
                // Top toolbar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = {
                                isFavorite = !isFavorite
                                onFavoriteClick()
                            },
                        ) {
                            Icon(
                                painter = painterResource(
                                    if (isFavorite) {
                                        R.drawable.baseline_favorite_24
                                    } else {
                                        R.drawable.baseline_favorite_border_24
                                    }
                                ),
                                contentDescription = if (isFavorite) {
                                    "Remove from favorites"
                                } else {
                                    "Add to favorites"
                                },
                            )
                        }
                    }

                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(onClick = onAddToPlaylistClick) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_playlist_add_24),
                                contentDescription = "Add to playlist"
                            )
                        }
                    }

                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = onExpandClick
                        ) {
                            Icon(
                                painter = painterResource(
                                    if (isQuickSettingsExpanded) {
                                        R.drawable.baseline_keyboard_arrow_down_24
                                    } else {
                                        R.drawable.baseline_keyboard_arrow_up_24
                                    }
                                ),
                                contentDescription = if (isQuickSettingsExpanded) {
                                    "Collapse Quick Settings"
                                } else {
                                    "Expand Quick Settings"
                                }
                            )
                        }
                    }

                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(onClick = onRepeatClick) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_repeat_24),
                                contentDescription = "Repeat mode"
                            )
                        }
                    }

                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(onClick = onShuffleClick) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_shuffle_24),
                                contentDescription = "Shuffle"
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(seekBarSpacing)
                )

                // Song progress
                val progress = 0.34f

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "1:24",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    BoxWithConstraints(
                        modifier = Modifier
                            .weight(1f)
                            .height(if (isQuickSettingsExpanded) 18.dp else 24.dp)
                    ) {
                        if (isPlaying) {
                            LinearWavyProgressIndicator(
                                progress = { progress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Center),
                                color = MaterialTheme.colorScheme.primary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        } else {
                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Center),
                                color = MaterialTheme.colorScheme.primary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        }

                        // Progress handle
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .offset {
                                    IntOffset(
                                        x = (maxWidth * progress).roundToPx() - 6.dp.roundToPx(),
                                        y = 0
                                    )
                                }
                                .size(12.dp)
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    CircleShape
                                )
                                .border(
                                    2.dp,
                                    MaterialTheme.colorScheme.surfaceContainer,
                                    CircleShape
                                )
                        )
                    }

                    Text(
                        text = "4:59",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(
                    modifier = Modifier.height(playbackControlsSpacing)
                )

                // Playback buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onPreviousClick,
                        modifier = Modifier.size(52.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_skip_previous_24),
                            contentDescription = "Previous",
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    IconButton(
                        onClick = onRewindClick,
                        modifier = Modifier.size(52.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_fast_rewind_24),
                            contentDescription = "Rewind",
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    // Main playback button
                    Box(
                        modifier = Modifier.size(84.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(94.dp)
                                .graphicsLayer {
                                    rotationZ = rotation.value
                                }
                                .background(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = MaterialShapes.Cookie9Sided.toShape()
                                )
                        )

                        IconButton(
                            onClick = {
                                isPlaying = !isPlaying
                                onPlayPauseClick()
                            },
                            modifier = Modifier
                                .size(76.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Icon(
                                painter = painterResource(
                                    if (isPlaying) {
                                        R.drawable.baseline_pause_24
                                    } else {
                                        R.drawable.baseline_play_arrow_24
                                    }
                                ),
                                contentDescription = if (isPlaying) "Pause" else "Play",
                                modifier = Modifier.size(52.dp),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }

                    IconButton(
                        onClick = onFastForwardClick,
                        modifier = Modifier.size(52.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_fast_forward_24),
                            contentDescription = "Fast forward",
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    IconButton(
                        onClick = onNextClick,
                        modifier = Modifier.size(52.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_skip_next_24),
                            contentDescription = "Next",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = isQuickSettingsExpanded,
                enter = fadeIn() + expandVertically(
                    expandFrom = Alignment.Top
                ),
                exit = fadeOut() + shrinkVertically(
                    shrinkTowards = Alignment.Top
                )
            ) {
                Column {
                    Spacer(Modifier.height(24.dp))

                    QuickSettingsPanel(
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}