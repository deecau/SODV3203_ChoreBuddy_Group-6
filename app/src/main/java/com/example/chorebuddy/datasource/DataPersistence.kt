
package com.example.chorebuddy.datasource

import android.content.Context
import android.content.SharedPreferences
import com.funny.data_saver.core.mutableDataSaverListStateOf
import com.funny.data_saver.core.rememberDataSaverListState


object DataPersistence {
    // Name for the SharedPreferences file
    private const val PREFS_NAME = "ChoreBuddyPrefs"

    //var data byz mutableDataSaverListStateOf(key = "members", initialValue = listOf())
    fun saveMemberList(context: Context, memberList: List<String>) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putStringSet("members", memberList.toSet())
        editor.apply()
    }

    // Retrieves the list of members from SharedPreferences
    fun retrieveMemberList(context: Context): List<String> {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val memberSet = sharedPreferences.getStringSet("members", setOf())
        return memberSet?.toList() ?: emptyList()
    }
}

