package ics.natsuyume

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ics.natsuyume.ui.screens.home.HomeScreen
import ics.natsuyume.ui.theme.NatsuyumePreviewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NatsuyumePreviewTheme {
                HomeScreen()
            }
        }
    }
}
