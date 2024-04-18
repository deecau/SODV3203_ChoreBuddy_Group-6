package com.example.chorebuddy.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chorebuddy.BuddyScreen
import com.example.chorebuddy.DataSource
import com.example.chorebuddy.R
import com.example.chorebuddy.model.ChoreBean
import com.example.chorebuddy.viewmodel.ChoreViewModel
import com.example.chorebuddy.viewmodel.MemberViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.TimeZone
import kotlin.coroutines.coroutineContext



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyChore(
    navController: NavController,
    id: Int = 0,
    memberViewModel: MemberViewModel = viewModel(factory = AppViewModelProvider.Factory),
    choreViewModel: ChoreViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val choreDetail = choreViewModel.getChore(id).collectAsState(initial = null)
    var progress = 0f
    var details by remember {
        mutableStateOf(choreDetail.value?.details ?: "")
    }
    var finishedChore by remember {
        mutableStateOf(false)
    }
    val members = memberViewModel.allMember.collectAsState(initial = arrayListOf())
    var expandShow by remember {
        mutableStateOf(false)
    }
    var expanded by remember { mutableStateOf(false) }
    val items = DataSource.choreCategories
    var selectedTypeIndex by remember {
        mutableStateOf(-1)
    }
    var selectedMemberIndex by remember {
        mutableStateOf(-1)
    }
    var selectedType by remember {
        mutableStateOf(choreDetail.value?.name ?: "Select Chore Type")
    }
    val member = memberViewModel.getMember(choreDetail.value?.id).collectAsState(initial = null)
    var selectedMember by remember {
        mutableStateOf(member.value?.name ?: "Choose A Member")
    }
    val coroutineContext = rememberCoroutineScope()
    var enteredDate by remember { mutableStateOf("") } // Store user-entered date
    var errorMessage by remember { mutableStateOf("") } // Store error message

    choreDetail.value?.let {
        if (details.isEmpty()) {
            details = it.details
        }
        if (selectedType == "Select Chore Type") {
            selectedType = it.name
        }
        if (selectedMember == "Choose A Member") {
            val memberId = it.memberId
            coroutineContext.launch {
                memberViewModel.getMember(memberId).collect { member ->
                    selectedMember = member.name
                }
            }
        }
        if (enteredDate == "") {
            enteredDate = choreDetail.value?.dueDate!!
        }
        val dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
//        val dateFormatter1 = DateTimeFormatter.ofPattern("yyyy-M-dd")
        val startDateObj = LocalDate.parse(it.date, dateFormatter)
        val dueDateObj = LocalDate.parse(it.dueDate, dateFormatter)
        val currentDateObj = LocalDate.now()
        val totalDays = ChronoUnit.DAYS.between(startDateObj, dueDateObj).toFloat()
        val passedDays = ChronoUnit.DAYS.between(startDateObj, currentDateObj).toFloat()
        // Calculates the percentage of remaining time
        progress = if (totalDays > 0) 1f - (passedDays / totalDays) else 0f
    }
    var selectedMemberId by remember {
        mutableStateOf(0)
    }
    BuddyScreen(navController = navController, titleResId = R.string.modify_chore_title) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF003891))
