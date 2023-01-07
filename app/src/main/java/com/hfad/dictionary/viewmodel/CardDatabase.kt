package com.hfad.dictionary.viewmodel

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hfad.dictionary.models.card.Card

@Database(entities = [Card::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class CardDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDAO

    companion object {

        @Volatile
        private var INSTANCE: CardDatabase? = null

        fun getDatabase(context: Context): CardDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CardDatabase::class.java,
                    "todo_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}