package org.selostudios.tracker_domain.usecase

import org.selostudios.core.doman.model.ActivityLevel
import org.selostudios.core.doman.model.Gender
import org.selostudios.core.doman.model.GoalType
import org.selostudios.core.doman.model.UserInfo
import org.selostudios.core.doman.preferences.Preferences
import org.selostudios.tracker_domain.model.MealType
import org.selostudios.tracker_domain.model.TrackedFood
import kotlin.math.roundToInt

class CalculateMealNutrients(private val preferences: Preferences) {

    operator fun invoke(trackedFoods: List<TrackedFood>): Result {
        val nutrients = trackedFoods.groupBy {
            it.mealType
        }.mapValues { entry ->
            val type = entry.key
            val foods = entry.value
            MealNutrients(
                carbs = foods.sumOf { it.carbs },
                protein = foods.sumOf { it.protein },
                fat = foods.sumOf { it.fat },
                calories = foods.sumOf { it.calories },
                mealType = type
            )
        }

        val totalCarbs = nutrients.values.sumOf { it.carbs }
        val totalProtein = nutrients.values.sumOf { it.protein }
        val totalFat = nutrients.values.sumOf { it.fat }
        val totalCalories = nutrients.values.sumOf { it.calories }

        val userInfo = preferences.loadUserInfo()
        val caloriesGoal = dailyCalorieRequirement(userInfo)
        val carbsGoal = (caloriesGoal * userInfo.carbRatio / 4f).roundToInt() //1g of carbs =  4cal
        val proteinGoal = (caloriesGoal * userInfo.carbRatio / 4f).roundToInt() //1g of protein = 4cal
        val fatGoal = (caloriesGoal * userInfo.carbRatio / 9f).roundToInt() //1g of fat = 9cal

        return Result(
            carbsGoal = carbsGoal,
            proteinGoal = proteinGoal,
            fatGoal = fatGoal,
            caloriesGoal = caloriesGoal,
            totalCarbs = totalCarbs,
            totalProtein = totalProtein,
            totalFat = totalFat,
            totalCalories = totalCalories,
            mealMap = nutrients
        )
    }

    //Basal metabolism rate. Burned calories when nothing is done
    private fun bmr(userInfo: UserInfo): Int {
        return when(userInfo.gender) {
            is Gender.Male -> {
                (66.47f + 13.75f * userInfo.weight +
                        5f * userInfo.height - 6.75f * userInfo.age).roundToInt()
            }
            is Gender.Female ->  {
                (665.09f + 9.56f * userInfo.weight +
                        1.84f * userInfo.height - 4.67 * userInfo.age).roundToInt()
            }
        }
    }

    //Factors in activity level alongside bmr
    //This is a rather rough estimate. Not actual values
    private fun dailyCalorieRequirement(userInfo: UserInfo): Int {
        val activityFactor = when(userInfo.activityLevel) {
            is ActivityLevel.Low -> 1.2f
            is ActivityLevel.Medium -> 1.3f
            is ActivityLevel.High -> 1.4f
        }
        val caloriesExtra = when(userInfo.goalType) {
            is GoalType.LoseWeight -> -500
            is GoalType.KeepWeight -> 0
            is GoalType.GainWeight -> 500
        }
        return (bmr(userInfo) * activityFactor + caloriesExtra).roundToInt()
    }

    data class MealNutrients(
        val carbs: Int,
        val protein: Int,
        val fat: Int,
        val calories: Int,
        val mealType: MealType
    )

    data class Result(
        val carbsGoal: Int,
        val proteinGoal: Int,
        val fatGoal: Int,
        val caloriesGoal: Int,
        val totalCarbs: Int,
        val totalProtein: Int,
        val totalFat: Int,
        val totalCalories: Int,
        val mealMap: Map<MealType, MealNutrients>
    )
}