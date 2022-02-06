package org.selostudios.tracker_data.mapper

import org.selostudios.tracker_data.local.entity.FoodEntity
import org.selostudios.tracker_domain.model.MealType
import org.selostudios.tracker_domain.model.TrackedFood
import java.time.LocalDate

fun FoodEntity.toTrackedFood() : TrackedFood {
    return TrackedFood(
        name = name,
        carbs = carbs,
        protein = protein,
        fat = fat,
        imageUrl = imageUrl,
        mealType = MealType.fromString(mealType),
        amountGrams = amount,
        date = LocalDate.of(year, month, dayOfMonth),
        calories = calories,
        id = id
    )
}

fun TrackedFood.toFoodEntity() : FoodEntity {
    return FoodEntity(
        name = name,
        carbs = carbs,
        protein = protein,
        fat = fat,
        imageUrl = imageUrl,
        mealType = mealType.name,
        amount = amountGrams,
        year = date.year,
        month = date.monthValue,
        dayOfMonth = date.dayOfMonth,
        calories = calories,
        id = id
    )
}