package com.example.tipcalculator.tip_calculator_screen.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.R

@Composable
fun Label(text: String) {
    Text(
        text = text,
        color = colorResource(id = R.color.grayish_cyan),
        fontSize = 16.sp
    )
}