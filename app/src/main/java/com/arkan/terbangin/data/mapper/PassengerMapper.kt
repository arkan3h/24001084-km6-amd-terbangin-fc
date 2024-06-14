package com.arkan.terbangin.data.mapper

import com.arkan.terbangin.data.model.PassengerBioData
import com.arkan.terbangin.data.source.network.model.passanger.PassengerPayload
import com.arkan.terbangin.data.source.network.model.passanger.PassengerResponse

fun PassengerResponse.toBioData() =
    PassengerBioData(
        id = this.data?.id,
        userId = this.data?.userId.orEmpty(),
        type = this.data?.type.orEmpty(),
        title = this.data?.title.orEmpty(),
        fullName = this.data?.fullName.orEmpty(),
        familyName = this.data?.familyName,
        birthDay = this.data?.birthDate.orEmpty(),
        nationality = this.data?.nationality.orEmpty(),
        identityId = this.data?.identityId.orEmpty(),
        issuingCountry = this.data?.issuingCountry.orEmpty(),
    )

fun PassengerBioData.toPayload() =
    PassengerPayload(
        userId = this.userId,
        title = this.title,
        type = this.type,
        fullName = this.fullName,
        familyName = this.familyName,
        birthDate = this.birthDay.orEmpty(),
        nationality = this.nationality,
        identityId = this.identityId,
        issuingCountry = this.issuingCountry,
    )
