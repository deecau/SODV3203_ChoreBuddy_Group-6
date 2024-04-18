package com.example.chorebuddy.repository

import com.example.chorebuddy.dao.ChoreDao
import com.example.chorebuddy.model.ChoreBean
import kotlinx.coroutines.flow.Flow

interface ChoreRepository {

    fun insert(choreBean: ChoreBean):Long

    fun update(choreBean: ChoreBean?)

    fun delete(choreBean: ChoreBean)

    fun getAll(): Flow<List<ChoreBean>>
    fun getFinishedChores(): Flow<List<ChoreBean>>
    fun getChoreById(id: Int):Flow<ChoreBean>
    fun getUnFinishedChores():Flow<List<ChoreBean>>

}

class ChoreDataRepository(private val choreDao: ChoreDao) : ChoreRepository {
    override fun insert(choreBean: ChoreBean):Long {
        return choreDao.insert(choreBean)
    }

    override fun update(choreBean: ChoreBean?) {
        choreDao.update(choreBean)
    }

    override fun delete(choreBean: ChoreBean) {
        choreDao.delete(choreBean)
    }

    override fun getAll(): Flow<List<ChoreBean>> {
        return choreDao.queryAll()
    }

    override fun getFinishedChores(): Flow<List<ChoreBean>> {
        return choreDao.getFinishedChores()
    }

    override fun getChoreById(id: Int):Flow<ChoreBean> {
        return choreDao.queryById(id)
    }

    override fun getUnFinishedChores(): Flow<List<ChoreBean>> {

        return choreDao.getUnFinishedChores()
    }


}