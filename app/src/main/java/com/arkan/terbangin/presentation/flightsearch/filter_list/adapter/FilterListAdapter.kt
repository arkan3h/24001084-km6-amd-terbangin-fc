package com.arkan.terbangin.presentation.flightsearch.filter_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arkan.terbangin.R
import com.arkan.terbangin.databinding.LayoutItemFilterListBinding
import com.arkan.terbangin.model.FilterList

class FilterListAdapter(
    private val filterLists: List<FilterList>,
    private val listener: OnClassItemClickListener,
) : RecyclerView.Adapter<FilterListAdapter.FilterListViewHolder>() {
    private var selectedPosition = -1

    inner class FilterListViewHolder(private val binding: LayoutItemFilterListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            filterList: FilterList,
            isSelected: Boolean,
        ) {
            binding.tvItemClassFilterList.text = filterList.nameFilter
            binding.tvItemFilterList.text = filterList.listFilter
            if (isSelected) {
                binding.ivCheckmark.visibility = View.VISIBLE
                binding.tvItemClassFilterList.setTextColor(itemView.context.getColor(R.color.md_theme_onPrimary_highContrast))
                binding.tvItemFilterList.setTextColor(itemView.context.getColor(R.color.md_theme_onPrimary_highContrast))
                itemView.setBackgroundResource(R.drawable.bg_normal_primary)
            } else {
                binding.ivCheckmark.visibility = View.GONE
                binding.tvItemClassFilterList.setTextColor(itemView.context.getColor(R.color.md_theme_onSurface_highContrast))
                binding.tvItemFilterList.setTextColor(itemView.context.getColor(R.color.md_theme_primary))
                itemView.setBackgroundResource(R.drawable.bg_normal_transparent)
            }
            binding.root.setOnClickListener {
                listener.onItemClick(filterList)
                notifyItemChanged(selectedPosition)
                selectedPosition = adapterPosition
                notifyItemChanged(selectedPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FilterListViewHolder {
        val binding = LayoutItemFilterListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FilterListViewHolder,
        position: Int,
    ) {
        holder.bind(filterLists[position], position == selectedPosition)
    }

    override fun getItemCount(): Int = filterLists.size
}

interface OnClassItemClickListener {
    fun onItemClick(filterList: FilterList)
}
