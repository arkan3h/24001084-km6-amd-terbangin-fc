package com.arkan.terbangin.presentation.history.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arkan.terbangin.R
import com.arkan.terbangin.data.model.SearchHistory

class SearchHistoryAdapter(
    private var searchHistoryList: List<SearchHistory>,
    private val onDeleteClick: (SearchHistory) -> Unit
) : RecyclerView.Adapter<SearchHistoryAdapter.HistorySearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorySearchViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history_search, parent, false)
        return HistorySearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistorySearchViewHolder, position: Int) {
        val searchHistory = searchHistoryList[position]
        holder.bind(searchHistory)
    }

    override fun getItemCount(): Int = searchHistoryList.size

    fun updateData(newSearchHistoryList: List<SearchHistory>) {
        searchHistoryList = newSearchHistoryList
        notifyDataSetChanged()
    }

    inner class HistorySearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvHistory: TextView = itemView.findViewById(R.id.tv_history)
        private val ivClear: ImageView = itemView.findViewById(R.id.iv_terminal_history_clear)

        fun bind(searchHistory: SearchHistory) {
            tvHistory.text = searchHistory.query
            ivClear.setOnClickListener {
                onDeleteClick(searchHistory)
            }
        }
    }
}
