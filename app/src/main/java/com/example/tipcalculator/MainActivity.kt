package com.example.tipcalculator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.SpaceMono
import com.example.tipcalculator.ui.theme.TipCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalculatorTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 0.dp, vertical = 32.dp),
                    containerColor = Color.White
                ) { innerPadding ->
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
    var customTip by remember { mutableStateOf("") }

    val bill = billAmount.toFloatOrNull() ?: 0f
    val people = numberOfPeople.toIntOrNull() ?: 1
    val tip = bill * (selectedTip / 100)
    val tipPerPerson = tip / people
    val totalPerPerson = (bill + tip) / people
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
                selectedTip = selectedTip,
                onTipSelected = { value -> selectedTip = value },
                customTip = customTip,
                onCustomTipChange = {
                    selectedTip = customTip.toFloatOrNull() ?: 0f
                }
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
                selectedTip = 15f
                numberOfPeople = ""
            }
        )
    }
}

@Composable
fun Label(text: String) {
    Text(
        text = text,
        color = colorResource(id = R.color.grayish_cyan),
        fontSize = 16.sp
    )
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

@Preview(showBackground = true)
@Composable
fun TipCalculatorScreenPreview() {
    TipCalculatorTheme {
        TipCalculatorScreen(modifier = Modifier)
    }
}