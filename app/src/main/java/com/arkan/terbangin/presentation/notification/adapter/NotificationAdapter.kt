package com.arkan.terbangin.presentation.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arkan.terbangin.base.OnItemCLickedListener
import com.arkan.terbangin.data.model.Notification
import com.arkan.terbangin.databinding.CardItemNotificationBinding
import com.arkan.terbangin.utils.formatDateNotification

class NotificationAdapter(
    private val listener: OnItemCLickedListener<Notification>,
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
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
            listener,
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
        private val listener: OnItemCLickedListener<Notification>,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Notification) {
            with(item) {
                binding.tvTitleNotification.text = title
                binding.tvNotification.text = message
                binding.tvDate.text = formatDateNotification(createdAt)
                itemView.setOnClickListener { listener.onItemClicked(item) }
            }
        }
    }
}
