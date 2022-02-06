package org.selostudios.tracker_data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.selostudios.tracker_data.local.entity.FoodEntity

@Dao
interface TrackerDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackedFood(foodEntity: FoodEntity)

    @Delete
    suspend fun deleteTrackedFood(foodEntity: FoodEntity)

    @Query(
        """SELECT * 
            FROM foodentity 
            WHERE dayOfMonth = :day AND month = :month AND year = :year
        """
    )
    fun getFoodsForDate(day: Int, month: Int, year: Int) : Flow<List<FoodEntity>>
}