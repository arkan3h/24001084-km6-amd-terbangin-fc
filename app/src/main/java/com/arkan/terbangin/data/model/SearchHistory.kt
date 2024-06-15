package com.arkan.terbangin.data.model

data class SearchHistory(
    val id: Int? = null,
    val query: String,
    val timestamp: Long = System.currentTimeMillis(),
)
