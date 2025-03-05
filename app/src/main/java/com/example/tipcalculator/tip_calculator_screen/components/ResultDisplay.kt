package com.example.tipcalculator.tip_calculator_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.R
import com.example.tipcalculator.ui.theme.SpaceMono

@Composable
fun ResultDisplay(
    totalPerPerson: Float,
    tipPerPerson: Float,
    onResetClicked: () -> Unit
) {
    val labels = listOf("Tip Amount", "Total")
    val values = listOf(tipPerPerson, totalPerPerson)
    val lines = labels.zip(values)

    Column(
        modifier = Modifier
            .background(
                color = colorResource(R.color.very_dark_cyan),
                shape = RoundedCornerShape(20.dp)
            )
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (line in lines) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = line.first,
                        fontSize = 16.sp,
                        lineHeight = 18.sp,
                        color = colorResource(R.color.white)
                    )
                    Text(
                        text = "/ person",
                        fontSize = 14.sp,
                        lineHeight = 16.sp,
                        color = colorResource(R.color.grayish_cyan)
                    )
                }
                Text(
                    text = "$${Math.round(line.second * 100) / 100f}",
                    fontSize = 32.sp,
                    color = colorResource(R.color.strong_cyan)
                )
            }
        }
        Button(
            onClick = onResetClicked,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                contentColor = colorResource(R.color.very_dark_cyan),
                containerColor = colorResource(R.color.strong_cyan)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "RESET", fontFamily = SpaceMono, fontSize = 20.sp)
        }
    }
}