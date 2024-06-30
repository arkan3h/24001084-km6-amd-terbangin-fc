package com.arkan.terbangin.presentation.history.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arkan.terbangin.base.OnItemCLickedListener
import com.arkan.terbangin.data.model.DetailHistory
import com.arkan.terbangin.databinding.ItemPassengerDetailsBinding

class DetailHistoryAdapter(
    private val listener: OnItemCLickedListener<DetailHistory>,
) : RecyclerView.Adapter<DetailHistoryAdapter.DetailHistoryViewHolder>() {
    private val itemData = mutableListOf<DetailHistory>()

    fun submitData(newData: List<DetailHistory>) {
        itemData.clear()
        itemData.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailHistoryViewHolder {
        return DetailHistoryViewHolder(
            ItemPassengerDetailsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            listener,
        )
    }

    override fun getItemCount(): Int = itemData.size

    override fun onBindViewHolder(
        holder: DetailHistoryViewHolder,
        position: Int
    ) {
        holder.bind(itemData[position])
    }

    class DetailHistoryViewHolder(
        private val binding: ItemPassengerDetailsBinding,
        private val listener: OnItemCLickedListener<DetailHistory>
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DetailHistory) {
//            binding.tvPassengerName.text =item.fullname
//            binding.tvPassengerId.text = item.passengerId

            itemView.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }
}