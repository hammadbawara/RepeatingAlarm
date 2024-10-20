package com.mlsa_uet_nc.repeatalarm.presentation.ui.components

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun TimePickerCard(
    modifier: Modifier = Modifier,
    initialTime: Calendar = Calendar.getInstance(),
    is24HourFormat: Boolean = true
) {
    var selectedTime by remember { mutableStateOf(initialTime) }
    val context = LocalContext.current

    Card(
        modifier = modifier
            .clickable {
                showTimePickerDialog(
                    context = context,
                    is24HourFormat = is24HourFormat,
                    initialTime = selectedTime,
                    onTimeSelected = { hourOfDay, minute ->
                        selectedTime = Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY, hourOfDay)
                            set(Calendar.MINUTE, minute)
                        }
                    }
                )
            },
        shape = RoundedCornerShape(8.dp),
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp),
        ) {
            val timeText = getFormattedTime(selectedTime, is24HourFormat)
            Text(
                text = timeText,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

fun showTimePickerDialog(
    context: Context,
    is24HourFormat: Boolean,
    initialTime: Calendar,
    onTimeSelected: (hourOfDay: Int, minute: Int) -> Unit
) {
    TimePickerDialog(
        context,
        { _, hourOfDay, minute -> onTimeSelected(hourOfDay, minute) },
        initialTime.get(Calendar.HOUR_OF_DAY),
        initialTime.get(Calendar.MINUTE),
        is24HourFormat
    ).show()
}

fun getFormattedTime(calendar: Calendar, is24HourFormat: Boolean): String {
    val format = if (is24HourFormat) "HH:mm" else "hh:mm a"
    return SimpleDateFormat(format, Locale.getDefault()).format(calendar.time)
}