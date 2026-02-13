package app.peter.mos.domain.repository

import app.peter.mos.domain.model.PlayItem
import app.peter.mos.domain.model.PlayList
import app.peter.mos.domain.model.Subscription

interface GoogleRepository {
    suspend fun getSubscriptions(): List<Subscription>
    suspend fun getPlaylist(channelId: String): List<PlayList>
    suspend fun getContentDetail(itemId: String): PlayItem
}
