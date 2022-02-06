package org.selostudios.tracker_domain.usecase

import org.selostudios.tracker_domain.model.TrackedFood
import org.selostudios.tracker_domain.repository.TrackerRepository

class DeleteTrackedFood(private val repo: TrackerRepository) {
    suspend operator fun invoke(food: TrackedFood) {
        return repo.deleteFood(food)
    }
}