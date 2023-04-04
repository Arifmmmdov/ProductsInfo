package com.kaplan57.azerimedtask.local_db.dao

import androidx.room.*
import com.kaplan57.azerimedtask.local_db.entity.PhonesEntity


@Dao
interface PhonesDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertData(phonesEntity: PhonesEntity):Void

    @Query("SELECT * FROM phones_table")
    fun getAll(): List<PhonesEntity>

    @Query("SELECT * FROM phones_table WHERE phonesName like '%'||:text||'%' or phonesDescription like '%'||:text||'%'")
    fun getResearchedData(text:String):List<PhonesEntity>

    @Query("DELETE FROM phones_table")
    fun deleteAll():Int

//    @Delete(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun delete(phonesEntity: PhonesEntity)
//
//    @Query("DELETE FROM phones_table")
//    suspend fun deleteAll()

}