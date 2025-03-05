package com.example.tipcalculator.tip_calculator_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tipcalculator.R
import com.example.tipcalculator.ui.theme.SpaceMono

@Composable
fun UserTextInput(
    inputValue: String,
    onInputValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    iconResource: Painter,
    iconDescription: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Label(label)

        OutlinedTextField(
            value = inputValue,
            onValueChange = { onInputValueChange(it) },
            label = {
                Text(
                    text = placeholder,
                    textAlign = TextAlign.End,
                    color = colorResource(id = R.color.very_dark_cyan),
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = SpaceMono
                )
            },
            textStyle = TextStyle(textAlign = TextAlign.End),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth(),
            leadingIcon = {
                Icon(
                    painter = iconResource,
                    contentDescription = iconDescription
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.strong_cyan),
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                unfocusedContainerColor = colorResource(id = R.color.very_light_grayish_cyan),
                focusedContainerColor = colorResource(id = R.color.very_light_grayish_cyan)
            ),
            shape = RoundedCornerShape(8.dp)
        )
    }
}