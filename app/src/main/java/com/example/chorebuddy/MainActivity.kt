package com.example.chorebuddy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chorebuddy.ui.AddChore
import com.example.chorebuddy.ui.AppViewModelProvider
import com.example.chorebuddy.ui.ChoreDetails
import com.example.chorebuddy.ui.ChoreSaved
import com.example.chorebuddy.ui.HistoryScreen
import com.example.chorebuddy.ui.HomeScreen
import com.example.chorebuddy.ui.ModifyChore
import com.example.chorebuddy.ui.MyProfile
import com.example.chorebuddy.viewmodel.MemberViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}


@Preview
@Composable
fun AppNavigation(memberViewModel: MemberViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val navController = rememberNavController()
    val members by memberViewModel.allMember.collectAsState(initial = emptyList())

    val startDestination = if (members.isNotEmpty()) {
        "home"
    } else {
        "IntroPage"
    }
    NavHost(navController = navController, startDestination = startDestination) {
        composable("home") { HomeScreen(navController) }
        composable("IntroPage") {
            com.example.chorebuddy.ui.IntroPage(
                navController
            )
        }
        composable("profile") { MyProfile(navController) }
        composable("addChore") { AddChore(navController) }
        composable(route="ChoreSaved/{id}", arguments = listOf(
            navArgument("id"){
                type= NavType.IntType
            }
        )){
            val choreId = it.arguments?.getInt("id")
//            Log.i("choreId2","$choreId")
            ChoreSaved(id = choreId?:0,navController)
        }
        composable(route = "ChoreDetails/{id}", arguments = listOf(
            navArgument("id"){
                type = NavType.IntType
            }
        )){
            val id = it.arguments?.getInt("id")
            ChoreDetails(navController = navController,id= id?:0)
        }
        composable(route="ChoreUpdate/{id}", arguments = listOf(
            navArgument("id"){
                type= NavType.IntType
            }
        )){
            val choreId = it.arguments?.getInt("id")
            ModifyChore(navController = navController, choreId!!)
        }
        composable(route="history"){
            HistoryScreen(navController = navController)
        }
    }
}

