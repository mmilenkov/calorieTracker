package org.selostudios.tracker_domain.usecase

//Helps with the viewModel constructor
data class TrackerUseCases(
    val trackFood: TrackFood,
    val searchForFood: SearchForFood,
    val deleteTrackedFood: DeleteTrackedFood,
    val calculateMealNutrients: CalculateMealNutrients,
    val getFoodsForDate: GetFoodsForDate
)
