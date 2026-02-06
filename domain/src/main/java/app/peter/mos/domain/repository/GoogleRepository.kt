package app.peter.mos.domain.repository

interface GoogleRepository {
    // TODO: Define Google API specific methods
    suspend fun test(): Result<Unit>
}
