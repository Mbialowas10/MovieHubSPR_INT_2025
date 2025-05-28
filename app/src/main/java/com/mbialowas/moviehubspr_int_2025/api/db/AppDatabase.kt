package com.mbialowas.moviehubspr_int_2025.api.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mbialowas.moviehubspr_int_2025.api.model.Movie

@Database(entities = [Movie::class], version=4, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun movieDao(): MovieDao

    companion object{
        // use the companion object to
        // implement the singleton pattern
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            return INSTANCE ?: synchronized (this){
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    AppDatabase::class.java,
                    "MovieHub SPR INT 2025"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }

    }

}