package org.selostudios.tracker_presentation.ui.trackeroverview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import org.selostudios.core.util.UiEvent
import org.selostudios.core_ui.LocalSpacing
import org.selostudios.core.R
import org.selostudios.tracker_presentation.ui.trackeroverview.components.*


@ExperimentalCoilApi
@Composable
fun TrackerOverviewScreen(
    onNavigateToSearch: (String, Int, Int, Int) -> Unit,
    viewModel: TrackerOverviewViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.medium)
    ){
        item {
            NutrientsHeader(state = state)
            Spacer(modifier = Modifier.height(spacing.medium))
            DaySelector(
                date = state.date,
                onPreviousClicked = { viewModel.onEvent(TrackerOverviewEvent.OnPreviousDayClick) },
                onNextClicked = { viewModel.onEvent(TrackerOverviewEvent.OnNextDayClick) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.medium)
            )
            Spacer(modifier = Modifier.height(spacing.medium))
        }
        items(state.meals) { meal ->
            ExpandableMeal(
                meal = meal,
                onToggleMealClick = { viewModel.onEvent(TrackerOverviewEvent.OnToggleMealClick(meal)) },
                content = {
                          Column(
                              modifier = Modifier
                                  .fillMaxWidth()
                                  .padding(horizontal = spacing.small)
                          ) {
                              state.trackedFoods
                                  .filter { it.mealType == meal.mealType }
                                  .forEach { food ->
                                  TrackedFoodItem(
                                      trackedFood = food,
                                      onDeleteClick = {
                                          viewModel.onEvent(
                                              TrackerOverviewEvent.OnDeleteTrackedFoodClick(food)
                                          )
                                      }
                                  )
                                  Spacer(modifier = Modifier.height(spacing.medium))
                              }
                              AddButton(
                                  text = stringResource(
                                      id = R.string.add_meal,
                                      meal.name.asString(context) //Fills in placeholder with value
                                  ),
                                  onClick = {
                                      onNavigateToSearch(
                                          meal.name.asString(context),
                                          state.date.dayOfMonth,
                                          state.date.monthValue,
                                          state.date.year
                                      )
                                  },
                                  modifier = Modifier.fillMaxWidth()
                              )
                          }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}