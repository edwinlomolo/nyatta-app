package com.example.nyatta.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nyatta.data.daos.UserDao
import com.example.nyatta.data.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class NyattaDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var Instance: NyattaDatabase? = null

        fun getDatabase(context: Context): NyattaDatabase {
            return Instance ?: synchronized(this) {
                Room
                    .databaseBuilder(context, NyattaDatabase::class.java, "user_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}