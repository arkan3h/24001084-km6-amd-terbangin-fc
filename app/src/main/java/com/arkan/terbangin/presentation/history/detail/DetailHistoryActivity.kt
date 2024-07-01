package com.arkan.terbangin.presentation.history.detail

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import coil.load
import com.arkan.terbangin.R
import com.arkan.terbangin.data.model.DetailHistory
import com.arkan.terbangin.data.model.History
import com.arkan.terbangin.databinding.ActivityDetailHistoryBinding
import com.arkan.terbangin.presentation.checkout.payment.PaymentActivity
import com.arkan.terbangin.presentation.history.detail.adapter.DetailHistoryAdapter
import com.arkan.terbangin.utils.capitalizeFirstLetter
import com.arkan.terbangin.utils.formatClassHistory
import com.arkan.terbangin.utils.formatDateHourStringHistory
import com.arkan.terbangin.utils.formatDateStringHistory
import com.arkan.terbangin.utils.proceedWhen
import com.arkan.terbangin.utils.toIndonesianFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailHistoryActivity : AppCompatActivity() {
    private val binding: ActivityDetailHistoryBinding by lazy {
        ActivityDetailHistoryBinding.inflate(layoutInflater)
    }

    private lateinit var detailHistoryAdapter: DetailHistoryAdapter

    private val viewModel: DetailHistoryViewModel by viewModel {
        parametersOf(intent.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setState()
        setClickListener()
        getDetailInfo()
        getProfile()
    }

    private fun setClickListener() {
        binding.layoutAppBar.ibBtnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.layoutTotalPrice.btnContinuePayment.setOnClickListener {
            if (viewModel.history?.bookingStatus == "UNPAID") {
                navigateToPayment(viewModel.history?.snapLink!!)
            } else if (viewModel.history?.bookingStatus == "ISSUED") {
            }
        }
    }

    private fun getDetailInfo() {
        viewModel.getDetailHistory(viewModel.history?.bookingId!!).observe(this) { it ->
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data ->
                        bindDetail(data)
                    }
                },
            )
        }
    }

    private fun bindDetail(data: List<DetailHistory>) {
        val detail = data[0]
        setBackgroundStatus(detail.bookingStatus, binding)
        binding.tvFlightDestination.text = detail.bookingStatus.capitalizeFirstLetter()
        binding.itemHistoryDetail.apply {
            tvBookingCode.text = detail.bookingCode
            tvTakeoffTime.text = formatDateHourStringHistory(detail.departureAt)
            tvTakeoffDate.text = formatDateStringHistory(detail.departureAt)
            tvAirportOrigin.text =
                getString(
                    R.string.binding_airport_origin_detail,
                    detail.airportStart,
                    detail.terminalStart,
                )
            ivLogoAirline.load(detail.aircraftImg)
            tvAirlineName.text =
                getString(
                    R.string.binding_airline_name_detail,
                    detail.aircraftName,
                    formatClassHistory(detail.classes),
                )
            tvAirlineCode.text = detail.aircraftCode
            tvLandingTime.text = formatDateHourStringHistory(detail.arrivalAt)
            tvLandingDate.text = formatDateStringHistory(detail.arrivalAt)
            tvAirportDestination.text =
                getString(
                    R.string.binding_airport_origin_detail,
                    detail.airportEnd,
                    detail.terminalEnd,
                )
        }
        binding.layoutTotalPrice.tvTotalPrice.text = detail.totalPrice.toDouble().toIndonesianFormat()
        setAdapter(data)
    }

    private fun getProfile() {
        viewModel.getProfileData().observe(this) { it ->
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data ->
                        viewModel.saveProfile(data)
                    }
                },
            )
        }
    }

    private fun setAdapter(data: List<DetailHistory>) {
        detailHistoryAdapter = DetailHistoryAdapter()
        detailHistoryAdapter.submitData(data)
        binding.itemHistoryDetail.rvPassengerDetailsList.adapter = detailHistoryAdapter
    }

    private fun setState() {
        binding.layoutAppBar.tvAppbarTitle.text = getString(R.string.appbar_title_rincian_penerbangan)
        if (viewModel.history?.bookingStatus == "ISSUED") {
            binding.layoutTotalPrice.btnContinuePayment.text = "Cetak Tiket"
        } else if (viewModel.history?.bookingStatus == "CANCELLED") {
            binding.layoutTotalPrice.btnContinuePayment.isVisible = false
        }
    }

    private fun setBackgroundStatus(
        status: String,
        viewBinding: ActivityDetailHistoryBinding,
    ) {
        val color =
            when (status) {
                "ISSUED" -> R.color.green
                "UNPAID" -> R.color.md_theme_error
                else -> R.color.md_theme_outline
            }
        val drawable = getOvalBackground(ContextCompat.getColor(viewBinding.root.context, color))
        viewBinding.tvFlightDestination.background = drawable
    }

    private fun getOvalBackground(color: Int): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 50f // Set this to a suitable value to make it oval
            setColor(color)
        }
    }

    private fun navigateToPayment(snapLink: String) {
        PaymentActivity.startActivity(this, snapLink)
    }

    companion object {
        const val EXTRA_ITEM_HISTORY = "EXTRA_ITEM_HISTORY"

        fun startActivity(
            context: Context,
            item: History,
        ) {
            val intent = Intent(context, DetailHistoryActivity::class.java)
            intent.putExtra(EXTRA_ITEM_HISTORY, item)
            context.startActivity(intent)
        }
    }
}
