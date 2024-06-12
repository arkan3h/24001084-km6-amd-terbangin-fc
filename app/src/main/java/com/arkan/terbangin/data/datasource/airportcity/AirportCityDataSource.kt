package com.arkan.terbangin.data.datasource.airportcity

import com.arkan.terbangin.data.model.AirportCity

interface AirportCityDataSource {
    suspend fun getAllAirportCity(): List<AirportCity>
}

class AirportCityDataSourceImpl() : AirportCityDataSource {
    override suspend fun getAllAirportCity(): List<AirportCity> {
        return mutableListOf(
            AirportCity(
                "Jakarta",
                "JKT",
            ),
            AirportCity(
                "Surabaya",
                "SBY",
            ),
            AirportCity(
                "Bandung",
                "BDG",
            ),
            AirportCity(
                "Yogyakarta",
                "DIY",
            ),
            AirportCity(
                "Makassar",
                "MKS",
            ),
            AirportCity(
                "Padang",
                "PDG",
            ),
            AirportCity(
                "Aceh",
                "ACH",
            ),
            AirportCity(
                "Bali",
                "BAL",
            ),
            AirportCity(
                "Balikpapan",
                "BLP",
            ),
            AirportCity(
                "Merauke",
                "MRK",
            ),
        )
    }
}
