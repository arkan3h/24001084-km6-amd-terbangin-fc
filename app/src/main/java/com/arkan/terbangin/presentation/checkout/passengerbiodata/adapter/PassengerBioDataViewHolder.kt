package com.arkan.terbangin.presentation.checkout.passengerbiodata.adapter

import android.app.DatePickerDialog
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.arkan.terbangin.R
import com.arkan.terbangin.data.model.PassengerBioData
import com.arkan.terbangin.data.model.PassengerForm
import com.arkan.terbangin.databinding.ActivityPassengerBiodataBinding
import com.arkan.terbangin.databinding.ItemPassengerBiodataBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class PassengerBioDataViewHolder(
    private val activityBinding: ActivityPassengerBiodataBinding,
    private val binding: ItemPassengerBiodataBinding,
    private val userId: String,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var passengerData: PassengerForm

    fun bind(passenger: PassengerForm) {
        passengerData = passenger
        setFormTitle(passengerData)
        setFamilyName()
        setupLabelTitle()
        setClickListener()
    }

    fun getData(): PassengerBioData {
        val type = binding.autoCompleteTitle.text.toString()
        val fullName = binding.tiEtNameBiodata.text.toString()
        val familyName = if (binding.switchAskNameFamily.isChecked) binding.tiEtNameFamilyBiodata.text.toString() else null
        val birthDay = binding.tiEtDateBirthBiodata.text.toString()
        val nationality = binding.tiEtCitizenship.text.toString()
        val identityId = binding.tiEtKtpPassport.text.toString()
        val issuingCountry = binding.tiEtIssuingCountry.text.toString()
        var title = "ADULT"
        when (passengerData.title) {
            "Dewasa" -> {
                title = "ADULT"
            }
            "Anak-anak" -> {
                title = "CHILD"
            }
            "Bayi" -> {
                title = "BABY"
            }
        }
        Log.d("Fullname", fullName)
        Log.d("Title", title)

        return PassengerBioData(
            userId = userId,
            type = title,
            title = type,
            fullName = fullName,
            familyName = familyName,
            birthDay = birthDay,
            nationality = nationality,
            identityId = identityId,
            issuingCountry = issuingCountry,
        )
    }

    private fun setFormTitle(passenger: PassengerForm) {
        binding.tvTitleFormBiodata.text =
            binding.root.context.getString(
                R.string.binding_text_title_passenger_form,
                passenger.id.toString(),
                passenger.title,
            )
    }

    private fun setClickListener() {
        binding.tiEtDateBirthBiodata.setOnClickListener {
            showDateBirthPickerDialog()
        }
//        binding.tilDateValidUntil.setOnClickListener {
//            showDateValidUntilPickerDialog()
//        }
    }

    private fun setFamilyName() {
        binding.switchAskNameFamily.setOnCheckedChangeListener { _, isChecked ->
            binding.tvNameFamilyBiodata.isVisible = isChecked
            binding.tilNameFamilyBiodata.isVisible = isChecked
        }
    }

//    private fun showDateValidUntilPickerDialog() {
//        val calendar = Calendar.getInstance()
//        val year = calendar.get(Calendar.YEAR)
//        val month = calendar.get(Calendar.MONTH)
//        val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//        val dateValidUntilPickerDialog =
//            DatePickerDialog(
//                binding.root.context,
//                { _, selectedYear, selectedMonth, selectedDay ->
//                    // Set the selected date to the input
//                    val formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
// //                    binding.tiEtDateValidUntil.setText(formattedDate)
//                },
//                year,
//                month,
//                day,
//            )
//
//        dateValidUntilPickerDialog.show()
//    }

    private fun showDateBirthPickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateBirthPickerDialog =
            DatePickerDialog(
                activityBinding.root.context,
                { _, selectedYear, selectedMonth, selectedDay ->
                    calendar.apply {
                        set(Calendar.YEAR, selectedYear)
                        set(Calendar.MONTH, selectedMonth)
                        set(Calendar.DAY_OF_MONTH, selectedDay)
                    }
                    val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale("id", "ID")).format(calendar.time)
                    binding.tiEtDateBirthBiodata.setText(formattedDate)
                },
                year,
                month,
                day,
            )

        dateBirthPickerDialog.show()
    }

    private fun setupLabelTitle() {
        val items = listOf("MR", "MRS")
        val autoComplete = binding.autoCompleteTitle
        val adapter = ArrayAdapter(binding.root.context, R.layout.item_label_title_biodata, items)

        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val itemSelected = adapterView.getItemAtPosition(i)
            }
    }
}
