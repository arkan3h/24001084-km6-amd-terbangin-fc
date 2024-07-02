package com.arkan.terbangin.presentation.checkout.selectpassengerseat.returnflight

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.arkan.terbangin.R
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.data.model.PassengerBioDataList
import com.arkan.terbangin.data.model.Seat
import com.arkan.terbangin.data.model.SeatList
import com.arkan.terbangin.databinding.ActivitySelectPassengerSeatBinding
import com.arkan.terbangin.presentation.checkout.detail.CheckoutDetailActivity
import com.arkan.terbangin.presentation.checkout.selectpassengerseat.seatbookview.SeatBookView
import com.arkan.terbangin.presentation.checkout.selectpassengerseat.seatbookview.SeatClickListener
import com.arkan.terbangin.utils.proceedWhen
import com.arkan.terbangin.utils.showAlertDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SelectReturnPassengerSeatActivity : AppCompatActivity() {
    private val binding: ActivitySelectPassengerSeatBinding by lazy {
        ActivitySelectPassengerSeatBinding.inflate(layoutInflater)
    }
    private val viewModel: SelectReturnPassengerSeatViewModel by viewModel {
        parametersOf(intent.extras)
    }

    private lateinit var seatBookView: SeatBookView

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
                viewModel.params?.destinationCity?.code,
                viewModel.params?.departureCity?.code,
                viewModel.selectableSeat.toString(),
            )
        binding.tvTitleInfoSeat.text =
            getString(
                R.string.binding_title_seat,
                viewModel.params?.ticketClass?.name,
                viewModel.capacity.toString(),
            )
    }

    private fun setSeatView(seats: List<Seat>) {
        val sortedSeats = seats.sortedBy { it.seatNumber }
        val newSeats = generateSeatsString(viewModel.seatCapacity, sortedSeats)
        val seatNumber = sortedSeats.map { it.seatNumber }
        Log.d("title", "setSeatView: $seatNumber")
        val title = generateSeatTitles(seatNumber)
        seatBookView = findViewById(R.id.layoutSeat)
        seatBookView.setSeatsLayoutString(newSeats)
            .isCustomTitle(true)
            .setCustomTitle(title)
            .setSeatLayoutPadding(2)
            .setSelectSeatLimit(viewModel.selectableSeat)
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

    private fun generateSeatTitles(seatNumbers: List<Int>): List<String> {
        val titles = mutableListOf<String>()
        val rowCount = seatNumbers.size / 6 + if (seatNumbers.size % 6 != 0) 1 else 0
        val seatLabels = listOf("A", "B", "C", "D", "E", "F")

        for (i in 0 until rowCount) {
            titles.add("/") // Start of a new row
            for (j in 0..2) {
                val seatIndex = i * 6 + j
                if (seatIndex < seatNumbers.size) {
                    titles.add("${seatNumbers[seatIndex]}${seatLabels[j]}")
                }
            }
            titles.add("") // Separator
            for (j in 3..5) {
                val seatIndex = i * 6 + j
                if (seatIndex < seatNumbers.size) {
                    titles.add("${seatNumbers[seatIndex]}${seatLabels[j]}")
                }
            }
        }

        return titles
    }

    private fun setClickListener() {
        binding.layoutAppBar.ibBtnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnSave.setOnClickListener {
            setSelectedSeat()
        }
    }

    private fun setSelectedSeat() {
        val selectedSeatIds = seatBookView.getSelectedIdList()
        val selectedSeat = mutableListOf<Seat>()

        viewModel.seats.value?.let { seats ->
            selectedSeatIds.forEach { id ->
                val seat = seats.getOrNull(id - 1)
                seat?.let {
                    selectedSeat.add(it)
                }
                // Toast.makeText(this, "Selected Seats: $selectedSeatIds", Toast.LENGTH_SHORT).show()
                navigateToCheckout(
                    viewModel.totalPrice!!,
                    viewModel.flight!!,
                    viewModel.flightReturn,
                    viewModel.params!!,
                    viewModel.passengerDataList!!,
                    viewModel.seatDeparture!!,
                    SeatList(selectedSeat),
                )
            }
        }
    }

    private fun navigateToCheckout(
        totalPrice: Double,
        flight: Flight,
        flightReturn: Flight?,
        params: FlightSearchParams,
        passengerList: PassengerBioDataList,
        seatDeparture: SeatList,
        seats: SeatList,
    ) {
        CheckoutDetailActivity.startActivity(
            this,
            totalPrice,
            flight,
            flightReturn,
            params,
            passengerList,
            seatDeparture,
            seats,
        )
    }

    companion object {
        const val EXTRA_FLIGHT_SEARCH_PARAMS = "EXTRA_FLIGHT_SEARCH_PARAMS"
        const val EXTRA_FLIGHT = "EXTRA_FLIGHT"
        const val EXTRA_FLIGHT_RETURN = "EXTRA_FLIGHT_RETURN"
        const val EXTRA_TOTAL_PRICE = "EXTRA_TOTAL_PRICE"
        const val EXTRA_PASSENGER_DATA = "EXTRA_PASSENGER_DATA"
        const val EXTRA_SEAT = "EXTRA_SEAT"

        fun startActivity(
            context: Context,
            params: FlightSearchParams,
            flight: Flight,
            flightReturn: Flight?,
            totalPrice: Double,
            passengerData: PassengerBioDataList,
            seats: SeatList,
        ) {
            val intent = Intent(context, SelectReturnPassengerSeatActivity::class.java)
            intent.putExtra(EXTRA_FLIGHT_SEARCH_PARAMS, params)
            intent.putExtra(EXTRA_FLIGHT, flight)
            intent.putExtra(EXTRA_FLIGHT_RETURN, flightReturn)
            intent.putExtra(EXTRA_TOTAL_PRICE, totalPrice)
            intent.putExtra(EXTRA_PASSENGER_DATA, passengerData)
            intent.putExtra(EXTRA_SEAT, seats)
            context.startActivity(intent)
        }
    }
}
