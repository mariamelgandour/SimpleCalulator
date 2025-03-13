package com.example.calulatorapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Calculator : AppCompatActivity() {
    private lateinit var resultTv: TextView
    private var LHS: String = ""
    private var tempOperation: String = ""
    private var isDot: Boolean = false
    private var isEqual: Boolean = false
    private lateinit var buttonDot: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calulator)
        resultTv = findViewById(R.id.result_tv)
        buttonDot = findViewById(R.id.button_dot)
    }

    fun onDigitClick(view: View) {
        val button = view as Button
        val digit = button.text.toString()

        if (isEqual) {
            resultTv.text = digit
            isEqual = false
        } else {
            if (digit == ".") {
                if (!isDot) {
                    isDot = true
                    buttonDot.isEnabled = false
                    resultTv.append(digit)
                }
            } else {
                resultTv.append(digit)
            }
        }
    }

    fun onOperationClick(view: View) {
        val clickOperation = view as Button
        val operation: String = clickOperation.text.toString()

        if (tempOperation.isEmpty()) {
            LHS = resultTv.text.toString()
            tempOperation = operation
            resultTv.text = ""
        } else {
            val RHS: String = resultTv.text.toString()
            if (RHS.isNotEmpty()) {
                LHS = calculate(LHS, tempOperation, RHS)
                tempOperation = operation
                resultTv.text = ""
            }
        }
        isDot = false
        buttonDot.isEnabled = true
    }

    fun onEqualClick(view: View) {
        val RHS: String = resultTv.text.toString()
        if (RHS.isNotEmpty() && tempOperation.isNotEmpty()) {
            LHS = calculate(LHS, tempOperation, RHS)
            resultTv.text = LHS
            tempOperation = ""
            LHS = ""
            isEqual = true
            isDot = false
            buttonDot.isEnabled = true
        }
    }

    private fun calculate(lhs: String, tempOperation: String, rhs: String): String {
        val number1 = lhs.toDouble()
        val number2 = rhs.toDouble()

        return when (tempOperation) {
            "+" -> (number1 + number2).toString()
            "-" -> (number1 - number2).toString()
            "*" -> (number1 * number2).toString()
            "/" -> if (number2 != 0.0) (number1 / number2).toString() else "Error"
            else -> "Error"
        }
    }
}