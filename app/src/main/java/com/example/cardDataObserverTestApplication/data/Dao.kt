package com.example.cardDataObserverTestApplication.data

import androidx.room.*

@Dao
interface CardDataDatabaseDao {
    @Query("SELECT * FROM cardDataDatabaseEntity ORDER BY insertTimeMark DESC")
    fun getAll(): List<CardDataDatabaseEntity>

    @Query("SELECT * FROM cardDataDatabaseEntity WHERE cardNumberEntity = (:cardNumber)")
    fun findByNumber(cardNumber: String): CardDataDatabaseEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cardDataDatabaseEntity: CardDataDatabaseEntity)

    @Query("DELETE FROM cardDataDatabaseEntity")
    fun deleteAllNotes()
}