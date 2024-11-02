package com.mlsa_uet_nc.repeatalarm.presentation.ui.add_alarm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalTime

data class AddEditAlarmUiState(
    val repeatIntervalTime: LocalTime = LocalTime.of(0, 0),
    val isAllDay: Boolean = true,
    val selectedSound: String = "Default",
    val isVibrationEnabled: Boolean = true,
    val selectedDays: List<String> = emptyList()
)

class AddEditAlarmViewModel : ViewModel() {
    // UI state encapsulated in a MutableState
    private val _uiState = MutableStateFlow(AddEditAlarmUiState())
    val uiState: StateFlow<AddEditAlarmUiState> = _uiState


    fun onCloseClick() {
    }

    fun onDoneClick() {

    }

    fun toggleAllDay(isAllDay: Boolean) {
        _uiState.value = _uiState.value.copy(isAllDay = isAllDay)
    }

    fun selectSound(sound: String) {
        _uiState.value = _uiState.value.copy(selectedSound = sound)
    }

    fun toggleVibration(isEnabled: Boolean) {
        _uiState.value = _uiState.value.copy(isVibrationEnabled = isEnabled)
    }

    fun toggleDaySelection(day: String) {
        val currentSelectedDays = _uiState.value.selectedDays.toMutableList()
        if (currentSelectedDays.contains(day)) {
            currentSelectedDays.remove(day)
        } else {
            currentSelectedDays.add(day)
        }
        _uiState.value = _uiState.value.copy(selectedDays = currentSelectedDays)
    }

    fun updateRepeatIntervalTime(time: LocalTime){
        _uiState.value = _uiState.value.copy( repeatIntervalTime = time)
    }
}

