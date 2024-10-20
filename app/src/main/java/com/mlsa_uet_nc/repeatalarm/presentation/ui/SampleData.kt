package com.mlsa_uet_nc.repeatalarm.presentation.ui

import com.mlsa_uet_nc.repeatalarm.presentation.ui.alarm_list.AlarmListItemUiState

object SampleData {
    val alarmListSampleData = listOf(
        AlarmListItemUiState(
            timeRange = "8:00 AM - 9:00 AM",
            interval = "Every 1 hour",
            days = "Mon, Tue, Wed, Thu, Fri",
            isActive = true
        ),
        AlarmListItemUiState(
            timeRange = "11:00 AM - 12:00 AM",
            interval = "Every 30 min",
            days = "Thu, Fri",
            isActive = false
        ),
        AlarmListItemUiState(
            timeRange = "2:00 PM - 3:00 PM",
            interval = "Every 2 hour",
            days = "Mon, Tue, Wed, Thu, Fri",
            isActive = true
        ),
        AlarmListItemUiState(
            timeRange = "5:00 PM - 6:00 PM",
            interval = "Every 1 hour",
            days = "Mon, Tue, Wed, Thu, Fri",
            isActive = true
        ),
        AlarmListItemUiState(
            timeRange = "8:00 PM - 9:00 PM",
            interval = "Every 1 hour",
            days = "Mon, Tue, Wed, Thu, Fri",
            isActive = true
        ),
        AlarmListItemUiState(
            timeRange = "11:00 PM - 12:00 AM",
            interval = "Every 30 min",
            days = "Thu, Fri",
            isActive = false
        ),
    )
}

