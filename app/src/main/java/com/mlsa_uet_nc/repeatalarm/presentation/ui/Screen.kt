package com.mlsa_uet_nc.repeatalarm.presentation.ui

sealed class Screen(val route: String) {
    object AddAlarm : Screen("add_alarm")
}