package com.example.cardDataObserverTestApplication.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cardDataObserverTestApplication.data.CardDataDatabase
import com.example.cardDataObserverTestApplication.data.CardDataDatabaseDao
import com.example.cardDataObserverTestApplication.data.CardDataDatabaseEntity
import com.example.cardDataObserverTestApplication.model.Bank
import com.example.cardDataObserverTestApplication.model.CardData
import com.example.cardDataObserverTestApplication.model.Country
import com.example.cardDataObserverTestApplication.model.Number
import com.example.cardDataObserverTestApplication.network.ApiService
import com.example.cardDataObserverTestApplication.utils.DataConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val apiService: ApiService = ApiService.invoke()
    private val dataConverter = DataConverter.getInstance()
    private var cardLiveData: MutableLiveData<CardData?> = MutableLiveData()
    private var requestHistoryLiveData: MutableLiveData<List<CardDataDatabaseEntity>?> = MutableLiveData()

    fun getCardData(): MutableLiveData<CardData?> {
        return cardLiveData
    }

    fun getDataFromDatabase(): MutableLiveData<List<CardDataDatabaseEntity>?> {
        return requestHistoryLiveData
    }

    fun loadCardData(cardNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            cardLiveData.postValue(cardDataExecutor(cardNumber))
        }
    }

    fun loadRequestHistory(database: CardDataDatabaseDao?) {
            requestHistoryLiveData.postValue(database?.getAll())
    }

    private suspend fun cardDataExecutor(cardNumber: String): CardData {
        try {
            return apiService.getCardData(cardNumber)
        } catch (exception: Exception) {
            when (exception.message) {
                "HTTP 400" -> println("400 - Card not found")
                "HTTP 429" -> println("429 - Too many requests")
                "HTTP 404" -> println("404 - Server is not responding")
            }

            return CardData(
                number = Number(
                    length = 0,
                    luhn = false
                ),
                scheme = "?",
                type = "?",
                brand = "?",
                prepaid = false,
                country = Country(
                    numeric = "?",
                    alpha2 = "?",
                    name = "?",
                    emoji = "?",
                    currency = "?",
                    latitude = 0,
                    longitude = 0
                ),
                bank = Bank(
                    name = "?",
                    url = "?",
                    phone = "?",
                    city = "?"
                )
            )
        }
    }
}