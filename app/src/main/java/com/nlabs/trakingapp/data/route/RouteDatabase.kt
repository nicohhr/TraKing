package com.nlabs.trakingapp.data.route

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Route::class],
    version=1,
    exportSchema = false
)
abstract class RouteDatabase: RoomDatabase() {

    abstract fun dao(): RouteDao

    companion object{
        @Volatile
        private var INSTANCE: RouteDatabase? = null

        fun getDatabase(context: Context): RouteDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RouteDatabase::class.java,
                    "routes_database"
                ).build()
                INSTANCE = instance
                return  instance
            }
        }
    }
}