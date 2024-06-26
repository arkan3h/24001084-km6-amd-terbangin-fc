package com.arkan.terbangin.presentation.home.class_sheet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arkan.terbangin.R
import com.arkan.terbangin.data.model.TicketClass
import com.arkan.terbangin.databinding.ItemListClassSheetBinding

class ClassSheetAdapter(
    private val ticketClasses: List<TicketClass>,
    private val listener: OnClassItemClickListener,
) : RecyclerView.Adapter<ClassSheetAdapter.ClassSheetViewHolder>() {
    private var selectedPosition = -1

    inner class ClassSheetViewHolder(private val binding: ItemListClassSheetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            ticketClass: TicketClass,
            isSelected: Boolean,
        ) {
            binding.tvItemClass.text = ticketClass.name
//            binding.tvItemClassPrice.text = ticketClass.price
            if (isSelected) {
                binding.ivCheckmark.visibility = View.VISIBLE
                binding.tvItemClass.setTextColor(itemView.context.getColor(R.color.md_theme_onPrimary_highContrast))
                itemView.setBackgroundResource(R.drawable.bg_normal_primary)
            } else {
                binding.ivCheckmark.visibility = View.GONE
                binding.tvItemClass.setTextColor(itemView.context.getColor(R.color.md_theme_onSurface_highContrast))
                itemView.setBackgroundResource(R.drawable.bg_normal_transparent)
            }
            binding.root.setOnClickListener {
                listener.onItemClick(ticketClass)
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
    ): ClassSheetViewHolder {
        val binding =
            ItemListClassSheetBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return ClassSheetViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ClassSheetViewHolder,
        position: Int,
    ) {
        holder.bind(ticketClasses[position], position == selectedPosition)
    }

    override fun getItemCount(): Int = ticketClasses.size
}

interface OnClassItemClickListener {
    fun onItemClick(ticketClass: TicketClass)
}
