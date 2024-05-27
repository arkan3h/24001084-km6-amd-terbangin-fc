package com.arkan.terbangin.presentation.calenderfilterhistory

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.children
import com.arkan.terbangin.R
import com.arkan.terbangin.databinding.Example4FragmentBinding
import com.arkan.terbangin.presentation.calenderfilterhistory.shared.DateSelection
import com.arkan.terbangin.presentation.calenderfilterhistory.shared.dateRangeDisplayText
import com.arkan.terbangin.presentation.calenderfilterhistory.shared.displayText
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class CalenderFilterHistoryBottomSheet : BottomSheetDialogFragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    private val today = LocalDate.now()

    @RequiresApi(Build.VERSION_CODES.O)
    private var selection = DateSelection()

    @RequiresApi(Build.VERSION_CODES.O)
    private val headerDateFormatter = DateTimeFormatter.ofPattern("EEE'\n'd MMM")

    private lateinit var binding: Example4FragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = Example4FragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        setFullScreen()
        super.onViewCreated(view, savedInstanceState)

        // Set the First day of week depending on Locale
        val daysOfWeek = daysOfWeek()
        binding.legendLayout.root.children.forEachIndexed { index, child ->
            (child as TextView).apply {
                text = daysOfWeek[index].displayText()
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
                setTextColorRes(R.color.md_theme_outline)
            }
        }

        configureBinders()
        val currentMonth = YearMonth.now()
        binding.exFourCalendar.setup(
            currentMonth,
            currentMonth.plusMonths(12),
            daysOfWeek.first(),
        )
        binding.exFourCalendar.scrollToMonth(currentMonth)

        binding.exFourSaveButton.setOnClickListener click@{
            val (startDate, endDate) = selection
            if (startDate != null && endDate != null) {
                val text = dateRangeDisplayText(startDate, endDate)
                Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG).show()
            }
            parentFragmentManager.popBackStack()
        }

        bindSummaryViews()
    }

    private fun bindSummaryViews() {
        binding.exFourStartDateText.apply {
            if (selection.startDate != null) {
                text = headerDateFormatter.format(selection.startDate)
                setTextColorRes(R.color.md_theme_outline)
            } else {
                text = getString(R.string.text_start_date)
                setTextColor(Color.GRAY)
            }
        }

        binding.exFourEndDateText.apply {
            if (selection.endDate != null) {
                text = headerDateFormatter.format(selection.endDate)
                setTextColorRes(R.color.md_theme_outline)
            } else {
                text = getString(R.string.text_end_date)
                setTextColor(Color.GRAY)
            }
        }

        binding.exFourSaveButton.isEnabled = selection.daysBetween != null
    }

    private fun setFullScreen() {
        val bottomSheet: FrameLayout = dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
        bottomSheet.layoutParams.height = 2000
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.apply {
            peekHeight = resources.displayMetrics.heightPixels
            state = BottomSheetBehavior.STATE_EXPANDED
            isDraggable = false
            addBottomSheetCallback(
                object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(
                        bottomSheet: View,
                        newState: Int,
                    ) {}

                    override fun onSlide(
                        bottomSheet: View,
                        slideOffset: Float,
                    ) {}
                },
            )
        }
    }

//    private fun onClickListener() {
//        binding.btnOpenDatePicker.setOnClickListener {
//            openDateRangePicker()
//        }
//    }
}
