package com.arkan.terbangin.presentation.class_sheet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arkan.terbangin.data.model.TicketClass

class ClassSheetAdapter {

    /*private val ticketClasses: List<TicketClass>,
    private val onItemClickListener: OnClassItemClickListener
) : RecyclerView.Adapter<ClassSheetAdapter.ClassSheetViewHolder>() {

    private var selectedItemPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassSheetViewHolder {
        val binding = ItemClassSheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClassSheetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClassSheetViewHolder, position: Int) {
        val ticketClass = ticketClasses[position]
        holder.bind(ticketClass, position == selectedItemPosition)
    }

    override fun getItemCount(): Int {
        return ticketClasses.size
    }

    inner class ClassSheetViewHolder(private val binding: ItemClassSheetBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ticketClass: TicketClass, isSelected: Boolean) {
            binding.apply {
                tvClassName.text = ticketClass.name
                tvClassPrice.text = ticketClass.price
                root.isSelected = isSelected
                root.setOnClickListener {
                    val previousPosition = selectedItemPosition
                    selectedItemPosition = adapterPosition
                    notifyItemChanged(previousPosition)
                    notifyItemChanged(selectedItemPosition)
                    onItemClickListener.onItemClick(ticketClass)
                }
            }
        }
    }

    fun getSelectedTicketClass(): TicketClass? {
        return if (selectedItemPosition != RecyclerView.NO_POSITION) {
            ticketClasses[selectedItemPosition]
        } else {
            null
        }
    }*/

}