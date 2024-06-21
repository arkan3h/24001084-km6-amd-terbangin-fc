package com.arkan.terbangin.data.repository.seat

import com.arkan.terbangin.data.datasource.seat.SeatDataSource
import com.arkan.terbangin.data.mapper.toSeat
import com.arkan.terbangin.data.model.Seat
import com.arkan.terbangin.utils.ResultWrapper
import com.arkan.terbangin.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface SeatRepository {
    fun getSeat(
        id: String,
        seatClass: String,
    ): Flow<ResultWrapper<List<Seat>>>
}

class SeatRepositoryImpl(
    private val dataSource: SeatDataSource,
) : SeatRepository {
    override fun getSeat(
        id: String,
        seatClass: String,
    ): Flow<ResultWrapper<List<Seat>>> {
        return proceedFlow {
            dataSource.getSeat(id).data.toSeat().filter {
                it.airlineClass.contains(seatClass)
            }
        }
    }
}
