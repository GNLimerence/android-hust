package com.example.bai1


import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etNumber = findViewById<EditText>(R.id.etNumber)
        val rbEven = findViewById<RadioButton>(R.id.rbEven)
        val rbOdd = findViewById<RadioButton>(R.id.rbOdd)
        val rbSquare = findViewById<RadioButton>(R.id.rbSquare)
        val btnShow = findViewById<Button>(R.id.btnShow)
        val lvResults = findViewById<ListView>(R.id.lvResults)
        val tvError = findViewById<TextView>(R.id.tvError)

        btnShow.setOnClickListener {
            val input = etNumber.text.toString()
            tvError.text = ""

            if (input.isEmpty() || input.toIntOrNull() == null || input.toInt() <= 0) {
                tvError.text = "Please enter a valid positive integer."
                return@setOnClickListener
            }

            val n = input.toInt()
            val results = mutableListOf<Int>()

            when {
                rbEven.isChecked -> {
                    results.addAll(generateEvenNumbers(n))
                }
                rbOdd.isChecked -> {
                    results.addAll(generateOddNumbers(n))
                }
                rbSquare.isChecked -> {
                    results.addAll(generateSquareNumbers(n))
                }
                else -> {
                    tvError.text = "Please select a number type."
                    return@setOnClickListener
                }
            }

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, results)
            lvResults.adapter = adapter
        }
    }

    private fun generateEvenNumbers(n: Int): List<Int> {
        return (0..n).filter { it % 2 == 0 }
    }

    private fun generateOddNumbers(n: Int): List<Int> {
        return (1..n).filter { it % 2 != 0 }
    }

    private fun generateSquareNumbers(n: Int): List<Int> {
        val results = mutableListOf<Int>()
        var i = 0
        while (i * i <= n) {
            results.add(i * i)
            i++
        }
        return results
    }
}
