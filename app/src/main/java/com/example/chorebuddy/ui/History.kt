package com.example.chorebuddy.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chorebuddy.BuddyScreen
import com.example.chorebuddy.R
import com.example.chorebuddy.model.ChoreBean
import com.example.chorebuddy.viewmodel.ChoreViewModel
import com.example.chorebuddy.viewmodel.MemberViewModel


@Composable
fun HistoryScreen(
    navController: NavController,
    choreViewModel: ChoreViewModel = viewModel(factory = AppViewModelProvider.Factory),
    memberViewModel: MemberViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val chores by remember {
        mutableStateOf(arrayListOf<ChoreBean>())
    }

    val finishedChores = choreViewModel.getFinishedChores().collectAsState(initial = arrayListOf())


    BuddyScreen(navController = navController, titleResId = R.string.history_title) {

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(finishedChores.value){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    val member = memberViewModel.getMember(it.memberId).collectAsState(initial = null)

                    Text(
                        text = "A ${it.name}\nwas finished by ${member.value?.name}\nAssigned Date : ${it.date}\nDue Date : ${it.dueDate}\nChore Details:${it.details}\n",
                        color = Color.White,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start
                    )
                }
            }

        }

    }

}


@Preview(showBackground = true)
@Composable
fun HistoryPreview() {
    val navController = rememberNavController() // Mock NavController for preview purposes
    HistoryScreen(navController)
}