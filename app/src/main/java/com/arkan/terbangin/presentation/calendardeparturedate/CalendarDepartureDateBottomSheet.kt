 package com.arkan.terbangin.presentation.calendardeparturedate

 import android.os.Build
 import android.os.Bundle
 import android.util.TypedValue
 import androidx.fragment.app.Fragment
 import android.view.LayoutInflater
 import android.view.Menu
 import android.view.MenuInflater
 import android.view.MenuItem
 import android.view.View
 import android.view.ViewGroup
 import android.widget.TextView
 import androidx.annotation.RequiresApi
 import androidx.core.view.children
 import com.arkan.terbangin.R
 import com.arkan.terbangin.databinding.BottomSheetCalendarDepartureDateBinding
 import com.arkan.terbangin.databinding.BottomSheetCalenderFilterHistoryBinding
 import com.arkan.terbangin.databinding.ItemCalendarDayBinding
 import com.arkan.terbangin.databinding.ItemCalendarHeaderBinding
 import com.arkan.terbangin.presentation.calendarfilterhistory.getDrawableCompat
 import com.arkan.terbangin.presentation.calendarfilterhistory.makeInVisible
 import com.arkan.terbangin.presentation.calendarfilterhistory.makeVisible
 import com.arkan.terbangin.presentation.calendarfilterhistory.setTextColorRes
 import com.arkan.terbangin.presentation.calendarfilterhistory.shared.dateRangeDisplayText
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
    private val today = LocalDate.now()

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
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
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

    private lateinit var menuItem: MenuItem
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.example_2_menu, menu)
        menuItem = menu.getItem(0)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuItemDone) {
            val date = selectedDate ?: return false
            val text = "Selected: ${DateTimeFormatter.ofPattern("d MMMM yyyy").format(date)}"
            Snackbar.make(requireView(), text, Snackbar.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configureBinders() {
        val calendarView = binding.exFourCalendar

        class DayViewContainer(view: View) : ViewContainer(view) {
            // Will be set when this container is bound. See the dayBinder.
            lateinit var day: CalendarDay
            val textView = ItemCalendarDayBinding.bind(view).exFourDayText

            init {
                textView.setOnClickListener {
                    if (day.position == DayPosition.MonthDate) {
                        if (selectedDate == day.date) {
                            selectedDate = null
                            calendarView.notifyDayChanged(day)
                        } else {
                            val oldDate = selectedDate
                            selectedDate = day.date
                            calendarView.notifyDateChanged(day.date)
                            oldDate?.let { calendarView.notifyDateChanged(oldDate) }
                        }
                        menuItem.isVisible = selectedDate != null
                    }
                }
            }
        }

        calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.day = data
                val textView = container.textView
                textView.text = data.date.dayOfMonth.toString()

                if (data.position == DayPosition.MonthDate) {
                    textView.makeVisible()
                    when (data.date) {
                        selectedDate -> {
                            textView.setTextColorRes(R.color.md_theme_background)
                            textView.setBackgroundResource(R.drawable.calendar_single_selected_bg)
                        }
                        today -> {
                            textView.setTextColorRes(R.color.md_theme_error)
                            requireContext().getDrawableCompat(R.drawable.calendar_today_bg)
                            textView.background = null
                        }
                        else -> {
                            textView.setTextColorRes(R.color.md_theme_onBackground)
                            textView.background = null
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
                override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                    container.textView.text = data.yearMonth.displayText()
                }
            }
    }
 }
