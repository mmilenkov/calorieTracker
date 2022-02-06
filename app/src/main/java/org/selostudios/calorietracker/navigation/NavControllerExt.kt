package org.selostudios.calorietracker.navigation

import androidx.navigation.NavController
import org.selostudios.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(event.route)
}