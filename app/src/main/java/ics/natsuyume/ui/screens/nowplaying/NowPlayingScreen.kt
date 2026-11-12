package ics.natsuyume.ui.screens.nowplaying

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.SeekableTransitionState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import ics.natsuyume.ui.screens.nowplaying.widgets.NowPlayingHeader
import ics.natsuyume.ui.screens.nowplaying.widgets.PlaybackControlsSheet
import ics.natsuyume.ui.screens.nowplaying.widgets.PlaybackSource
import ics.natsuyume.ui.screens.nowplaying.widgets.TrackArtwork
import ics.natsuyume.ui.screens.nowplaying.widgets.TrackInformation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch

@Composable
fun NowPlayingScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    transitionState: SeekableTransitionState<Boolean>,
    artworkCornerPercent: Int,
    onCollapse: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isQuickSettingsExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    val contentBottomPadding by animateDpAsState(
        targetValue = if (isQuickSettingsExpanded) 380.dp else 240.dp,
        label = "contentBottomPadding"
    )

    val informationAlpha by animateFloatAsState(
        targetValue = if (isQuickSettingsExpanded) 0f else 1f,
        label = "informationAlpha"
    )

    val scope = rememberCoroutineScope()

    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        val screenHeightPx = constraints.maxHeight.toFloat()
        val dismissThresholdPx = with(LocalDensity.current) {
            140.dp.toPx()
        }

        val dragRangePx = screenHeightPx * 0.45f

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .pointerInput(
                    isQuickSettingsExpanded,
                    screenHeightPx,
                    transitionState
                ) {
                    if (!isQuickSettingsExpanded) {
                        var dragDistance = 0f
                        var seekJob: Job? = null
                        var canDismiss = false

                        detectDragGestures(
                            onDragStart = {
                                canDismiss =
                                    transitionState.currentState &&
                                            transitionState.targetState

                                dragDistance = 0f
                            },
                            onDrag = { change, dragAmount ->
                                if (
                                    canDismiss &&
                                    (dragAmount.y > 0f || dragDistance > 0f)
                                ) {
                                    change.consume()

                                    dragDistance = (
                                            dragDistance + dragAmount.y
                                            ).coerceIn(0f, dragRangePx)

                                    val progress = (
                                            dragDistance / dragRangePx
                                            ).coerceIn(0f, 1f)

                                    seekJob?.cancel()

                                    seekJob = scope.launch {
                                        transitionState.seekTo(
                                            fraction = progress,
                                            targetState = false
                                        )
                                    }
                                }
                            },
                            onDragEnd = {
                                if (canDismiss) {
                                    val shouldDismiss =
                                        dragDistance >= dismissThresholdPx

                                    scope.launch {
                                        seekJob?.cancelAndJoin()

                                        transitionState.animateTo(
                                            targetState = !shouldDismiss
                                        )
                                    }
                                }
                            },
                            onDragCancel = {
                                if (canDismiss) {
                                    scope.launch {
                                        seekJob?.cancelAndJoin()

                                        transitionState.animateTo(true)
                                    }
                                }
                            }
                        )
                    }
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(bottom = contentBottomPadding)
            ) {
                NowPlayingHeader(
                    source = PlaybackSource.Album,
                    sourceName = "アイマイモコ",
                    onCollapse = onCollapse,
                    onQueueClick = {},
                    animatedVisibilityScope = animatedVisibilityScope
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    TrackArtwork(
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope,
                        artworkCornerPercent = artworkCornerPercent
                    )

                    TrackInformation(
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope,
                        modifier = Modifier
                            .padding(top = 28.dp)
                            .graphicsLayer {
                                alpha = informationAlpha
                            }
                    )
                }
            }

            PlaybackControlsSheet(
                animatedVisibilityScope = animatedVisibilityScope,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp),
                isQuickSettingsExpanded = isQuickSettingsExpanded,
                onExpandClick = {
                    isQuickSettingsExpanded = !isQuickSettingsExpanded
                }
            )
        }
    }
}