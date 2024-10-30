package com.example.bai3


import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var addressHelper: AddressHelper
    private lateinit var etMSSV: EditText
    private lateinit var etName: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var btnSelectDate: Button
    private lateinit var calendarView: CalendarView
    private lateinit var spProvince: Spinner
    private lateinit var spDistrict: Spinner
    private lateinit var spWard: Spinner
    private lateinit var cbSports: CheckBox
    private lateinit var cbMovies: CheckBox
    private lateinit var cbMusic: CheckBox
    private lateinit var cbAgreement: CheckBox
    private lateinit var btnSubmit: Button
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addressHelper = AddressHelper(resources)
        etMSSV = findViewById(R.id.etMSSV)
        etName = findViewById(R.id.etName)
        rgGender = findViewById(R.id.rgGender)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        btnSelectDate = findViewById(R.id.btnSelectDate)
        calendarView = findViewById(R.id.calendarView)
        spProvince = findViewById(R.id.spProvince)
        spDistrict = findViewById(R.id.spDistrict)
        spWard = findViewById(R.id.spWard)
        cbSports = findViewById(R.id.cbSports)
        cbMovies = findViewById(R.id.cbMovies)
        cbMusic = findViewById(R.id.cbMusic)
        cbAgreement = findViewById(R.id.cbAgreement)
        btnSubmit = findViewById(R.id.btnSubmit)

        btnSelectDate.setOnClickListener {
            calendarView.visibility = if (calendarView.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
            btnSelectDate.text = "Ngày sinh: $selectedDate"
            calendarView.visibility = View.GONE
        }


        setupAddressSpinners()


        btnSubmit.setOnClickListener { validateForm() }
    }

    private fun setupAddressSpinners() {
        val provinces = addressHelper.getProvinces()
        spProvince.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provinces)

        spProvince.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedProvince = provinces[position]
                val districts = addressHelper.getDistricts(selectedProvince)
                spDistrict.adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, districts)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spDistrict.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedDistrict = spDistrict.selectedItem.toString()
                val wards = addressHelper.getWards(spProvince.selectedItem.toString(), selectedDistrict)
                spWard.adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, wards)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun validateForm() {
        if (etMSSV.text.isEmpty() || etName.text.isEmpty() || rgGender.checkedRadioButtonId == -1 ||
            etEmail.text.isEmpty() || etPhone.text.isEmpty() || selectedDate.isEmpty() ||
            spProvince.selectedItem == null || spDistrict.selectedItem == null || spWard.selectedItem == null ||
            !cbAgreement.isChecked
        ) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Thông tin đã được gửi!", Toast.LENGTH_SHORT).show()
        }
    }
}
