package com.arkan.terbangin.data.model

data class Notification(
    var id: String,
    var userId: String,
    var bookingId: String,
    var title: String,
    var message: String,
    var statusRead: Boolean,
    var deletedAt: String,
    var createdAt: String,
    var updatedAt: String,
)
