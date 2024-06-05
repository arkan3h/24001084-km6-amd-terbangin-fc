package com.arkan.terbangin.presentation.calendardeparturedate

import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.children
import com.arkan.terbangin.R
import com.arkan.terbangin.databinding.BottomSheetCalendarDepartureDateBinding
import com.arkan.terbangin.databinding.ItemCalendarDayBinding
import com.arkan.terbangin.databinding.ItemCalendarHeaderBinding
import com.arkan.terbangin.presentation.calendarfilterhistory.makeInVisible
import com.arkan.terbangin.presentation.calendarfilterhistory.makeVisible
import com.arkan.terbangin.presentation.calendarfilterhistory.setTextColorRes
import com.arkan.terbangin.presentation.calendarfilterhistory.shared.displayText
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class CalendarDepartureDateBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetCalendarDepartureDateBinding
    private var selectedDate: LocalDate? = null
    private var temporarySelectedDate: LocalDate? = null
    private val today = LocalDate.now()

    @RequiresApi(Build.VERSION_CODES.O)
    private val headerDateFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = BottomSheetCalendarDepartureDateBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupData()

        binding.ivClose.setOnClickListener {
            dialog?.cancel()
        }

        binding.exFourSaveButton.setOnClickListener {
            if (temporarySelectedDate != null) {
                selectedDate = temporarySelectedDate
                val text = "Selected: ${DateTimeFormatter.ofPattern("d MMMM yyyy").format(selectedDate)}"
                Snackbar.make(requireView(), text, Snackbar.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            } else {
                Snackbar.make(requireView(), "No date selected", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupData() {
        val daysOfWeek = daysOfWeek()
        binding.legendLayout.root.children.forEachIndexed { index, child ->
            (child as TextView).apply {
                text = daysOfWeek[index].displayText()
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
                setTextColorRes(R.color.md_theme_onBackground)
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
    }

    private fun configureBinders() {
        val calendarView = binding.exFourCalendar

        @RequiresApi(Build.VERSION_CODES.O)
        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay
            val textView = ItemCalendarDayBinding.bind(view).exFourDayText

            init {
                textView.setOnClickListener {
                    if (day.position == DayPosition.MonthDate && !day.date.isBefore(today)) {
                        val oldDate = temporarySelectedDate
                        temporarySelectedDate = if (temporarySelectedDate == day.date) null else day.date

                        // Notify changes for both the old and new dates to update their views
                        calendarView.notifyDateChanged(day.date)
                        oldDate?.let { calendarView.notifyDateChanged(it) }
                        bindSummaryViews()
                    }
                }
            }
        }

        calendarView.dayBinder =
            object : MonthDayBinder<DayViewContainer> {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun create(view: View) = DayViewContainer(view)

                @RequiresApi(Build.VERSION_CODES.O)
                override fun bind(
                    container: DayViewContainer,
                    data: CalendarDay,
                ) {
                    container.day = data
                    val textView = container.textView
                    textView.text = data.date.dayOfMonth.toString()

                    if (data.position == DayPosition.MonthDate) {
                        textView.makeVisible()
                        if (data.date.isBefore(today)) {
                            textView.setTextColorRes(R.color.md_theme_outlineVariant)
                        } else {
                            when (data.date) {
                                temporarySelectedDate -> {
                                    textView.setTextColorRes(R.color.md_theme_background)
                                    textView.setBackgroundResource(R.drawable.calendar_single_selected_bg)
                                }
                                else -> {
                                    textView.setTextColorRes(R.color.md_theme_onBackground)
                                    textView.background = null
                                }
                            }
                        }
                    } else {
                        textView.makeInVisible()
                    }
                }
            }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val textView = ItemCalendarHeaderBinding.bind(view).exFourHeaderText
        }
        calendarView.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)

                @RequiresApi(Build.VERSION_CODES.O)
                override fun bind(
                    container: MonthViewContainer,
                    data: CalendarMonth,
                ) {
                    container.textView.text = data.yearMonth.displayText()
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindSummaryViews() {
        binding.tvDepartureDate.apply {
            if (temporarySelectedDate != null) {
                text = headerDateFormatter.format(temporarySelectedDate)
                setTextColorRes(R.color.md_theme_onBackground)
            } else {
                text = getString(R.string.text_pilih_tanggal_berangkat)
                setTextColorRes(R.color.md_theme_outlineVariant)
            }
        }

        binding.exFourSaveButton.isEnabled = temporarySelectedDate != null
    }
}
