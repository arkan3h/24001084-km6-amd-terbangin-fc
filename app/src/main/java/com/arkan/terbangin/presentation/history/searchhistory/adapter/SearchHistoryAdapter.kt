package com.arkan.terbangin.presentation.history.searchhistory.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arkan.terbangin.R
import com.arkan.terbangin.data.source.local.entity.SearchHistoryEntity

class SearchHistoryAdapter(
    private var searchHistoryEntityList: List<SearchHistoryEntity>,
    private val onDeleteClick: (SearchHistoryEntity) -> Unit,
) : RecyclerView.Adapter<SearchHistoryAdapter.HistorySearchViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HistorySearchViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history_search, parent, false)
        return HistorySearchViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: HistorySearchViewHolder,
        position: Int,
    ) {
        val searchHistory = searchHistoryEntityList[position]
        holder.bind(searchHistory)
    }

    override fun getItemCount(): Int = searchHistoryEntityList.size

    fun updateData(newSearchHistoryEntityList: List<SearchHistoryEntity>) {
        searchHistoryEntityList = newSearchHistoryEntityList
        notifyDataSetChanged()
    }

    inner class HistorySearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvHistory: TextView = itemView.findViewById(R.id.tv_history)
        private val ivClear: ImageView = itemView.findViewById(R.id.iv_terminal_history_clear)

        fun bind(searchHistoryEntity: SearchHistoryEntity) {
            tvHistory.text = searchHistoryEntity.query
            ivClear.setOnClickListener {
                onDeleteClick(searchHistoryEntity)
            }
        }
    }
}
