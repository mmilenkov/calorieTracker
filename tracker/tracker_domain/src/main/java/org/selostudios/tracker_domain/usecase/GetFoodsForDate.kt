package org.selostudios.tracker_domain.usecase

import kotlinx.coroutines.flow.Flow
import org.selostudios.tracker_domain.model.TrackedFood
import org.selostudios.tracker_domain.repository.TrackerRepository
import java.time.LocalDate

class GetFoodsForDate(private val repo: TrackerRepository) {
    operator fun invoke(date: LocalDate): Flow<List<TrackedFood>> {
        return repo.getFoodsForDate(date)
    }
}