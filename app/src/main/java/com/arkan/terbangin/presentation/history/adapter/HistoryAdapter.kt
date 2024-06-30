package com.arkan.terbangin.presentation.history.adapter

import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.core.content.ContextCompat
import com.arkan.terbangin.R
import com.arkan.terbangin.data.model.History
import com.arkan.terbangin.databinding.ItemSectionDataHistoryBinding
import com.arkan.terbangin.databinding.ItemSectionHeaderHistoryBinding
import com.arkan.terbangin.utils.capitalizeFirstLetter
import com.arkan.terbangin.utils.convertCurrencyFormatString
import com.arkan.terbangin.utils.formatClassHistory
import com.arkan.terbangin.utils.formatDateHourStringHistory
import com.arkan.terbangin.utils.formatDateStringHistory
import com.arkan.terbangin.utils.formatHoursEng
import com.xwray.groupie.viewbinding.BindableItem

class HistoryMonthHeaderItem(private val month: String) : BindableItem<ItemSectionHeaderHistoryBinding>() {
    override fun bind(
        viewBinding: ItemSectionHeaderHistoryBinding,
        position: Int,
    ) {
        viewBinding.tvSectionHeaderName.text = month
    }

    override fun getLayout() = R.layout.item_section_header_history

    override fun initializeViewBinding(view: View): ItemSectionHeaderHistoryBinding {
        return ItemSectionHeaderHistoryBinding.bind(view)
    }
}

class HistoryDataItem(private val data: History) : BindableItem<ItemSectionDataHistoryBinding>() {
    override fun bind(
        viewBinding: ItemSectionDataHistoryBinding,
        position: Int,
    ) {
        setBackgroundStatus(data.bookingStatus, viewBinding)
        viewBinding.tvOrderStatus.text = data.bookingStatus.capitalizeFirstLetter()
        viewBinding.tvFlightStartLocation.text = data.startLoc
        viewBinding.tvDepartureDate.text = formatDateStringHistory(data.departureAt)
        viewBinding.tvDepartureTime.text = formatDateHourStringHistory(data.departureAt)
        viewBinding.tvFlightTime.text = formatHoursEng(data.duration)
        viewBinding.tvFlightEndLocation.text = data.endLoc
        viewBinding.tvLandingDate.text = formatDateStringHistory(data.arrivalAt)
        viewBinding.tvLandingTime.text = formatDateHourStringHistory(data.arrivalAt)
        viewBinding.tvBookingCode.text = data.bookingCode
        viewBinding.tvClassBooking.text = formatClassHistory(data.classes)
        viewBinding.tvOrderPrice.text = convertCurrencyFormatString(data.totalPayment)
    }

    override fun getLayout() = R.layout.item_section_data_history

    override fun initializeViewBinding(view: View): ItemSectionDataHistoryBinding {
        return ItemSectionDataHistoryBinding.bind(view)
    }

    private fun getOvalBackground(color: Int): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 50f // Set this to a suitable value to make it oval
            setColor(color)
        }
    }

    private fun setBackgroundStatus(
        status: String,
        viewBinding: ItemSectionDataHistoryBinding,
    ) {
        val color =
            when (status) {
                "ISSUED" -> R.color.green
                "UNPAID" -> R.color.md_theme_error
                else -> R.color.md_theme_outline
            }
        val drawable = getOvalBackground(ContextCompat.getColor(viewBinding.root.context, color))
        viewBinding.tvOrderStatus.background = drawable
    }
}
