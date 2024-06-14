package com.arkan.terbangin.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class PassengerBioDataList(
    var list: List<PassengerBioData>,
) : Parcelable

@Parcelize
data class PassengerBioData(
    var id: String? = UUID.randomUUID().toString(),
    var userId: String,
    var type: String,
    var title: String,
    var fullName: String,
    var familyName: String?,
    val birthDay: String?,
    var nationality: String,
    var identityId: String,
    var issuingCountry: String,
) : Parcelable