//                    .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            // Body First Row
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                // Column 1: Chore List
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = "Chore List",
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 20.dp, end = 10.dp)
                    )
                }

                // Column 2: Scrollable List
                Column(
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxHeight()
                        .padding(13.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Box(contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .height(60.dp)
                            .background(Color.White)
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {

                        Text(
                            text = selectedType, modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    expanded = true
                                }, color = Color.Gray
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }) {
                            items.forEachIndexed { index, s ->
                                DropdownMenuItem(onClick = {
                                    selectedTypeIndex = index
                                    selectedType = items[index]
                                    expanded = false
                                }) {
                                    Text(text = s)
                                }
                            }
                        }

                    }
                }
            }


            // Body: Second Row
            Row(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
            ) {
                // Column 1: Chore Details
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Chore Details",
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 20.dp, end = 10.dp)
                    )
                }

                // Column 2: Text Box
                Column(
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxHeight()
                        .padding(13.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {

                    var textFieldScrollState = rememberScrollState()


                    TextField(
                        value = details,
                        onValueChange = { newText ->
                            details = newText
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(Color.White)
                            .verticalScroll(state = textFieldScrollState) // 启用滚动功能
                    )
                }
            }

            // Body: Third Row
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                // Column 1: Due Date
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Due Date",
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 20.dp, end = 10.dp)
                    )
                }

                // Column 2: Calendar Box
                Column(
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxHeight()
                        .padding(13.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {

                    // Calendar Box with TextField for manual input
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .background(Color.White)
                    ) {
                        var openDatePicker by remember {
                            mutableStateOf(false)
                        }
                        TextField(
                            value = enteredDate,
                            onValueChange = {
                                if (it.length <= 10) {
                                    enteredDate = it
                                    try {
                                        SimpleDateFormat("MM/dd/yyyy").parse(enteredDate)
                                        errorMessage = ""
                                    } catch (e: Exception) {
                                        errorMessage = "Invalid date"
                                    }

                                } else {
                                    errorMessage = "Invalid date"
                                }
                            },
                            textStyle = TextStyle(
                                color = if (errorMessage.isNotEmpty()) Color.Red else Color.Black,
                                fontSize = 14.sp
                            ),
                            placeholder = {
                                Text(
                                    text = "MM/DD/YYYY",
                                    color = Color(0xFF757575),
                                    fontSize = 14.sp,
                                )
                            },
                            singleLine = true,
                            enabled = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable{
                                    openDatePicker = true
                                }
                                .padding(0.dp)
                                .height(60.dp)
                        )
if (openDatePicker) {
    var datePickerState = rememberDatePickerState()
    DatePickerDialog(
        onDismissRequest = { openDatePicker = false },
        confirmButton = {
            TextButton(onClick = {
                openDatePicker = false
                var millis = datePickerState.selectedDateMillis
                val calendar = Calendar.getInstance(
                    TimeZone.getTimeZone(
                        ZoneId.systemDefault()
                    )
                )
                millis?.let {
                    calendar.timeInMillis = it
//                                            val date = Date()
//                                            date.time = millis
                    calendar.add(Calendar.DAY_OF_MONTH, 1)
                    enteredDate =
                        SimpleDateFormat("MM/dd/yyyy").format(calendar.time)
                }

            }) {
                Text(text = "Ok")
            }
        }) {
        DatePicker(state = datePickerState)
    }
}
                        if (enteredDate.length == 10) {
                            Text(
                                text = errorMessage,
                                color = Color.Red,
                                fontSize = 12.sp,
                                textAlign = TextAlign.End,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .align(Alignment.BottomEnd)
                            )
                        }
                    }
                }


            }

            // Body: Fourth Row
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                // Column 1: Assignment to Whom
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Assignment to Whom",
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier.padding(top = 20.dp, end = 10.dp)
                    )
                }

                // Column 2: Scrollable List
                Column(
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxHeight()
                        .padding(13.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .height(60.dp)
                            .background(Color.White)
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {

                        Text(
                            text =
                            selectedMember,
                            color = Color.Gray,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    expandShow = true
                                }
                        )

                        DropdownMenu(
                            expanded = expandShow,
                            onDismissRequest = { expandShow = false }) {
                            Log.i("memberInfo", members.value.toString())

                            members.value.forEachIndexed { index, item ->
                                DropdownMenuItem(onClick = {
                                    selectedMember = members.value[index].name
                                    Log.i("selectedMember", selectedMember)
                                    expandShow = false
                                    selectedMemberId = members.value[index].id
                                }) {
                                    Text(text = item.name)
                                }
                            }
                        }
                    }
                }
            }
            // Body: Final Row
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Column: Button
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!finishedChore) {
                        Button(
                            onClick = {
                                //update chores

                                coroutineContext.launch {
                                    withContext(Dispatchers.IO) {
                                        choreDetail.value?.details = details
                                        choreDetail.value?.dueDate = enteredDate
                                        if (selectedTypeIndex >= 0) {
                                            choreDetail.value?.name = selectedType
                                        }
                                        if (selectedMemberId > 0) {
                                            choreDetail.value?.memberId = selectedMemberId
                                        }
                                        choreViewModel.updateChore(choreDetail.value)
                                        withContext(Dispatchers.Main) {
                                            navController.navigate("ChoreSaved/${id}")
                                        }
                                    }
                                    Toast.makeText(
                                        navController.context,
                                        "Chore Updated",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },

                            modifier = Modifier
                                .padding(8.dp)
                                .size(width = 250.dp, height = 60.dp)
                        ) {
                            Text(
                                text = "Modify Chore",
                                fontSize = 20.sp
                            )

                        }
                    }
                }
            }

        }
    }






}

@Preview
@Composable
fun ModifyChorePreview() {
    val navController = rememberNavController()
    ModifyChore(navController)
}
