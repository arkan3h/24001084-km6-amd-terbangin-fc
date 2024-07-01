package com.arkan.terbangin.presentation.history.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arkan.terbangin.data.model.DetailHistory
import com.arkan.terbangin.databinding.ItemPassengerDetailsBinding

class DetailHistoryAdapter : RecyclerView.Adapter<DetailHistoryAdapter.DetailHistoryViewHolder>() {
    private val asyncDataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<DetailHistory>() {
                override fun areItemsTheSame(
                    oldItem: DetailHistory,
                    newItem: DetailHistory,
                ): Boolean {
                    return oldItem.passengerId == newItem.passengerId
                }

                override fun areContentsTheSame(
                    oldItem: DetailHistory,
                    newItem: DetailHistory,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(item: List<DetailHistory>) {
        val uniqueItems = item.distinctBy { it.passengerId }
        asyncDataDiffer.submitList(uniqueItems)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DetailHistoryViewHolder {
        return DetailHistoryViewHolder(
            ItemPassengerDetailsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: DetailHistoryViewHolder,
        position: Int,
    ) {
        holder.bind(asyncDataDiffer.currentList[position], position)
    }

    class DetailHistoryViewHolder(
        private val binding: ItemPassengerDetailsBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: DetailHistory,
            position: Int,
        ) {
            binding.tvPassengerName.text = "Penumpang ${position + 1}: ${item.passengerTitle}. ${item.passengerName} ${item.passengerFamilyName}"
            binding.tvPassengerId.text = "ID: ${item.passengerId}"
        }
    }
}
