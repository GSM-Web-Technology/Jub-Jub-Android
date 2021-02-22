package com.example.jub_jub_admin.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jub_jub_admin.data.local.dao.RentRequestDAO
import com.example.jub_jub_admin.entity.dataclass.RentRequest


@Database(entities = [RentRequest::class], version = 1, exportSchema = false)

abstract class RentRequestDB: RoomDatabase() {

    abstract fun rentRequestDAO(): RentRequestDAO

    companion object {
        private var INSTANCE: RentRequestDB? = null

        fun getInstance(context: Context): RentRequestDB? {
            if (INSTANCE == null) {
                synchronized(EquipmentDB::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        RentRequestDB::class.java, "rentRequest.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance() {
            INSTANCE = null
        }

    }

}