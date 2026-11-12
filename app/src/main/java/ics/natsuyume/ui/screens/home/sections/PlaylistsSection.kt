package ics.natsuyume.ui.screens.home.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ics.natsuyume.R
import androidx.compose.material3.carousel.HorizontalCenteredHeroCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import ics.natsuyume.ui.screens.home.widgets.MediaCard

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PlaylistsSection() {
    data class Playlist(
        val name: String,
        val songCount: Int,
        val artwork: Int
    )

    val playlists = listOf(
        Playlist(
            name = "Natsuyume",
            songCount = 4,
            artwork = R.drawable.albumcover
        ),
        Playlist(
            name = "Bing chilling",
            songCount = 17,
            artwork = R.drawable.heart_bookmark
        ),
        Playlist(
            name = "Racist Chinese songs ASMP",
            songCount = 9,
            artwork = R.drawable.turquoise
        )
    )

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Playlists",
                style = MaterialTheme.typography.titleLarge
            )

            IconButton(
                onClick = {
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_forward_24),
                    contentDescription = "See all playlists"
                )
            }
        }

        BoxWithConstraints{
            val cardWidth = maxWidth * 0.85f

            HorizontalCenteredHeroCarousel(
                state = rememberCarouselState { playlists.size },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                preferredItemWidth = cardWidth,
                itemSpacing = 8.dp,
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) { index ->
                val playlist = playlists[index]
                val cardShape = MaterialTheme.shapes.large

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .maskClip(cardShape),
                    shape = cardShape,
                    color = MaterialTheme.colorScheme.surface
                ) {
                    MediaCard(
                        title = playlist.name,
                        subtitle = null,
                        metadata = "${playlist.songCount} songs",
                        artwork = playlist.artwork,
                        onClick = {
                        },
                        onPlayClick = {
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}