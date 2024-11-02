package com.mlsa_uet_nc.repeatalarm.ui.add_alarm

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import com.commandiron.wheel_picker_compose.core.WheelTextPicker
import com.mlsa_uet_nc.repeatalarm.presentation.ui.add_alarm.AddEditAlarmViewModel
import com.mlsa_uet_nc.repeatalarm.presentation.ui.components.TextSwitch
import com.mlsa_uet_nc.repeatalarm.presentation.ui.components.TimePickerCard
import java.time.LocalTime

@Composable
fun AddEditAlarmScreen(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit = {},
    onDoneClick: () -> Unit = {},
    mViewModel : AddEditAlarmViewModel = viewModel()
) {

    val uiState by mViewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        BottomSheetControlButtons(onCloseClick = onCloseClick, onDoneClick = onDoneClick)
        Box (
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            WheelTextPicker(
                modifier = Modifier.pointerInput(Unit) {
                    detectVerticalDragGestures { change, _ ->
                        // Consume drag gestures so they don't propagate to the bottom sheet
                        change.consume()
                    }
                },
                startIndex = 0,
                texts = listOf("AM", "PM"),
                rowCount = 1
            )
        }
        //RepeatIntervalSelector(time = uiState.repeatIntervalTime, onTimeUpdate = mViewModel::updateRepeatIntervalTime)
        TextSwitch("All Day", uiState.isAllDay, onCheckedChange = mViewModel::toggleAllDay)
        TimeRangeSection(show = uiState.isAllDay)
        Text("Repeat", style = MaterialTheme.typography.titleMedium)
        WeekDaySelector(selectedDays = uiState.selectedDays, onToggleDay = mViewModel::toggleDaySelection)
        AlarmSoundSelector()
        TextSwitch("Vibrate", uiState.isVibrationEnabled, onCheckedChange = mViewModel::toggleVibration)
    }
}

@Composable
fun BottomSheetControlButtons(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit = {},
    onDoneClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            Icons.Default.Close,
            contentDescription = "Close",
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onCloseClick)
        )
        FilledTonalButton(
            onClick = onDoneClick
        ) {
            Text("Done")
        }
    }
}

@Composable
fun RepeatIntervalSelector(
    modifier: Modifier = Modifier,
    time: LocalTime,
    onTimeUpdate: (LocalTime) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text("Repeat After Every", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(12.dp))
        TimeInput(time = time, onTimeUpdate = onTimeUpdate)
    }
}

@Composable
fun TimeInput(
    time: LocalTime,
    modifier: Modifier = Modifier,
    onTimeUpdate : (LocalTime) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TimeInputItem(
            time.hour.toString(),
            onValueChange = {onTimeUpdate(time.withHour(it.toInt()))},
            modifier =  Modifier.weight(1f)
        )
        Text("hr")
        TimeInputItem(
            time.minute.toString(),
            onValueChange = {onTimeUpdate(time.withMinute(it.toInt()))},
            modifier = Modifier.weight(1f)
        )
        Text("min")
        TimeInputItem(
            time.second.toString(),
            onValueChange = {onTimeUpdate(time.withSecond(it.toInt()))},
            modifier = Modifier.weight(1f)
        )
        Text("sec")
    }
}

@Composable
fun TimeInputItem(
    value: String,
    onValueChange : (String) -> Unit = {},
    modifier: Modifier = Modifier,
    range: IntRange = 0..59
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { text->
            if (text.isEmpty()) {
                onValueChange("00")
            }
            else if (text.isDigitsOnly()) {
                val textInt = text.toInt()
                if (textInt in range) {
                    onValueChange(text)
                }
            }
        },
        textStyle = TextStyle.Default.copy(textAlign = TextAlign.Center, fontSize = 16.sp),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
    )
}

@Composable
fun TimeRangeSection(
    show : Boolean = true,
    modifier: Modifier = Modifier
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AnimatedVisibility(
            visible = !show,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                TimePickerCard(is24HourFormat = false, modifier = Modifier.weight(1f))
                Icon(Icons.AutoMirrored.Default.ArrowForward, contentDescription = "Arrow Forward")
                TimePickerCard(is24HourFormat = false, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun AlarmSoundSelector(
    modifier: Modifier = Modifier,
    selectedSound: String = "Default",
    onSoundSelect: () -> Unit = {}
) {
    Column(modifier) {
        Text("Alarm Sound", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(selectedSound, style = MaterialTheme.typography.labelMedium, modifier = Modifier.clickable(onClick = onSoundSelect))
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
    }
}

@Composable
fun WeekDaySelector(
    modifier: Modifier = Modifier,
    selectedDays: List<String>,
    onToggleDay: (String) -> Unit = {},
) {

    val days = listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa")
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(count = days.size) { index ->
            val day = days[index]
            val isSelected = selectedDays.contains(day)

            OutlinedButton(
                onClick = {
                    onToggleDay(day)
                },
                modifier = Modifier.size(40.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                )
            ) {
                Text(day, fontSize = 14.sp, color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddAlarmBottomSheetPreview() {
    Surface {
        AddEditAlarmScreen()
    }
}
