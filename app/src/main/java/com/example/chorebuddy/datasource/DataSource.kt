package com.example.chorebuddy

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.Date

object DataSource {
    // Sample initial members data
    private var _members by mutableStateOf(listOf("Member 1", "Member 2"))

    val members: List<String>
        get() = _members

    // Sample chore categories data
    val choreCategories = listOf(
//        "Select Chore Type",
        "Cleaning", "Laundry", "Cooking", "Yard Work",
        "Organizing", "Maintenance", "Pet Care", "Miscellaneous","Others"
    )


}