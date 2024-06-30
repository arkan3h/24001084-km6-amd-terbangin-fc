package com.arkan.terbangin.presentation.history.filterhistory.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arkan.terbangin.R
import com.arkan.terbangin.data.model.StatusPayment
import com.arkan.terbangin.databinding.ItemListFilterHistorySheetBinding

class FilterHistorySheetAdapter(
    private val status: List<StatusPayment>,
    private val listener: OnFilterItemClickListener,
) : RecyclerView.Adapter<FilterHistorySheetAdapter.FilterHistorySheetViewHolder>() {
    private var selectedPosition = -1

    inner class FilterHistorySheetViewHolder(private val binding: ItemListFilterHistorySheetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            status: StatusPayment,
            isSelected: Boolean,
        ) {
            binding.tvItemFilter.text = status.statusPayment
            if (isSelected) {
                binding.ivCheckmark.visibility = View.VISIBLE
                binding.tvItemFilter.setTextColor(itemView.context.getColor(R.color.md_theme_onPrimary_highContrast))
                itemView.setBackgroundResource(R.drawable.bg_normal_primary)
            } else {
                binding.ivCheckmark.visibility = View.GONE
                binding.tvItemFilter.setTextColor(itemView.context.getColor(R.color.md_theme_onSurface_highContrast))
                itemView.setBackgroundResource(R.drawable.bg_normal_transparent)
            }
            binding.root.setOnClickListener {
                listener.onItemClick(status)
                notifyItemChanged(selectedPosition)
                val newPosition = bindingAdapterPosition
                if (newPosition != RecyclerView.NO_POSITION) {
                    selectedPosition = newPosition
                    notifyItemChanged(selectedPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FilterHistorySheetViewHolder {
        val binding =
            ItemListFilterHistorySheetBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return FilterHistorySheetViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FilterHistorySheetViewHolder,
        position: Int,
    ) {
        holder.bind(status[position], position == selectedPosition)
    }

    override fun getItemCount(): Int = status.size
}

interface OnFilterItemClickListener {
    fun onItemClick(status: StatusPayment)
}
