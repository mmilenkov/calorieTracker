package org.selostudios.tracker_domain.usecase

import org.selostudios.tracker_domain.model.TrackableFood
import org.selostudios.tracker_domain.repository.TrackerRepository

class SearchForFood(private val repo: TrackerRepository) {
    suspend operator fun invoke(query: String, page: Int = 1, pageSize: Int = 40): Result<List<TrackableFood>> {
        if(query.isBlank()) {
            return Result.success(emptyList())
        }
        return repo.searchForFood(query.trim(), page, pageSize)
    } 
}