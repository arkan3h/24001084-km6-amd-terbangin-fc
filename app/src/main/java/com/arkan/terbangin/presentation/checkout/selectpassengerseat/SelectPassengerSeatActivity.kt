package com.arkan.terbangin.presentation.checkout.selectpassengerseat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.arkan.terbangin.R
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.data.model.PassengerBioDataList
import com.arkan.terbangin.data.model.Seat
import com.arkan.terbangin.databinding.ActivitySelectPassengerSeatBinding
import com.arkan.terbangin.presentation.checkout.selectpassengerseat.seatbookview.SeatBookView
import com.arkan.terbangin.presentation.checkout.selectpassengerseat.seatbookview.SeatClickListener
import com.arkan.terbangin.utils.proceedWhen
import com.arkan.terbangin.utils.showAlertDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SelectPassengerSeatActivity : AppCompatActivity() {
    private val binding: ActivitySelectPassengerSeatBinding by lazy {
        ActivitySelectPassengerSeatBinding.inflate(layoutInflater)
    }
    private val viewModel: SelectPassenegrSeatViewModel by viewModel {
        parametersOf(intent.extras)
    }

    private lateinit var seatBookView: SeatBookView

    private var title =
        listOf(
            "/", "A1", "B1", "C1", "", "D1", "E1", "F1",
            "/", "A2", "B2", "C2", "", "D2", "E2", "F2",
            "/", "A3", "B3", "C3", "", "D3", "E3", "F3",
            "/", "A4", "B4", "C4", "", "D4", "E4", "F4",
            "/", "A5", "B5", "C5", "", "D5", "E5", "F5",
            "/", "A6", "B6", "C6", "", "D6", "E6", "F6",
            "/", "A7", "B7", "C7", "", "D7", "E7", "F7",
            "/", "A8", "B8", "C8", "", "D8", "E8", "F8",
            "/", "A9", "B9", "C9", "", "D9", "E9", "F9",
            "/", "A10", "B10", "C10", "", "D10", "E10", "F10",
            "/", "A11", "B11", "C11", "", "D11", "E11", "F11",
            "/", "A12", "B12", "C12", "", "D12", "E12", "F12",
            "/", "A13", "B13", "C13", "", "D13", "E13", "F13",
            "/", "A14", "B14", "C14", "", "D14", "E14", "F14",
        )

    private val arrTitle = title.filter { it.isNotEmpty() && !it.contains("/") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getSeat()
        setTitle()
        setClickListener()
        Log.d("Passenger Data List", "onCreate: ${viewModel.passengerDataList}")
    }

    private fun getSeat() {
        viewModel.getSeat().observe(this) { it ->
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { seat ->
                        setSeatView(seat)
                        viewModel.seat.value = seat
                    }
                },
                doOnError = {
                    showAlertDialog(it.exception?.message.orEmpty())
                },
            )
        }
    }

    private fun setTitle() {
        binding.layoutAppBar.tvAppbarTitle.text =
            getString(
                R.string.binding_title_select_seat,
                viewModel.params?.totalQty.toString(),
            )
        binding.tvTitleInfoSeat.text =
            getString(
                R.string.binding_title_seat,
                viewModel.params?.ticketClass?.name,
                viewModel.capacity.toString(),
            )
    }

    private fun setSeatView(seats: List<Seat>) {
        val newSeats = generateSeatsString(viewModel.capacity, seats)
        seatBookView = findViewById(R.id.layoutSeat)
        seatBookView.setSeatsLayoutString(newSeats)
            .isCustomTitle(true)
            .setCustomTitle(title)
            .setSeatLayoutPadding(2)
            .setSelectSeatLimit(viewModel.params?.totalQty!!)
            .setSeatSizeBySeatsColumnAndLayoutWidth(7, -1)

        seatBookView.show()

        seatBookView.setSeatClickListener(
            object : SeatClickListener {
                override fun onAvailableSeatClick(
                    selectedIdList: List<Int>,
                    view: View,
                ) {
                }

                override fun onBookedSeatClick(view: View) {
                }

                override fun onReservedSeatClick(view: View) {
                }
            },
        )
    }

    private fun generateSeatsString(
        capacity: Int,
        seats: List<Seat>,
    ): String {
        Log.d("Seat", "generateSeatsString: $seats")
        val sb = StringBuilder()
        var remainingCapacity = capacity
        var seatCounter = 0
        var space = 3
        sb.append("/")

        while (remainingCapacity > 0) {
            if (seatCounter % 6 == 0 && seatCounter > 0) {
                sb.append("/")
            }

            val seatsInRow = minOf(remainingCapacity, 3)
            repeat(seatsInRow) {
                val currentSeatIndex = seatCounter + it
                if (currentSeatIndex < seats.size) {
                    val seat = seats[currentSeatIndex]
                    sb.append(if (seat.isAvailable) "A" else "U") // Use API data
                } else {
                    sb.append("_") // Fill remaining spaces
                }
            }

            remainingCapacity -= seatsInRow
            seatCounter += seatsInRow
            space += seatsInRow

            if (space % 6 == 0 && remainingCapacity > 0) {
                sb.append("_")
            }
        }

        return sb.toString()
    }

    private fun setClickListener() {
        binding.layoutAppBar.ibBtnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnChoose.setOnClickListener {
            setSelectedSeat()
        }
    }

    private fun setSelectedSeat() {
        val selectedSeatIds = seatBookView.getSelectedIdList()
        val selectedSeatNumbersFromApi = mutableListOf<Int>()

        viewModel.seats.value?.let { seats ->
            selectedSeatIds.forEach { id ->
                val seat = seats.getOrNull(id - 1)
                seat?.let {
                    selectedSeatNumbersFromApi.add(it.seatNumber)
                }
            }
        }

        val selectedSeatsString = selectedSeatNumbersFromApi.joinToString(", ")
        Toast.makeText(this, "Selected Seats: $selectedSeatsString", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_FLIGHT_SEARCH_PARAMS = "EXTRA_FLIGHT_SEARCH_PARAMS"
        const val EXTRA_FLIGHT = "EXTRA_FLIGHT"
        const val EXTRA_TOTAL_PRICE = "EXTRA_TOTAL_PRICE"
        const val EXTRA_PASSENGER_DATA = "EXTRA_PASSENGER_DATA"

        fun startActivity(
            context: Context,
            params: FlightSearchParams,
            flight: Flight,
            totalPrice: Double,
            passengerData: PassengerBioDataList,
        ) {
            val intent = Intent(context, SelectPassengerSeatActivity::class.java)
            intent.putExtra(EXTRA_FLIGHT_SEARCH_PARAMS, params)
            intent.putExtra(EXTRA_FLIGHT, flight)
            intent.putExtra(EXTRA_TOTAL_PRICE, totalPrice)
            intent.putExtra(EXTRA_PASSENGER_DATA, passengerData)
            context.startActivity(intent)
        }
    }
}
