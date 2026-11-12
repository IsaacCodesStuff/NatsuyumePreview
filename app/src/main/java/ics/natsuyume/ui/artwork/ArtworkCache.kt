package ics.natsuyume.ui.artwork

import android.content.Context
import coil3.ImageLoader
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import okio.Path.Companion.toOkioPath

object ArtworkCache {

    @Volatile
    private var instance: ImageLoader? = null

    fun get(context: Context): ImageLoader {
        return instance ?: synchronized(this) {
            instance ?: ImageLoader.Builder(context.applicationContext)
                .memoryCache {
                    MemoryCache.Builder()
                        .maxSizePercent(context, 0.20)
                        .build()
                }
                .diskCache {
                    DiskCache.Builder()
                        .directory(context.cacheDir.resolve("artwork_cache").toOkioPath())
                        .maxSizeBytes(64L * 1024 * 1024)
                        .build()
                }
                .build()
                .also { instance = it }
        }
    }
}