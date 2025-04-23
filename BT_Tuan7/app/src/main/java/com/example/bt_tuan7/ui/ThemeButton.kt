package com.example.bt_tuan7.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape


@Composable
fun ThemeButton(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(8.dp)

    Box(
        modifier = Modifier
            .size(width = 60.dp, height = 40.dp)
            .background(color, shape)
            .then(
                if (isSelected) Modifier.border(3.dp, Color.Black, shape)
                else Modifier.border(1.dp, Color.Gray, shape)
            )
            .clickable { onClick() }
    )
}
