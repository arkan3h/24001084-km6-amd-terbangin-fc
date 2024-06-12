package com.arkan.terbangin.presentation.home.passengers_count

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class PassengersCountViewModel : ViewModel() {
    val adultQty =
        MutableLiveData<Int>().apply {
            postValue(0)
        }
    val childrenQty =
        MutableLiveData<Int>().apply {
            postValue(0)
        }
    val babyQty =
        MutableLiveData<Int>().apply {
            postValue(0)
        }

    val totalQty: LiveData<Int> =
        MediatorLiveData<Int>().apply {
            val observer =
                Observer<Int> {
                    value = (adultQty.value ?: 0) + (childrenQty.value ?: 0) + (babyQty.value ?: 0)
                }
            addSource(adultQty, observer)
            addSource(childrenQty, observer)
            addSource(babyQty, observer)
        }

    init {
        if (adultQty.value == null) adultQty.value = 0
        if (childrenQty.value == null) childrenQty.value = 0
        if (babyQty.value == null) babyQty.value = 0
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
