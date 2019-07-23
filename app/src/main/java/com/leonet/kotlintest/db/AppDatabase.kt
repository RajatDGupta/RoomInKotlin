package com.leonet.kotlintest.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.leonet.kotlintest.db.dao.ArticleDao
import com.leonet.kotlintest.model.Articles

@Database(entities = [Articles::class], version = 1  ,exportSchema = false)
 abstract class AppDatabase: RoomDatabase()  {

abstract fun articleDao():ArticleDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "AppDB+"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}