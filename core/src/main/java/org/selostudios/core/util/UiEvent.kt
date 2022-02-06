package org.selostudios.core.util

// Event from viewModel to composables
// Navigating
// Showing snackbar
// popping backstack
sealed class UiEvent {
    data class Navigate(val route: String): UiEvent()
    object NavigateUp: UiEvent()
    data class ShowSnackbar(val message: UiText): UiEvent()
}
