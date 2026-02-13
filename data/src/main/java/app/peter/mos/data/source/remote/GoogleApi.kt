package app.peter.mos.data.source.remote

import app.peter.mos.data.source.model.google.youtube.Playlist
import app.peter.mos.data.source.model.google.youtube.PlaylistItem
import app.peter.mos.data.source.model.google.youtube.Subscription
import app.peter.mos.data.source.model.google.youtube.YoutubeResponse
import retrofit2.http.GET

interface GoogleApi {
    @GET("/youtube/v3/subscriptions") suspend fun getSubscriptionList(): YoutubeResponse<Subscription>
    @GET("/youtube/v3/playlists") suspend fun getPlayList(channelId: String): YoutubeResponse<Playlist>
    @GET("/youtube/v3/playlistItems") suspend fun getPlayItem(itemId: String): YoutubeResponse<PlaylistItem>
}
