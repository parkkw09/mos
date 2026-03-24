package app.peter.mos.data.source.local.dao

import androidx.room.*
import app.peter.mos.data.source.local.entity.CulturalEventEntity

@Dao
interface CulturalEventDao {
    @Query("SELECT * FROM cultural_events") suspend fun getAll(): List<CulturalEventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<CulturalEventEntity>)

    @Query("DELETE FROM cultural_events") suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM cultural_events") suspend fun getCount(): Int
}
