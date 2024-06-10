package com.arkan.terbangin.data.mapper

import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.source.network.model.flight.FlightDataResponse

fun FlightDataResponse?.toFlight() =
    Flight(
        airlineAdditionals = this?.airline?.additionals.orEmpty(),
        airlineBaggage = this?.airline?.baggage ?: 0,
        airlineCabinBaggage = this?.airline?.cabinBaggage ?: 0,
        airlineName = this?.airline?.name.orEmpty(),
        airlinePicture = this?.airline?.picture.orEmpty(),
        airlineSerialNumber = this?.airline?.serialNumber.orEmpty(),
        airlineId = this?.airline?.id.orEmpty(),
        arrivalAt = this?.arrivalAt.orEmpty(),
        capacityBussines = this?.capacityBussines ?: 0,
        capacityEconomy = this?.capacityEconomy ?: 0,
        capacityFirstClass = this?.capacityFirstClass ?: 0,
        departureAt = this?.departureAt.orEmpty(),
        duration = this?.duration ?: 0,
        endAirportCity = this?.endAirport?.city.orEmpty(),
        endAirportContinent = this?.endAirport?.continent.orEmpty(),
        endAirportCountry = this?.endAirport?.country.orEmpty(),
        endAirportName = this?.endAirport?.name.orEmpty(),
        endAirportPicture = this?.endAirport?.picture.orEmpty(),
        endAirportTerminal = this?.endAirport?.terminal.orEmpty(),
        endAirportId = this?.endAirport?.id.orEmpty(),
        id = this?.id.orEmpty(),
        priceBussines = this?.priceBussines ?: 0,
        priceEconomy = this?.priceEconomy ?: 0,
        priceFirstClass = this?.priceFirstClass ?: 0,
        startAirportCity = this?.startAirport?.city.orEmpty(),
        startAirportContinent = this?.startAirport?.continent.orEmpty(),
        startAirportCountry = this?.startAirport?.country.orEmpty(),
        startAirportName = this?.startAirport?.name.orEmpty(),
        startAirportPicture = this?.startAirport?.picture.orEmpty(),
        startAirportTerminal = this?.startAirport?.terminal.orEmpty(),
        startAirportId = this?.startAirport?.id.orEmpty(),
    )

fun Collection<FlightDataResponse>?.toFlight() =
    this?.map {
        it.toFlight()
    } ?: listOf()
