package com.example.chorebuddy.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chorebuddy.ChoreApplication
import com.example.chorebuddy.db.ChoreDatabase
import com.example.chorebuddy.model.MemberBean
import com.example.chorebuddy.repository.ChoreRepository
import com.example.chorebuddy.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MemberViewModel(private val repository: MemberRepository) : ViewModel() {


    // Flow of all members from the repository
    val allMember = repository.getAll()

    // Function to delete a member from the database
    suspend fun deleteMember(member: MemberBean) {
        repository.delete(member)
    }


    // Function to insert a new member into the database
    suspend fun insertMember(member: MemberBean): Long {
        return repository.insert(member)
    }


    // Function to insert a new member into the database
    suspend fun updateMember(member: MemberBean) {
        repository.update(member)
    }

    // Function to get a member from the database by their ID
    fun getMember(id: Int?): Flow<MemberBean> {
        return repository.getById(id)
    }

}