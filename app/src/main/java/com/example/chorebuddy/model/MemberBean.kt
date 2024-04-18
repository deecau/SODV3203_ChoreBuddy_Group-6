package com.example.chorebuddy.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "member")
data class MemberBean(

    @ColumnInfo(name = "name",typeAffinity = ColumnInfo.TEXT)
    var name:String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id",typeAffinity = ColumnInfo.INTEGER)
    val id:Int = 0
    ) {
}