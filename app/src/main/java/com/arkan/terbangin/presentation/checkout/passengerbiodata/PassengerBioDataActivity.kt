package com.arkan.terbangin.presentation.checkout.passengerbiodata

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.arkan.terbangin.R
import com.arkan.terbangin.databinding.ActivityPassengerBiodataBinding
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.textfield.TextInputEditText

class PassengerBioDataActivity : AppCompatActivity() {
    private val binding: ActivityPassengerBiodataBinding by lazy {
        ActivityPassengerBiodataBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupLabelTitle()
        setSwitchBioData()
        setClickListener()
    }

    private fun setClickListener() {
        findViewById<View>(R.id.til_date_birth_biodata).setOnClickListener {
            showDateBirthPickerDialog()
        }
        findViewById<View>(R.id.til_date_valid_until).setOnClickListener {
            showDateValidUntilPickerDialog()
        }
    }

    private fun showDateValidUntilPickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateValidUntilPickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Set the selected date to the input
                val formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                findViewById<TextInputEditText>(R.id.ti_et_date_valid_until).setText(formattedDate)
            },
            year, month, day
        )

        dateValidUntilPickerDialog.show()
    }

    private fun showDateBirthPickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateBirthPickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Set the selected date to the input
                val formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                findViewById<TextInputEditText>(R.id.ti_et_date_birth_biodata).setText(formattedDate)
            },
            year, month, day
        )

        dateBirthPickerDialog.show()
    }

    private fun setSwitchBioData() {
        val switch = findViewById<MaterialSwitch>(R.id.switch_ask_name_family)
        val textNameFamily = findViewById<TextView>(R.id.tv_name_family_biodata)
        val inputEditTextNameFamily = findViewById<TextInputEditText>(R.id.til_name_family_biodata)

        switch.setOnCheckedChangeListener { _, isChecked ->
            textNameFamily.isVisible = isChecked
            inputEditTextNameFamily.isVisible = isChecked
        }
    }

    private fun setupLabelTitle() {
        val items = listOf("Mr.", "Mrs.", "Ms.", "Dr.")
        val autoComplete: AutoCompleteTextView = findViewById(R.id.autoComplete_title)
        val adapter = ArrayAdapter(this, R.layout.item_passenger_biodata, items)

        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
            Toast.makeText(this, "Item : $itemSelected", Toast.LENGTH_SHORT).show()
        }
    }
}