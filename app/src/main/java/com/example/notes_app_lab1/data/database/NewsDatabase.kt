package com.example.notes_app_lab1.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes_app_lab1.data.dao.NewDao
import com.example.notes_app_lab1.data.entity.New

@Database(entities = arrayOf(New::class), version = 2)
abstract class NewsDatabase: RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getInstance(context: Context): NewsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "NewsDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun newsDao(): NewDao
}