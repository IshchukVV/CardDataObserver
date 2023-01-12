package com.example.cardDataObserverTestApplication.utils

import com.example.cardDataObserverTestApplication.data.CardDataDatabase
import com.example.cardDataObserverTestApplication.data.CardDataDatabaseDao
import com.example.cardDataObserverTestApplication.data.CardDataDatabaseEntity
import com.example.cardDataObserverTestApplication.model.Bank
import com.example.cardDataObserverTestApplication.model.CardData
import com.example.cardDataObserverTestApplication.model.Country
import com.example.cardDataObserverTestApplication.model.Number
import java.time.LocalDateTime

class DataConverter {
    private var convertedDataSet: CardDataDatabaseEntity = CardDataDatabaseEntity(
        cardNumberEntity = "?",
        insertTimeMark = "?",
        numberLength = 0,
        numberLuhn = false,
        scheme = "?",
        type = "?",
        brand = "?",
        prepaid = false,
        countryNumeric = "?",
        countryAlpha2 = "?",
        countryName = "?",
        countryEmoji = "?",
        countryCurrency = "?",
        countryLatitude = 0,
        countryLongitude = 0,
        bankName = "?",
        bankUrl = "?",
        bankPhone = "?",
        bankCity = "?"
    )

    fun convertDataSet(cardData: CardData?, cardNumber: String, now: LocalDateTime): CardDataDatabaseEntity{
        convertedDataSet.cardNumberEntity = cardNumber
        convertedDataSet.insertTimeMark = now.toString()
        convertedDataSet.numberLength = cardData!!.number?.length
        convertedDataSet.numberLuhn = cardData.number?.luhn
        convertedDataSet.scheme = cardData.scheme
        convertedDataSet.type = cardData.type
        convertedDataSet.brand = cardData.brand
        convertedDataSet.prepaid = cardData.prepaid
        convertedDataSet.countryNumeric = cardData.country?.numeric
        convertedDataSet.countryAlpha2 = cardData.country?.alpha2
        convertedDataSet.countryName = cardData.country?.name
        convertedDataSet.countryEmoji = cardData.country?.emoji
        convertedDataSet.countryCurrency = cardData.country?.currency
        convertedDataSet.countryLatitude = cardData.country?.latitude
        convertedDataSet.countryLongitude = cardData.country?.longitude
        convertedDataSet.bankName = cardData.bank?.name
        convertedDataSet.bankUrl = cardData.bank?.url
        convertedDataSet.bankPhone = cardData.bank?.phone
        convertedDataSet.bankCity = cardData.bank?.city
        return convertedDataSet
    }

    fun getReconvertedDataSet(cardNumber: String, dataDatabase: CardDataDatabaseDao): CardData{
        convertedDataSet = dataDatabase.findByNumber(cardNumber)
        return CardData(
            number = Number(
                length = convertedDataSet.numberLength,
                luhn = convertedDataSet.numberLuhn
            ),
            scheme = convertedDataSet.scheme,
            type = convertedDataSet.type,
            brand = convertedDataSet.brand,
            prepaid = convertedDataSet.prepaid,
            country = Country(
                numeric = convertedDataSet.countryNumeric,
                alpha2 = convertedDataSet.countryAlpha2,
                name = convertedDataSet.bankName,
                emoji = convertedDataSet.countryEmoji,
                currency = convertedDataSet.countryCurrency,
                latitude = convertedDataSet.countryLatitude,
                longitude = convertedDataSet.countryLongitude
            ),
            bank = Bank(
                name = convertedDataSet.bankName,
                url = convertedDataSet.bankUrl,
                phone = convertedDataSet.bankPhone,
                city = convertedDataSet.bankCity
            )
        )
    }

    companion object {
        private var instance: DataConverter? = null

        @Synchronized
        fun getInstance(): DataConverter {
            if (instance == null)
                instance = DataConverter()
            return instance!!
        }
    }
}