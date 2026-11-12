package ics.natsuyume.ui.artwork

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage

@Composable
fun ArtworkLoader(
    artwork: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    val context = LocalContext.current

    val imageLoader = remember(context) {
        ArtworkCache.get(context)
    }

    AsyncImage(
        model = artwork,
        contentDescription = contentDescription,
        imageLoader = imageLoader,
        contentScale = contentScale,
        modifier = modifier
    )
}