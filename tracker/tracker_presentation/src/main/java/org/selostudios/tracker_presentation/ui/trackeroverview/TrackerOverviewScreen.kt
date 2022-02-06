package org.selostudios.tracker_presentation.ui.trackeroverview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import org.selostudios.core.util.UiEvent
import org.selostudios.core_ui.LocalSpacing
import org.selostudios.tracker_presentation.ui.trackeroverview.components.NutrientsHeader

@Composable
fun TrackerOverviewScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TrackerOverviewViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current
    LazyColumn(
        modifier =  Modifier.fillMaxSize()
        .padding(bottom= spacing.medium)
    ){
        item {
            NutrientsHeader(state = state)
        }
    }
}