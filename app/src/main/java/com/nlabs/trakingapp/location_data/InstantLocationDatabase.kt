package com.nlabs.trakingapp.location_data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [InstantLocation::class],
    version = 1,
    exportSchema = false // TODO("Change it to true for serialization versioning")
)
abstract class InstantLocationDatabase: RoomDatabase() {

    abstract fun dao(): InstantLocationDao

    companion object{
        @Volatile
        private var INSTANCE: InstantLocationDatabase? = null

        fun getDatabase(context: Context): InstantLocationDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InstantLocationDatabase::class.java,
                    "instantLocation_database"
                ).build()
                INSTANCE = instance
                return  instance
            }
        }
    }
}