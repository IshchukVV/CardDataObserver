package com.example.cardDataObserverTestApplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CardDataDatabaseEntity(
    @PrimaryKey
    var cardNumberEntity: String,
    var insertTimeMark: String,
    var numberLength: Int?,
    var numberLuhn: Boolean?,
    var scheme: String?,
    var type: String?,
    var brand: String?,
    var prepaid: Boolean?,
    var countryNumeric: String?,
    var countryAlpha2: String?,
    var countryName: String?,
    var countryEmoji: String?,
    var countryCurrency: String?,
    var countryLatitude: Int?,
    var countryLongitude: Int?,
    var bankName: String?,
    var bankUrl: String?,
    var bankPhone: String?,
    var bankCity: String?
)