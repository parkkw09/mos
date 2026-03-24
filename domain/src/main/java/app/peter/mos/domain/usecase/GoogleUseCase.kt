package app.peter.mos.domain.usecase

import app.peter.mos.domain.model.google.PlayItem
import app.peter.mos.domain.model.google.PlayList
import app.peter.mos.domain.model.google.Subscription
import app.peter.mos.domain.repository.GoogleRepository
import javax.inject.Inject

class GetSubscriptionsUseCase @Inject constructor(private val repository: GoogleRepository) {
    suspend operator fun invoke(): List<Subscription> = repository.getSubscriptions()
}

class GetPlaylistUseCase @Inject constructor(private val repository: GoogleRepository) {
    suspend operator fun invoke(channelId: String): List<PlayList> =
            repository.getPlaylist(channelId)
}

class GetContentDetailUseCase @Inject constructor(private val repository: GoogleRepository) {
    suspend operator fun invoke(itemId: String): PlayItem = repository.getContentDetail(itemId)
}

class SaveGoogleTokenUseCase @Inject constructor(private val repository: GoogleRepository) {
    suspend operator fun invoke(token: String) = repository.saveAccessToken(token)
}

class ClearGoogleTokenUseCase @Inject constructor(private val repository: GoogleRepository) {
    suspend operator fun invoke() = repository.clearAccessToken()
}
