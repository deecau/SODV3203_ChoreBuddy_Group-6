package com.example.chorebuddy.repository

import android.util.Log
import com.example.chorebuddy.dao.MemberDao
import com.example.chorebuddy.model.MemberBean
import kotlinx.coroutines.flow.Flow

interface MemberRepository {

    suspend fun insert(member:MemberBean):Long

    fun update(member: MemberBean)

    suspend fun delete(member:MemberBean)

    fun getAll():Flow<List<MemberBean>>
    fun getById(id: Int?): Flow<MemberBean>
}

class MemberDataRepository(private val memberDao: MemberDao):MemberRepository{
    override suspend fun insert(member: MemberBean):Long {
       return memberDao.insert(member)
    }

    override fun update(member: MemberBean) {
        memberDao.update(member)
    }

    override suspend fun delete(member: MemberBean) {
        memberDao.delete(member)
    }

    override fun getAll(): Flow<List<MemberBean>> {
        val members = memberDao.getAllMember()
        return members
    }

    override fun getById(id: Int?): Flow<MemberBean> {
        return memberDao.getMember(id)
    }

}