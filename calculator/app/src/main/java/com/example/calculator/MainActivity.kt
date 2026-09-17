package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var display: TextView
    private var current = "0"
    private var previous: Double? = null
    private var operator: String? = null
    private var justEvaluated = false
    private var isOperatorPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.tvDisplay)

        val numberIds = mapOf(
            R.id.btn0 to "0", R.id.btn1 to "1", R.id.btn2 to "2",
            R.id.btn3 to "3", R.id.btn4 to "4", R.id.btn5 to "5",
            R.id.btn6 to "6", R.id.btn7 to "7", R.id.btn8 to "8", R.id.btn9 to "9"
        )
        for ((id, value) in numberIds) {
            findViewById<Button>(id).setOnClickListener { inputNumber(value) }
        }

        findViewById<Button>(R.id.btnDecimal).setOnClickListener { inputDecimal() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btndelete).setOnClickListener { deleteLast() }
        findViewById<Button>(R.id.btnPercent).setOnClickListener { percent() }
        findViewById<Button>(R.id.btnEquals).setOnClickListener { equals() }

        findViewById<Button>(R.id.btnAdd).setOnClickListener { chooseOperator("+") }
        findViewById<Button>(R.id.btnSubtract).setOnClickListener { chooseOperator("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { chooseOperator("*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { chooseOperator("/") }
    }

    private fun updateDisplay() {
        display.text = current
    }

    private fun inputNumber(n: String) {
        if (justEvaluated || isOperatorPressed) {
            current = n
            justEvaluated = false
            isOperatorPressed = false
        } else {
            current = if (current == "0") n else current + n
        }
        updateDisplay()
    }

    private fun inputDecimal() {
        if (justEvaluated || isOperatorPressed) {
            current = "0."
            justEvaluated = false
            isOperatorPressed = false
        } else if (!current.contains(".")) {
            current += "."
        }
        updateDisplay()
    }

    private fun deleteLast() {
        if (justEvaluated) {
            clearAll()
            return
        }

        current = if (current.length > 1) {
            val dropped = current.dropLast(1)
            if (dropped == "-") "0" else dropped
        } else {
            "0"
        }
        updateDisplay()
    }

    private fun clearAll() {
        current = "0"
        previous = null
        operator = null
        justEvaluated = false
        updateDisplay()
    }

    private fun percent() {
        val valDouble = current.toDoubleOrNull() ?: return
        current = (valDouble / 100.0).toCleanString()
        updateDisplay()
    }

    private fun compute(a: Double, b: Double, op: String): Double = when (op) {
        "+" -> a + b
        "-" -> a - b
        "*" -> a * b
        "/" -> if (b == 0.0) error("cannot divide by zero") else a / b
        else -> b
    }

    private fun chooseOperator(op: String) {
        if (operator != null && !isOperatorPressed && !justEvaluated) {
            val result = compute(previous ?: 0.0, current.toDouble(), operator!!)
            current = result.toCleanString()
            previous = result
            updateDisplay()
        } else {
            previous = current.toDouble()
        }
        operator = op
        isOperatorPressed = true
        justEvaluated = false
    }

    private fun equals() {
        val op = operator ?: return
        val prev = previous ?: return
        val result = compute(prev, current.toDouble(), op)
        current = result.toCleanString()
        previous = null
        operator = null
        justEvaluated = true
        isOperatorPressed = false
        updateDisplay()
    }

    private fun Double.toCleanString(): String {
        return if (this == this.toLong().toDouble()) this.toLong().toString()
        else this.toString()
    }
}
