package com.example.nyatta.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nyatta.data.daos.TokenDao
import com.example.nyatta.data.model.Token

@Database(entities = [Token::class], version = 1, exportSchema = false)
abstract class NyattaDatabase: RoomDatabase() {
    abstract fun tokenDao(): TokenDao

    companion object {
        @Volatile
        private var Instance: NyattaDatabase? = null

        fun getDatabase(context: Context): NyattaDatabase {
            return Instance ?: synchronized(this) {
                Room
                    .databaseBuilder(context, NyattaDatabase::class.java, "nyatta_db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}