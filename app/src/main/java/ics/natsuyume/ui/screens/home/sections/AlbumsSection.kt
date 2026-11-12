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
fun AlbumsSection() {
    data class Album(
        val title: String,
        val artist: String,
        val songCount: Int,
        val year: Int,
        val artwork: Int
    )

    val albums = listOf(
        Album(
            title = "heart bookmark",
            artist = "水瀬いのり",
            songCount = 7,
            year = 2024,
            artwork = R.drawable.heart_bookmark
        ),
        Album(
            title = "glow",
            artist = "水瀬いのり",
            songCount = 14,
            year = 2022,
            artwork = R.drawable.glow
        ),
        Album(
            title = "Turquoise",
            artist = "水瀬いのり",
            songCount = 8,
            year = 2025,
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
                text = "Albums",
                style = MaterialTheme.typography.titleLarge
            )

            IconButton(
                onClick = {
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_forward_24),
                    contentDescription = "See all albums"
                )
            }
        }

        BoxWithConstraints {
            val cardWidth = maxWidth * 0.85f

            HorizontalCenteredHeroCarousel(
                state = rememberCarouselState { albums.size },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                preferredItemWidth = cardWidth,
                itemSpacing = 8.dp,
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) { index ->
                val album = albums[index]
                val cardShape = MaterialTheme.shapes.large

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .maskClip(cardShape),
                    shape = cardShape,
                    color = MaterialTheme.colorScheme.surface
                ) {
                    MediaCard(
                        title = album.title,
                        subtitle = album.artist,
                        metadata = "${album.songCount} songs · ${album.year}",
                        artwork = album.artwork,
                        onClick = {},
                        onPlayClick = {},
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}