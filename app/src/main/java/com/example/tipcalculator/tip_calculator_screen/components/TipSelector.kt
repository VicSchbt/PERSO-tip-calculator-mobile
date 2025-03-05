package com.example.tipcalculator.tip_calculator_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.R
import com.example.tipcalculator.ui.theme.SpaceMono


@Composable
fun TipSelector(
    selectedTip: Float,
    onTipSelected: (Float) -> Unit,
    customTip: String,
    onCustomTipChange: (String) -> Unit
) {
    val tipsOptions = listOf(5f, 10f, 15f, 25f, 50f)

    Label("Select Tip %")

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        tipsOptions.forEach { tip ->
            item {
                Button(
                    onClick = {
                        onCustomTipChange("")
                        onTipSelected(tip)
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = if (selectedTip == tip) colorResource(R.color.very_dark_cyan) else colorResource(
                            R.color.white
                        ),
                        containerColor = if (selectedTip == tip) colorResource(R.color.strong_cyan) else colorResource(
                            R.color.very_dark_cyan
                        )
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .semantics {
                            role = Role.RadioButton
                            stateDescription =
                                if (selectedTip == tip) "Selected" else "Not Selected"
                        }
                ) {
                    Text("${tip.toInt()} %", fontFamily = SpaceMono, fontSize = 20.sp)
                }
            }
        }
        item {
            OutlinedTextField(
                value = customTip,
                onValueChange = { onCustomTipChange(it) },
                label = {
                    Text(
                        text = "Custom",
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth(),
                        fontFamily = SpaceMono,
                        color = colorResource(id = R.color.dark_grayish_cyan)
                    )
                },
                shape = RoundedCornerShape(8.dp),
                textStyle = TextStyle(textAlign = TextAlign.End),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(R.color.strong_cyan),
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    unfocusedContainerColor = colorResource(id = R.color.very_light_grayish_cyan),
                    focusedContainerColor = colorResource(id = R.color.very_light_grayish_cyan)
                ),

                )
        }

    }
}
