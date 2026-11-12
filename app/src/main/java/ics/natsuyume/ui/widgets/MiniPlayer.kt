@file:OptIn(
    ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalSharedTransitionApi::class
)
package ics.natsuyume.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.toShape
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.zIndex
import ics.natsuyume.R

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MiniPlayer(
    title: String,
    artist: String,
    album: String,
    onClick: () -> Unit,
    artworkCornerPercent: Int,
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
) {
    var isFavorite by rememberSaveable {
        mutableStateOf(false)
    }

    var isPlaying by rememberSaveable {
        mutableStateOf(false)
    }

    val rotation = remember {
        Animatable(0f)
    }

    val favoriteRotation = remember {
        Animatable(0f)
    }

    val scope = rememberCoroutineScope()

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

    BoxWithConstraints(
        modifier = modifier.fillMaxWidth()
    ) {
        val compact = maxWidth < 380.dp

        val artworkSize = if (compact) 56.dp else 72.dp
        val artworkImageSize = if (compact) 46.dp else 60.dp

        val innerPadding = if (compact) 8.dp else 12.dp
        val controlSpacing = if (compact) (-6).dp else (-4).dp
        val horizontalPadding = if (compact) 12.dp else 22.dp

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = horizontalPadding,
                    vertical = 12.dp
                )
                .height(96.dp)
                .clickable(onClick = onClick),
            shape = RoundedCornerShape(52.dp),
            tonalElevation = 2.dp
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                MusicWaveform(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(32.dp)
                        .zIndex(0f),
                    isPlaying = isPlaying
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                        .padding(horizontal = innerPadding)
                        .zIndex(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val sharedModifier = if (
                            sharedTransitionScope != null &&
                            animatedVisibilityScope != null
                        ) {
                            with(sharedTransitionScope) {
                                Modifier.sharedElement(
                                    sharedContentState = rememberSharedContentState("album_artwork"),
                                    animatedVisibilityScope = animatedVisibilityScope
                                )
                            }
                        } else {
                            Modifier
                        }

                        Box(
                            modifier = Modifier.size(artworkSize),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                progress = { 0.65f },
                                modifier = Modifier.size(artworkSize),
                                strokeWidth = 3.dp,
                                color = MaterialTheme.colorScheme.primary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant
                            )

                            Image(
                                painter = painterResource(R.drawable.albumcover),
                                contentDescription = "Album cover",
                                modifier = Modifier
                                    .size(artworkImageSize)
                                    .then(sharedModifier)
                                    .clip(RoundedCornerShape(percent = artworkCornerPercent))
                            )
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(0.dp)
                        ) {
                            val titleSharedModifier =
                                if (sharedTransitionScope != null && animatedVisibilityScope != null) {
                                    with(sharedTransitionScope) {
                                        Modifier.sharedBounds(
                                            sharedContentState = rememberSharedContentState("track_title"),
                                            animatedVisibilityScope = animatedVisibilityScope,
                                            enter = fadeIn(),
                                            exit = fadeOut(),
                                            resizeMode = SharedTransitionScope.ResizeMode.scaleToBounds()
                                        )
                                    }
                                } else Modifier

                            val artistSharedModifier =
                                if (sharedTransitionScope != null && animatedVisibilityScope != null) {
                                    with(sharedTransitionScope) {
                                        Modifier.sharedBounds(
                                            sharedContentState = rememberSharedContentState("track_artist"),
                                            animatedVisibilityScope = animatedVisibilityScope,
                                            enter = fadeIn(),
                                            exit = fadeOut(),
                                            resizeMode = SharedTransitionScope.ResizeMode.scaleToBounds()
                                        )
                                    }
                                } else Modifier

                            val albumSharedModifier =
                                if (sharedTransitionScope != null && animatedVisibilityScope != null) {
                                    with(sharedTransitionScope) {
                                        Modifier.sharedBounds(
                                            sharedContentState = rememberSharedContentState("track_album"),
                                            animatedVisibilityScope = animatedVisibilityScope,
                                            enter = fadeIn(),
                                            exit = fadeOut(),
                                            resizeMode = SharedTransitionScope.ResizeMode.scaleToBounds()
                                        )
                                    }
                                } else Modifier
                            Text(
                                text = title,
                                modifier = titleSharedModifier,
                                fontSize = 20.sp,
                                lineHeight = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Text(
                                text = album,
                                modifier = albumSharedModifier,
                                fontSize = 14.sp,
                                lineHeight = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Text(
                                text = artist,
                                modifier = artistSharedModifier,
                                fontSize = 10.sp,
                                lineHeight = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(controlSpacing)
                        ) {
                            Box(
                                modifier = Modifier.size(40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(if (compact) 32.dp else 36.dp)
                                        .graphicsLayer {
                                            rotationZ = favoriteRotation.value
                                        }
                                        .background(
                                            color = if (isFavorite) {
                                                MaterialTheme.colorScheme.errorContainer
                                            } else {
                                                MaterialTheme.colorScheme.surfaceVariant
                                            },
                                            shape = MaterialShapes.Pill.toShape()
                                        )
                                )

                                IconButton(
                                    onClick = {
                                        isFavorite = !isFavorite

                                        scope.launch {
                                            favoriteRotation.animateTo(
                                                targetValue = favoriteRotation.value + 360f,
                                                animationSpec = tween(
                                                    durationMillis = 500,
                                                    easing = FastOutSlowInEasing
                                                )
                                            )
                                        }
                                    }
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
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }

                            IconButton(
                                onClick = {}
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_skip_previous_24),
                                    contentDescription = "Previous",
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            Box(
                                modifier = Modifier.size(48.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(if (compact) 44.dp else 52.dp)
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
                                    }
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
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }

                            IconButton(
                                onClick = {}
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_skip_next_24),
                                    contentDescription = "Next",
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}