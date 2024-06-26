package com.arkan.terbangin.presentation.history.searchhistory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arkan.terbangin.base.OnItemCLickedListener
import com.arkan.terbangin.data.model.SearchHistory
import com.arkan.terbangin.databinding.ItemTerminalHistoryBinding

class SearchHistoryAdapter(
    private val listener: OnItemCLickedListener<SearchHistory>,
    private val historyListener: HistoryListener? = null,
) : RecyclerView.Adapter<SearchHistoryAdapter.HistoryViewHolder>() {
    private val asyncDataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<SearchHistory>() {
                override fun areItemsTheSame(
                    oldItem: SearchHistory,
                    newItem: SearchHistory,
                ): Boolean {
                    return oldItem.query == newItem.query
                }

                override fun areContentsTheSame(
                    oldItem: SearchHistory,
                    newItem: SearchHistory,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(items: List<SearchHistory>) {
        asyncDataDiffer.submitList(items)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HistoryViewHolder {
        return HistoryViewHolder(
            ItemTerminalHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            listener,
            historyListener,
        )
    }

    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: HistoryViewHolder,
        position: Int,
    ) {
        holder.bind(asyncDataDiffer.currentList[position])
    }

    class HistoryViewHolder(
        private val binding: ItemTerminalHistoryBinding,
        private val listener: OnItemCLickedListener<SearchHistory>,
        private val historyListener: HistoryListener?,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchHistory) {
            binding.tvTerminal.text = item.query
            binding.ivTerminalHistoryClear.setOnClickListener {
                historyListener?.deleteHistory(item)
            }
            itemView.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }
}

interface HistoryListener {
    fun deleteHistory(item: SearchHistory)
}
