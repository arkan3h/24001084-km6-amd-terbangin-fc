package com.arkan.terbangin.data.mapper

import com.arkan.terbangin.data.model.Airport
import com.arkan.terbangin.data.source.network.model.airport.AirportData

fun AirportData?.toAirport() =
    Airport(
        city = this?.city.orEmpty(),
        continent = this?.continent.orEmpty(),
        country = this?.country.orEmpty(),
        code = this?.iataCode.orEmpty(),
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        picture = this?.picture.orEmpty(),
        terminal = this?.terminal.orEmpty(),
    )

fun Collection<AirportData>?.toAirport() =
    this?.map {
        it.toAirport()
    } ?: listOf()
