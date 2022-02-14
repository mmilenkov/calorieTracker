package org.selostudios.core.util

// Event from viewModel to composables
// Navigating
// Showing snackbar
// popping backstack
sealed class UiEvent {
    object Success: UiEvent()
    object NavigateUp: UiEvent()
    data class ShowSnackbar(val message: UiText): UiEvent()
}
