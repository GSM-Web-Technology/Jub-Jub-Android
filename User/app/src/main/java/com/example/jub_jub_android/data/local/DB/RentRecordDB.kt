package com.example.jub_jub_android.data.local.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jub_jub_android.data.local.DAO.RentRecordDAO
import com.example.jub_jub_android.entity.dataclass.RentRecord


@Database(entities = [RentRecord::class], version = 1)
abstract class RentRecordDB: RoomDatabase() {

    abstract fun rentRecordDAO(): RentRecordDAO

    companion object{
        private var INSTANCE: RentRecordDB? = null

        fun getInstance(context: Context): RentRecordDB? {
            if (INSTANCE == null) {
                synchronized(RentRecordDB::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        RentRecordDB::class.java, "rentRecord.db")
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