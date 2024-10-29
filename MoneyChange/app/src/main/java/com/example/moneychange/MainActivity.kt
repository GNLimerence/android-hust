package com.example.moneychange

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sourceAmountEditText: EditText
    private lateinit var destinationAmountEditText: EditText
    private lateinit var sourceCurrencySpinner: Spinner
    private lateinit var destinationCurrencySpinner: Spinner

    private val exchangeRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.85,
        "JPY" to 110.0,
        "VND" to 24000.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sourceAmountEditText = findViewById(R.id.sourceAmountEditText)
        destinationAmountEditText = findViewById(R.id.destinationAmountEditText)
        sourceCurrencySpinner = findViewById(R.id.sourceCurrencySpinner)
        destinationCurrencySpinner = findViewById(R.id.destinationCurrencySpinner)

        val currencies = exchangeRates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sourceCurrencySpinner.adapter = adapter
        destinationCurrencySpinner.adapter = adapter
        
        setupListeners()
    }

    private fun setupListeners() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                convertCurrency()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        sourceAmountEditText.addTextChangedListener(textWatcher)
        sourceCurrencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        destinationCurrencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun convertCurrency() {
        val amount = sourceAmountEditText.text.toString().toDoubleOrNull() ?: return
        val sourceCurrency = sourceCurrencySpinner.selectedItem.toString()
        val destinationCurrency = destinationCurrencySpinner.selectedItem.toString()

        val sourceRate = exchangeRates[sourceCurrency] ?: 1.0
        val destinationRate = exchangeRates[destinationCurrency] ?: 1.0

        val result = amount * (destinationRate / sourceRate)
        destinationAmountEditText.setText(result.toString())
    }
}
