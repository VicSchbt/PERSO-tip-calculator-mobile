package com.example.tipcalculator.utils

fun calculateTip(bill: Float, tipPercentage: Float, people: Int): Pair<Float, Float> {
    val tip = bill * (tipPercentage / 100)
    val totalPerPerson = (bill + tip) / people
    return Pair(tip, totalPerPerson)
}
