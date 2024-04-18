package com.example.chorebuddy.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chorebuddy.ChoreApplication
import com.example.chorebuddy.db.ChoreDatabase
import com.example.chorebuddy.model.ChoreBean
import com.example.chorebuddy.repository.ChoreRepository
import kotlinx.coroutines.flow.Flow

class ChoreViewModel(private val choreRepository: ChoreRepository): ViewModel() {



    // Function to add a chore to the database
    suspend fun addChore(chore: ChoreBean):Long {

        return choreRepository.insert(chore)
    }


    // Function to update a chore in the database
    suspend fun updateChore(chore: ChoreBean?) {
        choreRepository.update(chore)

    }


    // Function to delete a chore from the database
    suspend fun deleteChore(chore: ChoreBean) {
        choreRepository.delete(chore)

    }

    // Function to get a chore from the database by its ID
    fun getChore(id: Int):Flow<ChoreBean> {
        return choreRepository.getChoreById(id)
    }


    // Function to get a list of finished chores from the database
    fun getFinishedChores(): Flow<List<ChoreBean>> {
        return choreRepository.getFinishedChores()
    }

    // Query to get a list of unfinished chores directly from the repository
    val queryUnFinishedChores = choreRepository.getUnFinishedChores()
}