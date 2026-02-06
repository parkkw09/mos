package app.peter.mos.data.repositories

import app.peter.mos.data.source.remote.GoogleApi
import app.peter.mos.domain.repository.GoogleRepository
import javax.inject.Inject

class GoogleRepositoryImpl @Inject constructor(private val api: GoogleApi) : GoogleRepository {
    override suspend fun test(): Result<Unit> {
        return try {
            val response = api.test()
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("API Error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
