package ics.natsuyume.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.SeekableTransitionState
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ics.natsuyume.ui.screens.home.sections.AlbumsSection
import ics.natsuyume.ui.screens.home.sections.ArtistsSection
import ics.natsuyume.ui.screens.home.sections.PlaylistsSection
import ics.natsuyume.ui.screens.home.sections.QuickAccessSection
import ics.natsuyume.ui.screens.nowplaying.NowPlayingScreen
import ics.natsuyume.ui.widgets.MiniPlayer
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    val transitionState = remember {
        SeekableTransitionState(false)
    }

    val transition = rememberTransition(
        transitionState = transitionState,
        label = "nowPlayingTransition"
    )

    val scope = rememberCoroutineScope()

    val artworkCornerPercent by remember(
        transitionState.currentState,
        transitionState.targetState,
        transitionState.fraction
    ) {
        derivedStateOf {
            val current = transitionState.currentState
            val target = transitionState.targetState
            val fraction = transitionState.fraction

            when {
                !current && !target -> 50
                current && target -> 8
                !current && target -> (50 - (42 * fraction)).toInt().coerceIn(8, 50)
                current && !target -> (8 + (42 * fraction)).toInt().coerceIn(8, 50)
                else -> 8
            }
        }
    }

    SharedTransitionLayout {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = MaterialTheme.colorScheme.background
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            top = 16.dp,
                            bottom = 120.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        item {
                            Text(
                                text = "Natsuyume (Preview)",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }

                        item { QuickAccessSection() }
                        item { AlbumsSection() }
                        item { ArtistsSection() }
                        item { PlaylistsSection() }
                    }

                    transition.AnimatedVisibility(
                        visible = { !it },
                        modifier = Modifier.align(Alignment.BottomCenter),
                        enter = fadeIn(animationSpec = tween(300)),
                        exit = fadeOut(animationSpec = tween(300))
                    ) {
                        MiniPlayer(
                            title = "夏夢",
                            artist = "水瀬いのり",
                            album = "アイマイモコ",
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedVisibilityScope = this@AnimatedVisibility,
                            artworkCornerPercent = artworkCornerPercent,
                            onClick = {
                                scope.launch {
                                    transitionState.animateTo(true)
                                }
                            }
                        )
                    }
                }
            }

            transition.AnimatedVisibility(
                visible = { it },
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            MaterialTheme.colorScheme.scrim.copy(alpha = 0.32f)
                        )
                )
            }

            transition.AnimatedVisibility(
                visible = { it },
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300))
            ) {
                NowPlayingScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility,
                    transitionState = transitionState,
                    artworkCornerPercent = artworkCornerPercent,
                    onCollapse = {
                        scope.launch {
                            transitionState.animateTo(false)
                        }
                    }
                )
            }
        }
    }
}