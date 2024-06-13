package com.arkan.terbangin.presentation.home.terminal_search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arkan.terbangin.R
import com.arkan.terbangin.base.OnItemCLickedListener
import com.arkan.terbangin.data.model.Airport
import com.arkan.terbangin.databinding.ItemTerminalBinding

class TerminalAdapter(
    private val context: Context,
    private val listener: OnItemCLickedListener<Airport>,
) : RecyclerView.Adapter<TerminalAdapter.AirportCityViewHolder>() {
    private val asyncDataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Airport>() {
                override fun areItemsTheSame(
                    oldItem: Airport,
                    newItem: Airport,
                ): Boolean {
                    return oldItem.code == newItem.code
                }

                override fun areContentsTheSame(
                    oldItem: Airport,
                    newItem: Airport,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(items: List<Airport>) {
        asyncDataDiffer.submitList(items)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AirportCityViewHolder {
        return AirportCityViewHolder(
            context,
            ItemTerminalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            listener,
        )
    }

    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: AirportCityViewHolder,
        position: Int,
    ) {
        holder.bind(asyncDataDiffer.currentList[position])
    }

    class AirportCityViewHolder(
        private val context: Context,
        private val binding: ItemTerminalBinding,
        private val listener: OnItemCLickedListener<Airport>,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Airport) {
            binding.tvTerminal.text = context.getString(R.string.text_binding_airport_city, item.city, item.code)
            itemView.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }
}
