package com.arkan.terbangin.presentation.filter_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arkan.terbangin.databinding.LayoutItemFilterListBinding
import com.arkan.terbangin.model.FilterList

class FilterListAdapter(
    private val filterLists: List<FilterList>,
    private val listener: OnClassItemClickListener
) : RecyclerView.Adapter<FilterListAdapter.FilterListViewHolder>() {

    private var selectedPosition = -1

    inner class FilterListViewHolder(private val binding: LayoutItemFilterListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(filterList: FilterList, isSelected: Boolean) {
            binding.rbItemClassFilterList.text = filterList.nameFilter
            binding.tvItemClassFilterList.text = filterList.listFilter
            binding.rbItemClassFilterList.isChecked = isSelected
            binding.root.setOnClickListener {
                listener.onItemClick(filterList)
                notifyItemChanged(selectedPosition)
                selectedPosition = adapterPosition
                notifyItemChanged(selectedPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterListViewHolder {
        val binding = LayoutItemFilterListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterListViewHolder, position: Int) {
        holder.bind(filterLists[position], position == selectedPosition)
    }

    override fun getItemCount(): Int = filterLists.size
}

interface OnClassItemClickListener {
    fun onItemClick(filterList: FilterList)
}
