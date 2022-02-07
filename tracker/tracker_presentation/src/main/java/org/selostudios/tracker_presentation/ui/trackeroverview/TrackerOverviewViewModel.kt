package org.selostudios.tracker_presentation.ui.trackeroverview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.selostudios.core.doman.preferences.Preferences
import org.selostudios.core.navigation.Route
import org.selostudios.core.util.UiEvent
import org.selostudios.tracker_domain.usecase.TrackerUseCases
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    preferences: Preferences,
    private val trackerUseCases: TrackerUseCases
): ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private var refreshJob: Job? = null

    var state by mutableStateOf(TrackerOverviewState())
        private set
    init {
        preferences.saveShouldShowOnboarding(false)
    }

    fun onEvent(event: TrackerOverviewEvent) {
        when (event) {
            is TrackerOverviewEvent.OnAddFoodClick -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        //Provides arguments to the navController in our MainActivity
                        UiEvent.Navigate(route = Route.SEARCH +
                        "/${event.meal.mealType.name}" +
                        "/${state.date.dayOfMonth}" +
                        "/${state.date.monthValue}" +
                        "/${state.date.year}"
                        )
                    )
                }
            }
            is TrackerOverviewEvent.OnDeleteTrackedFoodClick -> {
                viewModelScope.launch {
                    trackerUseCases.deleteTrackedFood(event.trackedFood)
                    //refresh state as possible change
                    refreshFoods()
                }
            }
            is TrackerOverviewEvent.OnToggleMealClick -> {
                state = state.copy(
                    meals = state.meals.map {
                        if (it.name == event.meal.name) {
                            it.copy(isExpanded = !it.isExpanded)
                        } else it
                    }
                )
            }
            is TrackerOverviewEvent.OnPreviousDayClick -> {
                state = state.copy(
                    date = state.date.minusDays(1)
                )
                refreshFoods()
            }
            is TrackerOverviewEvent.OnNextDayClick -> {
                state = state.copy(
                    date = state.date.plusDays(1)
                )
                refreshFoods()
            }
        }
    }

    private fun refreshFoods() {
        refreshJob?.cancel()
        refreshJob = trackerUseCases.getFoodsForDate(state.date).onEach { foods ->
            val result = trackerUseCases.calculateMealNutrients(foods)
            state = state.copy(
                totalCarbs = result.totalCarbs,
                totalProtein = result.totalProtein,
                totalFat = result.totalFat,
                totalCalories = result.totalCalories,
                carbGoal = result.carbsGoal,
                proteinGoal = result.proteinGoal,
                fatGoal = result.fatGoal,
                calorieGoal = result.caloriesGoal,
                meals = state.meals.map {
                    val nutrientsForMeal = result.mealMap[it.mealType] ?:
                    return@map it.copy(
                        carbs = 0,
                        protein = 0,
                        fat = 0,
                        calories = 0
                    )
                    it.copy(
                        carbs = nutrientsForMeal.carbs,
                        protein = nutrientsForMeal.protein,
                        fat = nutrientsForMeal.fat,
                        calories = nutrientsForMeal.calories
                    )
                }
            )
        }.launchIn(viewModelScope)
    }
}