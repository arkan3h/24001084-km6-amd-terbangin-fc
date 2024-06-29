package com.arkan.terbangin.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.arkan.terbangin.base.OnItemCLickedListener
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.databinding.ItemHomeDestinationBinding
import com.arkan.terbangin.utils.formatDateString
import com.arkan.terbangin.utils.toIndonesianFormat

class FlightRecommendationAdapter(
    private val listener: OnItemCLickedListener<Flight>,
) : RecyclerView.Adapter<FlightRecommendationAdapter.FlightViewHolder>() {
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
            ItemHomeDestinationBinding.inflate(
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
        private val binding: ItemHomeDestinationBinding,
        private val listener: OnItemCLickedListener<Flight>,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Flight) {
            binding.tvItemFrom.text = item.startAirportCity
            binding.tvItemTo.text = item.endAirportCity
            binding.tvItemAirlines.text = item.airlineName
            binding.tvItemPrice.text = item.priceEconomy.toDouble().toIndonesianFormat()
            binding.tvItemDate.text = formatDateString(item.departureAt)
            binding.ivDestinationImage.load(item.endAirportPicture)
            itemView.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }
}
