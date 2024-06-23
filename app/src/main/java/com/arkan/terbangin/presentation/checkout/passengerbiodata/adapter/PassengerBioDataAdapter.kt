package com.arkan.terbangin.presentation.checkout.passengerbiodata.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.data.model.PassengerBioData
import com.arkan.terbangin.data.model.PassengerForm
import com.arkan.terbangin.databinding.ActivityPassengerBiodataBinding
import com.arkan.terbangin.databinding.ItemPassengerBiodataBinding

class PassengerBioDataAdapter(
    private val params: FlightSearchParams,
    private val userId: String,
    private val activityBinding: ActivityPassengerBiodataBinding,
) : RecyclerView.Adapter<PassengerBioDataViewHolder>() {
    private val biodataList: List<PassengerForm> = generateBioDataList(params)
    private val viewHolders = mutableListOf<PassengerBioDataViewHolder>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PassengerBioDataViewHolder {
        val viewHolder =
            PassengerBioDataViewHolder(
                activityBinding,
                ItemPassengerBiodataBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
                userId,
            )
        viewHolders.add(viewHolder) // Add this line
        return viewHolder
    }

    override fun getItemCount(): Int = biodataList.size

    override fun onBindViewHolder(
        holder: PassengerBioDataViewHolder,
        position: Int,
    ) {
        holder.bind(biodataList[position])
    }

    override fun onViewRecycled(holder: PassengerBioDataViewHolder) {
        super.onViewRecycled(holder)
        viewHolders.remove(holder) // Remove the recycled viewholder
    }

    private fun generateBioDataList(params: FlightSearchParams): List<PassengerForm> {
        val biodataList = mutableListOf<PassengerForm>()
        var id = 1

        repeat(params.adultQty) {
            biodataList.add(PassengerForm(id++, "Dewasa"))
        }
        repeat(params.childrenQty) {
            biodataList.add(PassengerForm(id++, "Anak-anak"))
        }
        repeat(params.babyQty) {
            biodataList.add(PassengerForm(id++, "Bayi"))
        }

        return biodataList
    }

    fun getAllData(): List<PassengerBioData> {
        return viewHolders.map {
            it.getData()
        }
    }
}
