package com.arkan.terbangin.presentation.calenderfilterhistory

import android.graphics.drawable.Drawable
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
import com.arkan.terbangin.databinding.BottomSheetCalenderFilterHistoryBinding
import com.arkan.terbangin.databinding.ItemCalendarDayBinding
import com.arkan.terbangin.databinding.ItemCalendarHeaderBinding
import com.arkan.terbangin.presentation.calenderfilterhistory.shared.ContinuousSelectionHelper
import com.arkan.terbangin.presentation.calenderfilterhistory.shared.DateSelection
import com.arkan.terbangin.presentation.calenderfilterhistory.shared.dateRangeDisplayText
import com.arkan.terbangin.presentation.calenderfilterhistory.shared.displayText
import com.google.android.material.bottomsheet.BottomSheetBehavior
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

class CalenderFilterHistoryBottomSheet : BottomSheetDialogFragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    private val today = LocalDate.now()

    @RequiresApi(Build.VERSION_CODES.O)
    private var selection = DateSelection()

    @RequiresApi(Build.VERSION_CODES.O)
    private val headerDateFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM")

    private lateinit var binding: BottomSheetCalenderFilterHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = BottomSheetCalenderFilterHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setFullScreen()
        setupData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupData() {
        // Set the First day of week depending on Locale
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindSummaryViews() {
        binding.exFourStartDateText.apply {
            if (selection.startDate != null) {
                text = headerDateFormatter.format(selection.startDate)
                setTextColorRes(R.color.md_theme_onBackground)
            } else {
                text = getString(R.string.text_pilih_tanggal_mulai)
                setTextColorRes(R.color.md_theme_outlineVariant)
            }
        }

        binding.exFourEndDateText.apply {
            if (selection.endDate != null) {
                text = headerDateFormatter.format(selection.endDate)
                setTextColorRes(R.color.md_theme_onBackground)
            } else {
                text = getString(R.string.text_pilih_tanggal_sampai)
                setTextColorRes(R.color.md_theme_outlineVariant)
            }
        }

        binding.exFourSaveButton.isEnabled = selection.daysBetween != null
    }

    private fun configureBinders() {
        val clipLevelHalf = 5000
        val ctx = requireContext()
        val rangeStartBackground =
            ctx.getDrawableCompat(R.drawable.calendar_continuous_selected_bg_start).also {
                it.level = clipLevelHalf // Used by ClipDrawable
            }
        val rangeEndBackground =
            ctx.getDrawableCompat(R.drawable.calendar_continuous_selected_bg_end).also {
                it.level = clipLevelHalf // Used by ClipDrawable
            }
        val rangeMiddleBackground =
            ctx.getDrawableCompat(R.drawable.calendar_continuous_selected_bg_middle)
        val singleBackground = ctx.getDrawableCompat(R.drawable.calendar_single_selected_bg)
        val todayBackground = ctx.getDrawableCompat(R.drawable.calendar_today_bg)

        @RequiresApi(Build.VERSION_CODES.O)
        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay // Will be set when this container is bound.
            val binding = ItemCalendarDayBinding.bind(view)

            init {
                view.setOnClickListener {
                    if (day.position == DayPosition.MonthDate &&
                        (day.date == today || day.date.isAfter(today))
                    ) {
                        selection =
                            ContinuousSelectionHelper.getSelection(
                                clickedDate = day.date,
                                dateSelection = selection,
                            )
                        this@CalenderFilterHistoryBottomSheet.binding.exFourCalendar.notifyCalendarChanged()
                        bindSummaryViews()
                    }
                }
            }
        }

        binding.exFourCalendar.dayBinder =
            object : MonthDayBinder<DayViewContainer> {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun create(view: View) = DayViewContainer(view)

                @RequiresApi(Build.VERSION_CODES.O)
                override fun bind(
                    container: DayViewContainer,
                    data: CalendarDay,
                ) {
                    container.day = data
                    val textView = container.binding.exFourDayText
                    val roundBgView = container.binding.exFourRoundBackgroundView
                    val continuousBgView = container.binding.exFourContinuousBackgroundView

                    textView.text = null
                    roundBgView.makeInVisible()
                    continuousBgView.makeInVisible()

                    val (startDate, endDate) = selection

                    when (data.position) {
                        DayPosition.MonthDate -> {
                            textView.text = data.date.dayOfMonth.toString()
                            if (data.date.isBefore(today)) {
                                textView.setTextColorRes(R.color.md_theme_outlineVariant)
                            } else {
                                when {
                                    startDate == data.date && endDate == null -> {
                                        textView.setTextColorRes(R.color.md_theme_background)
                                        roundBgView.applyBackground(singleBackground)
                                    }
                                    data.date == startDate -> {
                                        textView.setTextColorRes(R.color.md_theme_background)
                                        continuousBgView.applyBackground(rangeStartBackground)
                                        roundBgView.applyBackground(singleBackground)
                                    }
                                    startDate != null && endDate != null && (data.date > startDate && data.date < endDate) -> {
                                        textView.setTextColorRes(R.color.md_theme_onBackground)
                                        continuousBgView.applyBackground(rangeMiddleBackground)
                                    }
                                    data.date == endDate -> {
                                        textView.setTextColorRes(R.color.md_theme_background)
                                        continuousBgView.applyBackground(rangeEndBackground)
                                        roundBgView.applyBackground(singleBackground)
                                    }
                                    data.date == today -> {
                                        textView.setTextColorRes(R.color.md_theme_onBackground)
                                        roundBgView.applyBackground(todayBackground)
                                    }
                                    else -> textView.setTextColorRes(R.color.md_theme_onBackground)
                                }
                            }
                        }
                        // Make the coloured selection background continuous on the
                        // invisible in and out dates across various months.
                        DayPosition.InDate ->
                            if (startDate != null && endDate != null &&
                                ContinuousSelectionHelper.isInDateBetweenSelection(
                                    data.date,
                                    startDate,
                                    endDate,
                                )
                            ) {
                                continuousBgView.applyBackground(rangeMiddleBackground)
                            }
                        DayPosition.OutDate ->
                            if (startDate != null && endDate != null &&
                                ContinuousSelectionHelper.isOutDateBetweenSelection(
                                    data.date,
                                    startDate,
                                    endDate,
                                )
                            ) {
                                continuousBgView.applyBackground(rangeMiddleBackground)
                            }
                    }
                }

                private fun View.applyBackground(drawable: Drawable) {
                    makeVisible()
                    background = drawable
                }
            }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val textView = ItemCalendarHeaderBinding.bind(view).exFourHeaderText
        }
        binding.exFourCalendar.monthHeaderBinder =
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

        binding.ivClose.setOnClickListener {
            dialog?.cancel()
        }
    }

    private fun setFullScreen() {
        val bottomSheet: FrameLayout = dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
        bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
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
}
