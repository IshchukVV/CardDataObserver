package com.example.cardDataObserverTestApplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CardDataDatabaseEntity::class], version = 2)
abstract class CardDataDatabase : RoomDatabase() {

    abstract fun cardDataDatabaseDao(): CardDataDatabaseDao

    companion object {
        private var instance: CardDataDatabase? = null

        @Synchronized
        fun getInstance(context: Context): CardDataDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    CardDataDatabase::class.java,
                    "card_data_database"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            return instance!!
        }
    }
}