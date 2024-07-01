package com.arkan.terbangin.presentation.history.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.arkan.terbangin.R
import com.arkan.terbangin.base.OnItemCLickedListener
import com.arkan.terbangin.data.model.DetailHistory
import com.arkan.terbangin.data.model.History
import com.arkan.terbangin.databinding.ActivityDetailHistoryBinding
import com.arkan.terbangin.presentation.history.detail.adapter.DetailHistoryAdapter
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
        setAppBarTitle()
        setAdapter()
        setClickListener()
        getDetailInfo()
    }

    private fun setClickListener() {
        binding.layoutAppBar.ibBtnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
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
        binding.tvFlightDestination.text = detail.bookingStatus
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
                    detail.classes,
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
        detailHistoryAdapter.submitData(data)
    }

    private fun onItemClick(item: DetailHistory) {
    }

    private fun setAdapter() {
        val itemClickListener =
            object : OnItemCLickedListener<DetailHistory> {
                override fun onItemClicked(item: DetailHistory) {
                    onItemClick(item)
                }
            }
        detailHistoryAdapter = DetailHistoryAdapter(itemClickListener)
        binding.itemHistoryDetail.rvPassengerDetailsList.adapter = detailHistoryAdapter
    }

    private fun setAppBarTitle() {
        binding.layoutAppBar.tvAppbarTitle.text = getString(R.string.appbar_title_rincian_penerbangan)
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
