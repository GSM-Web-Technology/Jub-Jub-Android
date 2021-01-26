package com.example.jup_jup_android.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jup_jup_android.entity.dataclass.RentStatus

@Dao
interface RentStatusDAO {

    @Query("SELECT * FROM rentStatus")
    fun getAll(): List<RentStatus>

    @Query("SELECT * FROM rentStatus WHERE status LIKE :word ")
    fun search(word: String): List<RentStatus>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rentStatus: RentStatus)

    @Query("DELETE from rentStatus")
    fun clear()


}