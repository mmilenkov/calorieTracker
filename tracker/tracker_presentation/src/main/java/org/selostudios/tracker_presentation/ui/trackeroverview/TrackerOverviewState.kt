package org.selostudios.tracker_presentation.ui.trackeroverview

import org.selostudios.tracker_domain.model.TrackedFood
import java.time.LocalDate

data class TrackerOverviewState(
    val totalCarbs: Int = 0,
    val totalProtein: Int = 0,
    val totalFat: Int = 0,
    val totalCalories: Int = 0,
    val carbGoal: Int = 0,
    val proteinGoal: Int = 0,
    val fatGoal: Int = 0,
    val calorieGoal: Int = 0,
    val date: LocalDate = LocalDate.now(),
    val trackedFoods: List<TrackedFood> = emptyList(),
    val meals: List<Meal> = defaultMeals
)