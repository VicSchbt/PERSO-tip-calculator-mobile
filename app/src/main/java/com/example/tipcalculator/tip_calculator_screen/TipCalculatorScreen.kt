package com.example.tipcalculator.tip_calculator_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalculator.R
import com.example.tipcalculator.tip_calculator_screen.components.ResultDisplay
import com.example.tipcalculator.tip_calculator_screen.components.TipSelector
import com.example.tipcalculator.tip_calculator_screen.components.UserTextInput
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import com.example.tipcalculator.utils.calculateTip

@SuppressLint("DefaultLocale")
@Composable
fun TipCalculatorScreen(modifier: Modifier) {
    var billAmount by remember { mutableStateOf("") }
    var selectedTip by remember { mutableFloatStateOf(15f) }
    var numberOfPeople by remember { mutableStateOf("") }

    val bill = billAmount.toFloatOrNull() ?: 0f
    val people = numberOfPeople.toIntOrNull()?.takeIf { it > 0 } ?: 1
    val (tipPerPerson, totalPerPerson) = calculateTip(bill, selectedTip, people)

    val tipsOptions = listOf(5f, 10f, 15f, 25f, 50f)

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            UserTextInput(
                inputValue = billAmount,
                onInputValueChange = { value -> billAmount = value },
                label = "Bill",
                placeholder = "Enter your amount",
                iconResource = painterResource(R.drawable.icon_dollar),
                iconDescription = stringResource(id = R.string.icon_dollar)

            )

            TipSelector(
                tipsOptions = tipsOptions,
                onTipSelected = { value -> selectedTip = value },
            )

            UserTextInput(
                inputValue = numberOfPeople,
                onInputValueChange = { value -> numberOfPeople = value },
                label = "Number of People",
                placeholder = "Enter a number",
                iconResource = painterResource(R.drawable.icon_person),
                iconDescription = stringResource(id = R.string.icon_person)

            )
        }
        ResultDisplay(
            totalPerPerson = totalPerPerson,
            tipPerPerson = tipPerPerson,
            onResetClicked = {
                billAmount = ""
                selectedTip = 0f
                numberOfPeople = ""
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TipCalculatorScreenPreview() {
    TipCalculatorTheme {
        TipCalculatorScreen(modifier = Modifier)
    }
}