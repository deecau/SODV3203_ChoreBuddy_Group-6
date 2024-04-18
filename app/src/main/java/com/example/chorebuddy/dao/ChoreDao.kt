package com.example.chorebuddy.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.chorebuddy.model.ChoreBean
import kotlinx.coroutines.flow.Flow

@Dao
interface ChoreDao {
    @Query("select * from chore_table")
    fun queryAll():Flow<List<ChoreBean>>

    @Query("select * from chore_table where memberId=:memberId")
    fun queryByMemberId(memberId:Int):Flow<List<ChoreBean>>

    @Query("select * from chore_table where id=:id")
    fun queryById(id:Int):Flow<ChoreBean>

    @Insert
    fun insert(choreBean: ChoreBean):Long


    @Update
    fun update(choreBean: ChoreBean?)

    @Delete
    fun delete(choreBean: ChoreBean)

    @Query("select* from chore_table where finished=1 order by finishDate desc")
    fun getFinishedChores(): Flow<List<ChoreBean>>

    @Query("select* from chore_table where finished=0")
    fun getUnFinishedChores(): Flow<List<ChoreBean>>
}