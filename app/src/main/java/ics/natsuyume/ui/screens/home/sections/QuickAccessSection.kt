package ics.natsuyume.ui.screens.home.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ics.natsuyume.R

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun QuickAccessSection() {
    data class QuickAccessItem(
        val title: String,
        val icon: Int
    )

    val items = listOf(
        QuickAccessItem("Queues", R.drawable.baseline_queue_music_24),
        QuickAccessItem("Library", R.drawable.baseline_library_music_24),
        QuickAccessItem("Favorites", R.drawable.baseline_favorite_24),
        QuickAccessItem("Settings", R.drawable.baseline_settings_24)
    )

    Column {
        Text(
            text = "Quick Access",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 12.dp
            )
        )
        HorizontalUncontainedCarousel(
            state = rememberCarouselState { items.count() },
            itemWidth = 160.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            itemSpacing = 8.dp
        ) { index ->
            val item = items[index]
            val cardShape = MaterialTheme.shapes.large

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .maskClip(cardShape),
                shape = cardShape,
                color = MaterialTheme.colorScheme.primaryContainer
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )

                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}