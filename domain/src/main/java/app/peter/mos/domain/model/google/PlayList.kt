package app.peter.mos.domain.model.google

data class PlayList(
    val publishedAt: String,
    val channelId: String,
    val title: String,
    val description: String,
    val thumbnails: String,
    val channelTitle: String,
    val localized: String
)