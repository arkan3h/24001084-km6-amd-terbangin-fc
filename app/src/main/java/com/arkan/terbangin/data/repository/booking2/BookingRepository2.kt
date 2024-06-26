// package com.arkan.terbangin.data.repository.booking2
//
// import com.arkan.terbangin.data.datasource.booking2.BookingDataSource2
// import com.arkan.terbangin.data.model.Booking2
// import com.arkan.terbangin.utils.ResultWrapper
// import com.arkan.terbangin.utils.proceedFlow
// import kotlinx.coroutines.flow.Flow
//
// interface BookingRepository2 {
//    fun createBooking(booking: Booking2) : Flow<ResultWrapper<Booking2>>
// }
//
// class BookingRepositoryImpl2(
//    private val dataSource: BookingDataSource2
// ): BookingRepository2 {
//    override fun createBooking(booking: Booking2): Flow<ResultWrapper<Booking2>> {
//        return proceedFlow {
//            dataSource.createBooking(booking.)
//        }
//    }
//
// }
