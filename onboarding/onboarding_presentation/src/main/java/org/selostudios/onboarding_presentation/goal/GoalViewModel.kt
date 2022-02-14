package org.selostudios.onboarding_presentation.goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.selostudios.core.doman.model.GoalType
import org.selostudios.core.doman.preferences.Preferences
import org.selostudios.core.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(private val preferences: Preferences): ViewModel() {
    var selectedGoalType by mutableStateOf<GoalType>(GoalType.KeepWeight)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onGoalTypeClick(type: GoalType) {
        selectedGoalType = type
    }

    fun onNextClick() {
        viewModelScope.launch {
            preferences.saveGoalType(selectedGoalType)
            _uiEvent.send(UiEvent.Success)
        }
    }
}