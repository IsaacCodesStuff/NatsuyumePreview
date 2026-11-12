package ics.natsuyume.ui.screens.nowplaying.widgets

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ics.natsuyume.R

@Composable
fun TrackArtwork(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    artworkCornerPercent: Int,
    modifier: Modifier = Modifier
) {
    val sharedModifier = with(sharedTransitionScope) {
        Modifier.sharedElement(
            sharedContentState = rememberSharedContentState(
                key = "album_artwork"
            ),
            animatedVisibilityScope = animatedVisibilityScope
        )
    }

    Image(
        painter = painterResource(R.drawable.albumcover),
        contentDescription = "Album artwork",
        modifier = modifier
            .size(360.dp)
            .then(sharedModifier)
            .clip(RoundedCornerShape(percent = artworkCornerPercent))
    )
}
