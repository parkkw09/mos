package app.peter.mos.data.repositories

import app.peter.mos.data.source.model.google.youtube.Playlist
import app.peter.mos.data.source.model.google.youtube.PlaylistItem
import app.peter.mos.data.source.model.google.youtube.Subscription as DataSubscription
import app.peter.mos.data.source.remote.GoogleApi
import app.peter.mos.domain.model.google.PlayItem
import app.peter.mos.domain.model.google.PlayList
import app.peter.mos.domain.model.google.Subscription
import app.peter.mos.domain.repository.GoogleRepository
import javax.inject.Inject

class GoogleRepositoryImpl @Inject constructor(private val api: GoogleApi) : GoogleRepository {

    override suspend fun getSubscriptions(): List<Subscription> {
        val response = api.getSubscriptionList()
        return response.items.map { it.toDomain() }
    }

    override suspend fun getPlaylist(channelId: String): List<PlayList> {
        val response = api.getPlayList(channelId)
        return response.items.map { it.toDomain() }
    }

    override suspend fun getContentDetail(itemId: String): PlayItem {
        val response = api.getPlayItem(itemId)
        val item =
                response.items.firstOrNull()
                        ?: throw NoSuchElementException(
                                "PlaylistItem not found for itemId: $itemId"
                        )
        return item.toDomain()
    }

    // ── Mapping: Data → Domain ──────────────────────────────────────────

    private fun DataSubscription.toDomain() =
            Subscription(
                    publishedAt = snippet.publishedAt,
                    title = snippet.title,
                    description = snippet.description,
                    resourceId = snippet.resourceId.channelId,
                    channelId = snippet.channelId,
                    thumbnails = snippet.thumbnails.high.url
            )

    private fun Playlist.toDomain() =
            PlayList(
                    publishedAt = snippet.publishedAt,
                    channelId = snippet.channelId,
                    title = snippet.title,
                    description = snippet.description,
                    thumbnails = snippet.thumbnails.high.url,
                    channelTitle = snippet.channelTitle,
                    localized = "${snippet.localized.title} - ${snippet.localized.description}"
            )

    private fun PlaylistItem.toDomain() =
            PlayItem(
                    channelId = snippet.channelId,
                    title = snippet.title,
                    description = snippet.description,
                    thumbnails = snippet.thumbnails.high.url,
                    channelTitle = snippet.channelTitle,
                    playlistId = snippet.playlistId,
                    resourceId = snippet.resourceId.videoId
            )
}
