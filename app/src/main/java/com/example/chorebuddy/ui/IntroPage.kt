package com.example.chorebuddy.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chorebuddy.BuddyScreen
import com.example.chorebuddy.R

@Composable
fun IntroPage(navController: NavController) {
    BuddyScreen(navController = navController, titleResId = R.string.intro_page_title) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to Chores Buddy\n\nFor the 1st time use, please click the profile page to set up your family member 1st.\n\nOnce you have set up your family member, you can start clicking the bottom right hand side button to start adding new Chores.\n\nEnjoy your chores with your Family!",
                color = Color(0xFF64B7F6),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Preview
@Composable
fun IntroPagePreview() {
    // For preview purposes, pass a NavController instance
    val navController = rememberNavController()
    IntroPage(navController = navController)
}

