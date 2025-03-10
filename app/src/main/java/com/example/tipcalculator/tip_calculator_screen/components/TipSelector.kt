package com.example.tipcalculator.tip_calculator_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    tipsOptions: List<Float>,
    selectedTip: Float,
    customTip: String,
    onButtonClick: (Float) -> Unit,
    onCustomChange: (String) -> Unit
) {

    Label("Select Tip %")

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        tipsOptions.chunked(2).forEach { rowTips ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                rowTips.forEach { tip ->
                    TipButton(
                        tip,
                        onClick = { onButtonClick(tip) },
                        isSelected = selectedTip == tip,
                        modifier = Modifier.weight(1f)
                    )
                }
                if (rowTips.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            TipTextField(
                customTip,
                onValueChange = { newText: String ->
                    onCustomChange(newText)
                },
                modifier = Modifier.weight(1f)
            )
        }

    }
}


@Composable
fun TipButton(tip: Float, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            contentColor = if (isSelected) colorResource(R.color.very_dark_cyan) else colorResource(
                R.color.white
            ),
            containerColor = if (isSelected) colorResource(R.color.strong_cyan) else colorResource(
                R.color.very_dark_cyan
            )
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.semantics {
            role = Role.RadioButton
            stateDescription =
                if (isSelected) "Selected" else "Not Selected"
        }
    ) {
        Text("${tip.toInt()} %", fontFamily = SpaceMono, fontSize = 20.sp)
    }
}

@Composable
fun TipTextField(customTip: String, onValueChange: (String) -> Unit, modifier: Modifier) {
    OutlinedTextField(
        value = customTip,
        onValueChange = { newText -> onValueChange(newText) },
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
        modifier = modifier
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
