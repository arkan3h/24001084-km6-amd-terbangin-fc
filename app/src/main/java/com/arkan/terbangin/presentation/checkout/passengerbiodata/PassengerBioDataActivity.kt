package com.arkan.terbangin.presentation.checkout.passengerbiodata

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.arkan.terbangin.R
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.databinding.ActivityPassengerBiodataBinding
import com.arkan.terbangin.databinding.ItemPassengerBiodataBinding

class PassengerBioDataActivity : AppCompatActivity() {
    private val binding: ActivityPassengerBiodataBinding by lazy {
        ActivityPassengerBiodataBinding.inflate(layoutInflater)
    }

    private val bindingForm: ItemPassengerBiodataBinding by lazy {
        ItemPassengerBiodataBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSwitchBioData()
        setupLabelTitle()
        setClickListener()
    }

    private fun setClickListener() {
        bindingForm.tilDateBirthBiodata.setOnClickListener {
            showDateBirthPickerDialog()
        }
        bindingForm.tilDateValidUntil.setOnClickListener {
            showDateValidUntilPickerDialog()
        }
    }

    private fun showDateValidUntilPickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateValidUntilPickerDialog =
            DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Set the selected date to the input
                    val formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                    bindingForm.tiEtDateValidUntil.setText(formattedDate)
                },
                year,
                month,
                day,
            )

        dateValidUntilPickerDialog.show()
    }

    private fun showDateBirthPickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateBirthPickerDialog =
            DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Set the selected date to the input
                    val formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                    bindingForm.tiEtDateBirthBiodata.setText(formattedDate)
                },
                year,
                month,
                day,
            )

        dateBirthPickerDialog.show()
    }

    private fun setSwitchBioData() {
        val switchAskNameFamily = bindingForm.switchAskNameFamily
        val tvNameFamilyBiodata = bindingForm.tvNameFamilyBiodata
        val tilNameFamilyBiodata = bindingForm.tilNameFamilyBiodata
        val tiEtNameFamilyBiodata = bindingForm.tiEtNameFamilyBiodata

        switchAskNameFamily.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                tvNameFamilyBiodata.isEnabled = true
                tilNameFamilyBiodata.isEnabled = true
                tvNameFamilyBiodata.setTextColor(ContextCompat.getColor(this, R.color.md_theme_primary))
                tiEtNameFamilyBiodata.setTextColor(ContextCompat.getColor(this, R.color.md_theme_primary))
                tiEtNameFamilyBiodata.isEnabled = true
            } else {
                tvNameFamilyBiodata.isEnabled = false
                tilNameFamilyBiodata.isEnabled = false
                tvNameFamilyBiodata.setTextColor(
                    ContextCompat.getColor(this, R.color.md_theme_outline),
                )
                tiEtNameFamilyBiodata.setTextColor(
                    ContextCompat.getColor(this, R.color.md_theme_outline),
                )
                tiEtNameFamilyBiodata.isEnabled = false
            }
        }
    }

    private fun setupLabelTitle() {
        val items = listOf("Mr.", "Mrs.", "Ms.", "Dr.")
        val autoComplete = bindingForm.autoCompleteTitle
        val adapter = ArrayAdapter(this, R.layout.item_label_title_biodata, items)

        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val itemSelected = adapterView.getItemAtPosition(i)
                Toast.makeText(this, "Item : $itemSelected", Toast.LENGTH_SHORT).show()
            }
    }

    companion object {
        const val EXTRA_FLIGHT_SEARCH_PARAMS = "EXTRA_FLIGHT_SEARCH_PARAMS"
        const val EXTRA_FLIGHT = "EXTRA_FLIGHT"
        const val EXTRA_TOTAL_PRICE = "EXTRA_TOTAL_PRICE"

        fun startActivity(
            context: Context,
            params: FlightSearchParams,
            flight: Flight,
            totalPrice: Double,
        ) {
            val intent = Intent(context, PassengerBioDataActivity::class.java)
            intent.putExtra(EXTRA_FLIGHT_SEARCH_PARAMS, params)
            intent.putExtra(EXTRA_FLIGHT, flight)
            intent.putExtra(EXTRA_TOTAL_PRICE, totalPrice)
            context.startActivity(intent)
        }
    }
}
