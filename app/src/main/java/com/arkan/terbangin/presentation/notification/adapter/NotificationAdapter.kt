package com.arkan.terbangin.presentation.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arkan.terbangin.data.model.Notification
import com.arkan.terbangin.databinding.CardItemNotificationBinding

class NotificationAdapter(private val itemClick: (Notification) -> Unit) :
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    private val asyncDataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Notification>() {
                override fun areItemsTheSame(
                    oldItem: Notification,
                    newItem: Notification,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Notification,
                    newItem: Notification,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<Notification>) {
        asyncDataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): NotificationViewHolder {
        return NotificationViewHolder(
            CardItemNotificationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            itemClick,
        )
    }

    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: NotificationViewHolder,
        position: Int,
    ) {
        holder.bind(asyncDataDiffer.currentList[position])
    }

    class NotificationViewHolder(
        private val binding: CardItemNotificationBinding,
        val itemClick: (Notification) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Notification) {
            with(item) {
                binding.tvTitleNotification.text = title
                binding.tvNotification.text = message
                binding.tvDate.text = createdAt
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}
