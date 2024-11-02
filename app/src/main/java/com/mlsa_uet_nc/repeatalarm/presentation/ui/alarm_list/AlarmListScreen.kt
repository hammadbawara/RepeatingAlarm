package com.mlsa_uet_nc.repeatalarm.presentation.ui.alarm_list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.mlsa_uet_nc.repeatalarm.presentation.ui.SampleData.alarmListSampleData
import com.mlsa_uet_nc.repeatalarm.presentation.ui.add_alarm.AddAlarmScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmListScreen() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val modalSheetState = rememberModalBottomSheetState()
    var showAddAlarmScreen by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("Alarms List")},
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {

            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Alarm")
            }
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding->
        AlarmList(
            modifier = Modifier.padding(innerPadding),
            alarmList = alarmListSampleData
        )
    }

    if (showAddAlarmScreen) {
        ModalBottomSheet(
            onDismissRequest = { showAddAlarmScreen = false}
        ) {
            AddAlarmScreen()
        }
    }

}

@Composable
fun AlarmList(
    alarmList: List<AlarmListItemUiState>,
    modifier: Modifier = Modifier
) {
    LazyColumn (
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp, 4.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ){
        items(alarmList.size) { index ->
            AlarmListItem(
                alarmItem = alarmList[index]
            )
        }
    }
}

@PreviewLightDark
@Preview
@Composable
fun PreviewAlarmListScreen() {
    Surface {
        AlarmList(
            alarmList = alarmListSampleData
        )
    }
}