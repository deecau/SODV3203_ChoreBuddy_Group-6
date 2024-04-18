package com.example.chorebuddy.ui


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chorebuddy.BuddyScreen
import com.example.chorebuddy.MainActivity
import com.example.chorebuddy.R
import com.example.chorebuddy.model.ChoreBean
import com.example.chorebuddy.viewmodel.ChoreViewModel
import com.example.chorebuddy.viewmodel.MemberViewModel

@Composable
fun HomeScreen(navController: NavController, choreViewModel: ChoreViewModel = viewModel(factory = AppViewModelProvider.Factory),memberViewModel: MemberViewModel = viewModel(factory = AppViewModelProvider.Factory)){

    val context = LocalContext.current as MainActivity

    var chores by remember { mutableStateOf(listOf<ChoreBean>()) }

    val datas = choreViewModel.queryUnFinishedChores.collectAsState(initial = arrayListOf())

      BuddyScreen(navController, titleResId = R.string.home_page_title) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Text(
                text = "Unfinished Chores",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            LazyColumn(content = {

                items(datas.value) {
                    val member = memberViewModel.getMember(it.memberId).collectAsState(initial = null)

                    ChoreItem(it.id,it.name, member.value?.name, it.date, it.dueDate, it.details,navController)
                }
            })
        }
    }
}


@Preview
@Composable
fun HomePagePreview() {
    // For preview purposes, pass a NavController instance
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}
