package com.arkan.terbangin.presentation.flightsearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arkan.terbangin.base.OnItemCLickedListener
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.databinding.ItemFlightListBinding
import com.arkan.terbangin.utils.formatDateHourString
import com.arkan.terbangin.utils.formatHours
import com.arkan.terbangin.utils.toIndonesianFormat

class FlightAdapter(
    private val listener: OnItemCLickedListener<Flight>,
    private val totalQty: Int,
    private val classSeat: String,
) : RecyclerView.Adapter<FlightAdapter.FlightViewHolder>() {
    private val asyncDataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Flight>() {
                override fun areItemsTheSame(
                    oldItem: Flight,
                    newItem: Flight,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Flight,
                    newItem: Flight,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(items: List<Flight>) {
        asyncDataDiffer.submitList(items)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FlightViewHolder {
        return FlightViewHolder(
            ItemFlightListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            listener,
            totalQty,
            classSeat,
        )
    }

    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: FlightViewHolder,
        position: Int,
    ) {
        holder.bind(asyncDataDiffer.currentList[position])
    }

    class FlightViewHolder(
        private val binding: ItemFlightListBinding,
        private val listener: OnItemCLickedListener<Flight>,
        private val totalQty: Int,
        private val classSeat: String,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Flight) {
            var totalPrice = 0.0
            binding.tvTakeoffTime.text = formatDateHourString(item.departureAt)
            binding.tvTakeoffPlace.text = item.startAirportCity
            binding.tvFlightDuration.text = formatHours(item.duration)
            binding.tvLandingTime.text = formatDateHourString(item.arrivalAt)
            binding.tvLandingPlace.text = item.endAirportCity
            binding.tvAirlineName.text = item.airlineName
            itemView.setOnClickListener {
                listener.onItemClicked(item)
            }
            when (classSeat) {
                "Economy" -> {
                    totalPrice = totalQty * item.priceEconomy.toDouble()
                }
                "Business" -> {
                    totalPrice = totalQty * item.priceBussines.toDouble()
                }
                "First Class" -> {
                    totalPrice = totalQty * item.priceFirstClass.toDouble()
                }
            }
            binding.tvFlightPrice.text = totalPrice.toIndonesianFormat()
        }
    }
}
