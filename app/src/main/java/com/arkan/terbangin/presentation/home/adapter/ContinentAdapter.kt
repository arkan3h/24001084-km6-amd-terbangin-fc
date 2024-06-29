package com.arkan.terbangin.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arkan.terbangin.R
import com.arkan.terbangin.base.OnItemCLickedListener
import com.arkan.terbangin.data.model.ContinentList
import com.arkan.terbangin.databinding.ItemHomeContinentBinding

class ContinentAdapter(
    private val continent: List<ContinentList>,
    private val listener: OnItemCLickedListener<ContinentList>,
) : RecyclerView.Adapter<ContinentAdapter.FlightRecommendationViewHolder>() {
    private var selectedPosition = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FlightRecommendationViewHolder {
        val binding = ItemHomeContinentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlightRecommendationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FlightRecommendationViewHolder,
        position: Int,
    ) {
        holder.bind(continent[position], position == selectedPosition)
    }

    override fun getItemCount(): Int = continent.size

    inner class FlightRecommendationViewHolder(
        private val binding: ItemHomeContinentBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            continent: ContinentList,
            isSelected: Boolean,
        ) {
            binding.textContinent.text = continent.titleContinent
            if (isSelected) {
                binding.constraint.setBackgroundResource(R.drawable.bg_normal_primary)
                binding.ivSearch.setColorFilter(itemView.context.getColor(R.color.md_theme_onPrimary_highContrast))
                binding.textContinent.setTextColor(itemView.context.getColor(R.color.md_theme_onPrimary_highContrast))
            } else {
                binding.constraint.setBackgroundResource(R.drawable.bg_normal_transparent)
                binding.ivSearch.setColorFilter(itemView.context.getColor(R.color.md_theme_onSurface))
                binding.textContinent.setTextColor(itemView.context.getColor(R.color.md_theme_onSurface))
            }
            binding.root.setOnClickListener {
                listener.onItemClicked(continent)
                notifyItemChanged(selectedPosition)
                val newPosition = bindingAdapterPosition
                if (newPosition != RecyclerView.NO_POSITION) {
                    selectedPosition = newPosition
                    notifyItemChanged(selectedPosition)
                }
            }
        }
    }
}
