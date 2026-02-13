package app.peter.mos.domain.model

data class PlayItem(
    val channelId: String,
    val title: String,
    val description: String,
    val thumbnails: String,
    val channelTitle: String,
    val playlistId: String,
    val resourceId: String
)
