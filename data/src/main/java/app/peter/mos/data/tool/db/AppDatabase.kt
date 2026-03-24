package app.peter.mos.data.tool.db

import androidx.room.Database
import androidx.room.RoomDatabase
import app.peter.mos.data.source.local.dao.CulturalEventDao
import app.peter.mos.data.source.local.entity.CulturalEventEntity

@Database(entities = [CulturalEventEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun culturalEventDao(): CulturalEventDao
}
