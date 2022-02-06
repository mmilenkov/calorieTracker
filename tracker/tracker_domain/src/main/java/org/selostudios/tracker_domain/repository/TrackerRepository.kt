package org.selostudios.tracker_domain.repository

import kotlinx.coroutines.flow.Flow
import org.selostudios.tracker_domain.model.TrackableFood
import org.selostudios.tracker_domain.model.TrackedFood
import java.time.LocalDate

interface TrackerRepository {
    suspend fun searchForFood(
        query: String,
        page: Int,
        pageSize: Int
    ) : Result<List<TrackableFood>>

    suspend fun insertFood(food: TrackedFood)

    suspend fun deleteFood(food: TrackedFood)

    fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>>
}