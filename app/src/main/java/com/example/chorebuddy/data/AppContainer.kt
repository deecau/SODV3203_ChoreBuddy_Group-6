
package com.example.chorebuddy.data

import android.content.Context
import com.example.chorebuddy.db.ChoreDatabase
import com.example.chorebuddy.repository.ChoreDataRepository
import com.example.chorebuddy.repository.ChoreRepository
import com.example.chorebuddy.repository.MemberDataRepository
import com.example.chorebuddy.repository.MemberRepository

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val choreRepository: ChoreRepository
    val memberRepository:MemberRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val choreRepository: ChoreRepository by lazy {
        ChoreDataRepository(ChoreDatabase.getDataBase(context).choreDao())
    }

    override val memberRepository: MemberRepository by lazy {
        MemberDataRepository(ChoreDatabase.getDataBase(context).memberDao())
    }

}
