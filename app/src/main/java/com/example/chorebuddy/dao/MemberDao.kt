package com.example.chorebuddy.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.chorebuddy.model.MemberBean
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {

    @Query("SELECT * FROM member")
    fun getAllMember(): Flow<List<MemberBean>>

    @Query("SELECT * FROM member WHERE id = :id")
    fun getMember(id: Int?): Flow<MemberBean>


    @Query("select * from member")
    fun getMembers():List<MemberBean>
    @Insert
    suspend fun insert(member: MemberBean):Long

    @Update
    fun update(member: MemberBean)

    @Delete
    suspend fun delete(member: MemberBean)
}