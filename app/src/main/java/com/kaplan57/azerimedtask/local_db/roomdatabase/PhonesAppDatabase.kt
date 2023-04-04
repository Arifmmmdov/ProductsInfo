package com.kaplan57.azerimedtask.local_db.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kaplan57.azerimedtask.local_db.dao.PhonesDao
import com.kaplan57.azerimedtask.local_db.entity.PhonesEntity

@Database(entities = [PhonesEntity::class], version = 1)
abstract class PhonesAppDatabase : RoomDatabase() {

    abstract fun phonesDao(): PhonesDao

    companion object{

        @Volatile
        private var INSTANCE : PhonesAppDatabase? = null

        fun getDatabaseInstance(context:Context):PhonesAppDatabase{

            val templateInstance = INSTANCE
            if(templateInstance != null) return templateInstance

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    PhonesAppDatabase::class.java,
                    "app_database"
                )
                    .addMigrations()
                    .build()
                INSTANCE = instance
                return instance
            }

        }
    }
}