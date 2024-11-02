package com.mlsa_uet_nc.repeatalarm.presentation.ui.add_alarm

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mlsa_uet_nc.repeatalarm.presentation.ui.components.TimePickerCard

@Composable
fun AddAlarmScreen(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit = {},
    onDoneClick: () -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item { BottomSheetControlButtons(onCloseClick = onCloseClick, onDoneClick = onDoneClick) }
        item { RepeatIntervalSelector() }
        item { TimeRangeSection() }
        item { AlarmSoundSelector() }
        item { VibrationToggle() }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepeatIntervalSelector(
    modifier: Modifier = Modifier,
    initialHour: Int = 0,
    initialMinute: Int = 20,
    is24Hour: Boolean = true
) {
    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = is24Hour,
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Text("Repeat After Every", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(12.dp))
        TimeInput(timePickerState, modifier.align(Alignment.CenterHorizontally))
    }
    HorizontalDivider()
}

@Composable
fun TimeRangeSection(
    modifier: Modifier = Modifier
) {
    var isAllDay by remember { mutableStateOf(true) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("All day", style = MaterialTheme.typography.titleMedium)
            Switch(
                checked = isAllDay,
                onCheckedChange = { isAllDay = it }
            )
        }

        AnimatedVisibility(
            visible = !isAllDay,
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

        Text("Repeat", style = MaterialTheme.typography.titleMedium)
        WeekDaySelector()
    }
    Spacer(Modifier.height(8.dp))
    HorizontalDivider()
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
fun VibrationToggle(
    modifier: Modifier = Modifier,
    isVibrationEnabled: Boolean = true,
    onVibrationToggle: (Boolean) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Vibrate", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = isVibrationEnabled,
            onCheckedChange = onVibrationToggle
        )
    }
}



@Composable
fun WeekDaySelector(
    modifier: Modifier = Modifier,
    onDaySelected: (String) -> Unit = {}
) {
    val days = listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa")
    val selectedDays = remember { mutableStateListOf<String>() }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(count = days.size) { index ->
            val day = days[index]
            val isSelected = selectedDays.contains(day)

            OutlinedButton(
                onClick = {
                    if (isSelected) {
                        selectedDays.remove(day)
                    } else {
                        selectedDays.add(day)
                    }
                    onDaySelected(day)
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
        AddAlarmScreen()
    }
}
