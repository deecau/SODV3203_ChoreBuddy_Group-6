package com.example.chorebuddy.ui

import android.os.Handler
import android.os.Looper
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chorebuddy.BuddyScreen
import com.example.chorebuddy.DataSource
import com.example.chorebuddy.R
import com.example.chorebuddy.model.ChoreBean
import com.example.chorebuddy.viewmodel.ChoreViewModel
import com.example.chorebuddy.viewmodel.MemberViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.TimeZone


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddChore(
    navController: NavHostController,
    memberViewModel: MemberViewModel = viewModel(factory = AppViewModelProvider.Factory),
    choreViewModel: ChoreViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }
//    var enteredText by remember { mutableStateOf("") }
    var newMemberName by remember { mutableStateOf("") }
    val members = memberViewModel.allMember.collectAsState(initial = arrayListOf())
    var selectedIndex by remember {
        mutableStateOf(-1)
    }
    var expandShow by remember {
        mutableStateOf(false)
    }
    var selectedMember by remember {
        mutableStateOf("Choose A Member")
    }
    var expanded by remember { mutableStateOf(false) }
    val items = DataSource.choreCategories
    var selectedTypeIndex by remember {
        mutableStateOf(-1)
    }
    var selectedType by remember {
        mutableStateOf("Select Chore Type")
    }
    var text by remember { mutableStateOf("") }
    var enteredDate by remember { mutableStateOf("") } // Store user-entered date
    var errorMessage by remember { mutableStateOf("") } // Store error message

    var coroutineScope = rememberCoroutineScope()
    BuddyScreen(navController = navController, titleResId = R.string.add_chore_title) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF003891))
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
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(8.dp)
                    ) {

                        Text(
                            text = selectedType, modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    expanded = true
                                }, color = Color.Gray
                        )
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
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
                        fontSize = 20.sp,
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
                        value = text,
                        onValueChange = { newText ->
                            text = newText
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
                        .fillMaxWidth()
                        .padding(13.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {

                    // Calendar Box with TextField for manual input
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxWidth()
                            .height(60.dp)
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
                            ),
                            placeholder = {
                                Text(
                                    text = "MM/DD/YYYY",
                                    color = Color(0xFF757575),
                                    fontSize = 14.sp
                                )
                            },
                            singleLine = true,
                            enabled = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(0.dp)
                                .clickable {
                                    openDatePicker = true
                                }
                                .background(Color.White)
                        )
                        if (openDatePicker) {
                            var datePickerState = rememberDatePickerState()
                            DatePickerDialog(
                                onDismissRequest = { openDatePicker = false },
                                confirmButton = {
                                    TextButton(onClick = {
                                        openDatePicker = false
                                        var millis = datePickerState.selectedDateMillis
                                        val calendar = Calendar.getInstance(TimeZone.getTimeZone(
                                            ZoneId.systemDefault()))
                                        millis?.let {
                                            calendar.timeInMillis = it
//                                            val date = Date()
//                                            date.time = millis
                                            calendar.add(Calendar.DAY_OF_MONTH,1)
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
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {

                        Text(
                            text =
                            selectedMember,
                            color = Color.Gray,
                            modifier = Modifier
                                .padding(8.dp)
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
                                    selectedIndex = index
                                    selectedMember = members.value[index].name
                                    Log.i("selectedMember", selectedMember)
                                    expandShow = false
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
                    .weight(1f)
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
                    Button(
                        onClick = {
                            if (selectedTypeIndex == -1) {
                                Toast.makeText(
                                    navController.context,
                                    "Please select a type",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }
                            if (text == "") {
                                Toast.makeText(
                                    navController.context,
                                    "Please enter a chore detail",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }
                            if (enteredDate == "") {
                                Toast.makeText(
                                    navController.context,
                                    "Please enter a date",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }

                            if (selectedIndex == -1) {
                                Toast.makeText(
                                    navController.context,
                                    "Please select a member",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }
                            val choreBean = ChoreBean(
                                items[selectedTypeIndex],
                                0,
                                text,
                                SimpleDateFormat("MM/dd/yyyy").format(Date()),
                                members.value[selectedIndex].id,
                                enteredDate,0,null
                            )
                            coroutineScope.launch {
                                withContext(Dispatchers.IO) {
                                    val id = choreViewModel.addChore(choreBean)
//                                Handler(Looper.getMainLooper()).postDelayed({
                                    Log.i("choreId1", id.toString())
                                    withContext(Dispatchers.Main) {
                                        Log.i("choreId3", id.toString())
                                        navController.navigate("ChoreSaved/${id}")
                                    }
//                                },50)
                                }
                                Toast
                                    .makeText(
                                        navController.context,
                                        "Chore Added",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .size(width = 200.dp, height = 60.dp)
                            .clickable {

                            }
                    ) {
                        Text(
                            text = "Add New Chore",
                            fontSize = 20.sp
                        )

                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddChorePreview() {
    val navController = rememberNavController() // Mock NavController for preview purposes
    AddChore(navController)
}