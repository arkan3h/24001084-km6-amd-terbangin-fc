package com.arkan.terbangin.presentation.home.common

import com.arkan.terbangin.data.model.StatusPayment

interface FilterStatusListener {
    fun onFilterStatusSelected(status: StatusPayment)

    fun onSearch(query: String)
}
