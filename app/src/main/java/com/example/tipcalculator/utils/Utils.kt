package com.example.tipcalculator.utils

fun calculateTip(bill: Float, tipPercentage: Float, people: Int): Pair<Float, Float> {
    val tip = bill * (tipPercentage / 100)
    val tipPerPerson = tip / people
    val totalPerPerson = (bill + tip) / people
    return Pair(tipPerPerson, totalPerPerson)
}
