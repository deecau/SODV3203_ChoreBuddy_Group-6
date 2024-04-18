
package com.example.chorebuddy.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.chorebuddy.ChoreApplication
import com.example.chorebuddy.viewmodel.ChoreViewModel
import com.example.chorebuddy.viewmodel.MemberViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEntryViewModel
        initializer {
            MemberViewModel(choreApplication().appContainer.memberRepository)
        }

        // Initializer for ItemDetailsViewModel
        initializer {
            ChoreViewModel(choreApplication().appContainer.choreRepository)
        }

    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.choreApplication(): ChoreApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ChoreApplication)
