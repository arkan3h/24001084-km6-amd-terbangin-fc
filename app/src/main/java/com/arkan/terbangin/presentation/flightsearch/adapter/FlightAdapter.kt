package com.arkan.terbangin.presentation.flightsearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.databinding.ItemFlightListBinding

class FlightAdapter(
    private val listener: OnItemCLickedListener<Flight>,
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
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Flight) {
            binding.tvTakeoffTime.text = item.departureAt
            binding.tvTakeoffPlace.text = item.startAirportCity
            binding.tvFlightDuration.text = item.duration.toString()
            binding.tvLandingTime.text = item.arrivalAt
            binding.tvLandingPlace.text = item.endAirportCity
            binding.tvAirlineName.text = item.airlineName
            itemView.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }
}

interface OnItemCLickedListener<T> {
    fun onItemClicked(item: T)
}
