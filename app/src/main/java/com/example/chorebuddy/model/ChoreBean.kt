package com.example.chorebuddy.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity("chore_table")
data class ChoreBean(

    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    var name:String,
    val typeId:Int,
    var details:String,
    var date:String,
    var memberId:Int,
    var dueDate:String,
    var finished:Int = 0,
    var finishDate: String?,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "id", typeAffinity = ColumnInfo.INTEGER)
    val id:Int = 0)
