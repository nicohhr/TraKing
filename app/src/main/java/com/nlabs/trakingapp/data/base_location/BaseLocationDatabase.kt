package com.nlabs.trakingapp.data.base_location

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [BaseLocation::class],
    version = 1,
    exportSchema = false // TODO("Change it to true for serialization versioning")
)
@TypeConverters(Converters::class)
abstract class BaseLocationDatabase: RoomDatabase() {

    abstract fun dao(): BaseLocationDao

    companion object{
        @Volatile
        private var INSTANCE: BaseLocationDatabase? = null

        fun getDatabase(context: Context): BaseLocationDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BaseLocationDatabase::class.java,
                    "instantLocation_database"
                ).build()
                INSTANCE = instance
                return  instance
            }
        }
    }
}