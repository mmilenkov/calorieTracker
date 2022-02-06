package org.selostudios.tracker_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import org.selostudios.tracker_data.local.entity.FoodEntity

@Database(entities =  [FoodEntity::class], version = 1)
abstract class TrackerDatabase: RoomDatabase() {
    abstract val dao: TrackerDAO
}