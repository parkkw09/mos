package app.peter.mos.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cultural_events")
data class CulturalEventEntity(
        @PrimaryKey val title: String,
        val date: String,
        val place: String,
        val mainImage: String,
        val orgName: String,
        val useFee: String,
        val createdAt: Long = System.currentTimeMillis()
)
