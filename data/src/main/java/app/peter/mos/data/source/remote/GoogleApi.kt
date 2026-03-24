package app.peter.mos.data.source.remote

import app.peter.mos.data.source.model.google.youtube.Playlist
import app.peter.mos.data.source.model.google.youtube.PlaylistItem
import app.peter.mos.data.source.model.google.youtube.Subscription
import app.peter.mos.data.source.model.google.youtube.YoutubeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleApi {

    @GET("youtube/v3/subscriptions")
    suspend fun getSubscriptionList(
        @Query("part") part: String = "snippet,contentDetails",
        @Query("mine") mine: Boolean = true,
        @Query("maxResults") maxResults: Int = 50
    ): YoutubeResponse<Subscription>

    @GET("youtube/v3/playlists")
    suspend fun getPlayList(
        @Query("channelId") channelId: String,
        @Query("part") part: String = "snippet,status",
        @Query("maxResults") maxResults: Int = 50
    ): YoutubeResponse<Playlist>

    @GET("youtube/v3/playlistItems")
    suspend fun getPlayItem(
        @Query("playlistId") playlistId: String,
        @Query("part") part: String = "snippet",
        @Query("maxResults") maxResults: Int = 50
    ): YoutubeResponse<PlaylistItem>
}
