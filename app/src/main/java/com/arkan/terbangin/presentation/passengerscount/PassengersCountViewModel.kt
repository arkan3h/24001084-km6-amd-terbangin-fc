package com.arkan.terbangin.presentation.passengerscount

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PassengersCountViewModel : ViewModel() {
    val adultQty =
        MutableLiveData(0).apply {
            postValue(0)
        }
    val childrenQty =
        MutableLiveData(0).apply {
            postValue(0)
        }
    val babyQty =
        MutableLiveData(0).apply {
            postValue(0)
        }

    fun addQtyAdult() {
        val count = (adultQty.value ?: 0) + 1
        adultQty.postValue(count)
    }

    fun removeQtyAdult() {
        if ((adultQty.value ?: 0) > 0) {
            val count = (adultQty.value ?: 0) - 1
            adultQty.postValue(count)
        } else {
            adultQty.postValue(0)
        }
    }

    fun addQtyChildren() {
        val count = (childrenQty.value ?: 0) + 1
        childrenQty.postValue(count)
    }

    fun removeQtyChildren() {
        if ((childrenQty.value ?: 0) > 0) {
            val count = (childrenQty.value ?: 0) - 1
            childrenQty.postValue(count)
        } else {
            childrenQty.postValue(0)
        }
    }

    fun addQtyBaby() {
        val count = (babyQty.value ?: 0) + 1
        babyQty.postValue(count)
    }

    fun removeQtyBaby() {
        if ((babyQty.value ?: 0) > 0) {
            val count = (babyQty.value ?: 0) - 1
            babyQty.postValue(count)
        } else {
            babyQty.postValue(0)
        }
    }
}
