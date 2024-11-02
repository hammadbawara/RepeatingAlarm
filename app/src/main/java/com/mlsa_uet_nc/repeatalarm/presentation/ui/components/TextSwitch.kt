package com.mlsa_uet_nc.repeatalarm.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun TextSwitch(
    text: String,
    isChecked : Boolean = true,
    onCheckedChange : (Boolean) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row (
        modifier = modifier
            .clickable { onCheckedChange(!isChecked) },
        verticalAlignment = Alignment.CenterVertically
    ){
        Text (
            text, style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .weight(1f),
        )
        Switch(checked = isChecked, onCheckedChange = onCheckedChange)
    }
}