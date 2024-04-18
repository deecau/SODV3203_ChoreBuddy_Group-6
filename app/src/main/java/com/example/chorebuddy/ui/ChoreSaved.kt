package com.example.chorebuddy.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chorebuddy.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.chorebuddy.BuddyScreen
import com.example.chorebuddy.viewmodel.ChoreViewModel
import com.example.chorebuddy.viewmodel.MemberViewModel

@Composable
fun ChoreSaved(id:Int,
               navController: NavController,choreViewModel: ChoreViewModel= viewModel(factory = AppViewModelProvider.Factory),
               memberViewModel: MemberViewModel=viewModel(factory=AppViewModelProvider.Factory)) {
    BuddyScreen(navController = navController, titleResId = R.string.chore_saved_title) {
    val chore = choreViewModel.getChore(id).collectAsState(initial = null)
    Log.i("choreId",id.toString())
    Log.i("chore",chore.value.toString())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF003891))
    ) {


        // Top Divider Line
        Spacer(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(Color(0xFF1964BD))
        )

        // Body
        Box(
            modifier = Modifier
                .weight(6f)
                .padding(top = 50.dp, start = 30.dp, end = 30.dp)
        ) {
            val member = memberViewModel.getMember(chore.value?.memberId).collectAsState(initial = null)
            Text(
                text = "A ${chore.value?.name}\nwas successfully added to\n${member.value?.name}\n\nDue Date is\n${chore.value?.date}",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp),
                textAlign = TextAlign.Start
            )


        }
        Column(
            modifier = Modifier.fillMaxWidth(),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(onClick = {
                navController.popBackStack()
            }, modifier = Modifier.padding(8.dp).height(60.dp)) {
                Text(text = "Add Another Chore",fontSize = 20.sp)
            }
        }

        // Bottom Divider Line
        Spacer(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(Color(0xFF1964BD))
        )
        }
    }

}

@Preview
@Composable
fun ChoreSavedPreview() {
//    ChoreSaved()
}
