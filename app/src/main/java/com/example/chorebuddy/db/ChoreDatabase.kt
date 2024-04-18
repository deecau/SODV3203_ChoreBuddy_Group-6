package com.example.chorebuddy.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chorebuddy.dao.ChoreDao
import com.example.chorebuddy.dao.MemberDao
import com.example.chorebuddy.model.ChoreBean
import com.example.chorebuddy.model.MemberBean

@Database(entities = [ChoreBean::class,MemberBean::class], version = 1, exportSchema = false)
abstract class ChoreDatabase:RoomDatabase() {

    abstract fun choreDao(): ChoreDao

    abstract fun memberDao(): MemberDao //Added for member table


    companion object{
        @Volatile
        private var INSTANCE:ChoreDatabase?=null

        fun getDataBase(context: Context):ChoreDatabase{
            return INSTANCE?:
            synchronized(this){
                Room.databaseBuilder(context,ChoreDatabase::class.java,"chore.db").build().also {
                    INSTANCE = it
                }
            }
        }

    }
}