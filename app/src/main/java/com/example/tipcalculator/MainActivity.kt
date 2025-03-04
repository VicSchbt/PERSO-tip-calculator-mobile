package com.example.tipcalculator

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TipCalculatorScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun TipCalculatorScreen(modifier: Modifier) {
    var billAmount by remember { mutableStateOf("") }
    var selectedTip by remember { mutableFloatStateOf(15f) }
    var numberOfPeople by remember { mutableStateOf("") }

    val bill = billAmount.toFloatOrNull() ?: 0f
    val people = numberOfPeople.toIntOrNull() ?: 1
    val tip = bill * (selectedTip / 100)
    val total = (bill + tip) / people

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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
            selectedTip = selectedTip,
            onTipSelected = { value -> selectedTip = value }
        )

        UserTextInput(
            inputValue = billAmount,
            onInputValueChange = { value -> billAmount = value },
            label = "Number of People",
            placeholder = "Enter a number",
            iconResource = painterResource(R.drawable.icon_person),
            iconDescription = stringResource(id = R.string.icon_person)

        )

        // Display Tip AMount and Total
        Text(text = "Tip Amount: €${String.format("%.2f", tip)}")
        Text(text = "Total Amount: €${String.format("%.2f", total)}")
        // Reset Button
        Button(
            onClick = {
                billAmount = ""
                selectedTip = 15f
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Reset")
        }
    }
}

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
        Text(
            text = label,
            color = colorResource(id = R.color.grayish_cyan)
        )
        // Bill Amount Input
        OutlinedTextField(
            value = inputValue,
            onValueChange = { onInputValueChange(it) },
            label = {
                Text(
                    text = placeholder,
                    textAlign = TextAlign.End,
                    color = colorResource(id = R.color.very_dark_cyan),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.very_light_grayish_cyan)),
            leadingIcon = {
                Icon(
                    painter = iconResource,
                    contentDescription = iconDescription
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Red,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
            )
        )
    }
}

@Composable
fun TipSelector(
    selectedTip: Float,
    onTipSelected: (Float) -> Unit
) {
    val tipsOptions = listOf(5f, 10f, 15f, 25f, 50f, 75f)

    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        tipsOptions.forEach { tip ->
            item {
                Button(
                    onClick = { onTipSelected(tip) },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = if (selectedTip == tip) Color.Blue else Color.LightGray,
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .semantics {
                            role = Role.RadioButton
                            stateDescription =
                                if (selectedTip == tip) "Selected" else "Not Selected"
                        }
                ) {
                    Text("$tip %")
                }
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun TipCalculatorScreenPreview() {
    TipCalculatorTheme {
        TipCalculatorScreen(modifier = Modifier)
    }
}