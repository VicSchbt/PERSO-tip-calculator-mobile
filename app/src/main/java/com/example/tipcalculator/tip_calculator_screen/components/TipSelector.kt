package com.example.tipcalculator.tip_calculator_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
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
    onTipSelected: (Float) -> Unit
) {
    var selectedTip by remember { mutableStateOf<Float?>(null) }
    var customTip by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Label("Select Tip %")

    for (i in 0..tipsOptions.size step 2) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (i < tipsOptions.size) {
                val tip = tipsOptions[i]
                TipButton(
                    tip,
                    onClick = {
                        selectedTip = tip
                        customTip = ""
                        focusManager.clearFocus()
                        onTipSelected(tip)
                    },
                    isSelected = selectedTip == tip,
                    modifier = Modifier.weight(1f)
                )
            }
            if (i + 1 < tipsOptions.size) {
                val tip = tipsOptions[i + 1]
                TipButton(
                    tip,
                    onClick = {
                        selectedTip = tip
                        customTip = ""
                        focusManager.clearFocus()
                        onTipSelected(tip)
                    },
                    isSelected = selectedTip == tip,
                    modifier = Modifier.weight(1f)
                )

            }
            if (tipsOptions.size in intArrayOf(i, i + 1)) {
                TipTextField(
                    customTip,
                    onValueChange = { newText: String ->
                    customTip = newText
                        selectedTip = null
                    val tipValue = newText.toFloatOrNull()
                    if (tipValue != null) {
                        onTipSelected(tipValue)
                    }
                },
                    modifier = Modifier.weight(1f)
                )
            }

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
