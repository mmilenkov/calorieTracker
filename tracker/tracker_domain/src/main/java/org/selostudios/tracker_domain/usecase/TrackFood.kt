package org.selostudios.tracker_domain.usecase

import org.selostudios.tracker_domain.model.MealType
import org.selostudios.tracker_domain.model.TrackableFood
import org.selostudios.tracker_domain.model.TrackedFood
import org.selostudios.tracker_domain.repository.TrackerRepository
import java.time.LocalDate
import kotlin.math.roundToInt

class TrackFood(private val repo: TrackerRepository) {
    suspend operator fun invoke(
        food: TrackableFood,
        amount: Int,
        mealType: MealType,
        date: LocalDate
    ) {
        repo.insertFood(TrackedFood(
            name = food.name,
            carbs = ((food.carbsPer100g / 100f) * amount).roundToInt(),
            protein = ((food.proteinPer100g / 100f) * amount).roundToInt(),
            fat = ((food.fatsPer100g / 100f) * amount).roundToInt(),
            calories = ((food.caloriesPer100g / 100f) * amount).roundToInt(),
            imageUrl = food.imageUrl,
            mealType = mealType,
            amountGrams = amount,
            date = date
        ))
    }
}