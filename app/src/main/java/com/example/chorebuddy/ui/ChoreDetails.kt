package com.example.chorebuddy.ui


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date


@Composable
fun ChoreDetails(
    navController: NavController,
    id: Int = 0,
    choreViewModel: ChoreViewModel = viewModel(factory = AppViewModelProvider.Factory),
    memberViewModel: MemberViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    BuddyScreen(navController = navController, titleResId = R.string.chore_saved_title) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF003891))
        ) {
            val choreDetailBean = choreViewModel.getChore(id).collectAsState(initial = null)
            //TopBar(modifier = Modifier.weight(1f))

            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth()
                    .background(Color(0xFF1964BD))
            )

            // Provide a placeholder chore details
            choreDetailBean.value?.let {
                val member = memberViewModel.getMember(it.memberId).collectAsState(initial = null)
                BodyContent(
                    it.id,
                    it.name,
                    member.value?.name ?: "",
                    it.memberId,
                    it.date,
                    it.dueDate,
                    it.details,
                    navController,
                    modifier = Modifier.weight(6f),
                    it.finished,
                    choreViewModel
                )
            }

            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth()
                    .background(Color(0xFF1964BD))
            )

//        BottomBar(modifier = Modifier.weight(1f))
        }
    }
}


@Composable
fun BodyContent(
    choreId: Int,
    choreName: String,
    memberName: String,
    memberId: Int,
    startDate: String,
    dueDate: String,
    choreDetails: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    finished: Int,
    choreViewModel: ChoreViewModel
) {
    var finish by remember {
        mutableStateOf(finished)
    }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        // Display ChoreItem
        ChoreItem(
            choreId,
            choreName,
            memberName,
            startDate,
            dueDate,
            choreDetails,
            navController,
            false
        )

        // Spacer to add some space between due date and chore details
        Spacer(modifier = Modifier.height(16.dp))

        // White container for displaying chore details
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f) // Set the width of the container to 80% of the parent width
                .fillMaxHeight(0.4f) // Set the height of the container to 40% of the parent height
                .background(Color.White)
                .padding(20.dp)
                .align(Alignment.CenterHorizontally) // Horizontally center the container
        ) {
            Text(
                text = choreDetails,
                color = Color.Black,
                fontSize = 16.sp
            )
        }

        // Spacer to add some space between chore details and buttons
        Spacer(modifier = Modifier.height(16.dp))

        if (finish == 0) {
            // Buttons
            Button(
                onClick = { navController.navigate("ChoreUpdate/${choreId}") },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 30.dp)
                    .size(width = 200.dp, height = 50.dp)

            ) {
                Text(
                    text = "Modify Chore",
                    fontSize = 20.sp
                )
            }
        }
        Button(
            onClick = {
                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        val choreBean = ChoreBean(
                            choreName,
                            0,
                            choreDetails,
                            startDate,
                            memberId,
                            dueDate,
                            1,
                            SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()),
                            choreId
                        )
                        choreViewModel.updateChore(choreBean)
                        finish = 1
                    }
                    Toast
                        .makeText(navController.context, "Chore Finished", Toast.LENGTH_SHORT)
                        .show()
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(20.dp)
                .clickable {

                }
                .size(width = 200.dp, height = 50.dp)
        ) {
            Text(
                text = "Mark As Done",
                fontSize = 20.sp
            )
        }

    }
}


@Composable
fun TopBar(modifier: Modifier = Modifier) {
    // Use the modifier passed to the function. If none is passed, the default Modifier will be used.
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.chores_buddy),
            contentDescription = "Chore Buddy Logo",
            tint = Color.Unspecified,
            modifier = Modifier.size(90.dp)
        )
        Text(
            text = "Chore Details",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChoreItem(
    choreId: Int,
    choreName: String,
    memberName: String?,
    startDate: String,
    dueDate: String,
    choreDetails: String,
    navController: NavController,
    navigable: Boolean = true
) {
    // Adjusted to handle dates in "MM/dd/yyyy" format
    val dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
//    val dateFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val startDateObj = LocalDate.parse(startDate, dateFormatter)
    val dueDateObj = LocalDate.parse(dueDate, dateFormatter)
    val currentDateObj = LocalDate.now()

    val totalDays = ChronoUnit.DAYS.between(startDateObj, dueDateObj).toFloat()
    val passedDays = ChronoUnit.DAYS.between(startDateObj, currentDateObj).toFloat()
    // Calculates the percentage of remaining time
    val progress = if (totalDays > 0) 1f - (passedDays / totalDays) else 0f

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (navigable) {
                    navController.navigate("ChoreDetails/$choreId")
                }
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(2f)
        ) {
            Text(
                text = choreName,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = memberName ?: "",
                color = Color.White,
                fontSize = 20.sp
            )
        }

        Column(
            modifier = Modifier
                .weight(3f)
        ) {
            Slider(
                value = progress,
                onValueChange = { /* No action needed because Slider is not interactive */ },
                valueRange = 0f..1f,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(10.dp),
//                enabled = false,
                colors = SliderDefaults.colors(
                    activeTrackColor = Color(0xFF18ad9b),
                    inactiveTrackColor = Color.Gray,
                ),
                thumb = {}
            )
            Text(
                text = "Due Date: $dueDate",
                color = Color.White,
                fontSize = 16.sp
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun PreviewChoreDetails() {
    val controller = rememberNavController()
    ChoreDetails(controller)
}

