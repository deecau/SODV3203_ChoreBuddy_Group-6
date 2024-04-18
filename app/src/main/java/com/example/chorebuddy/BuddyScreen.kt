package com.example.chorebuddy

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun BuddyScreen(navController: NavController, titleResId: Int, content: @Composable () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF003891))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Column 1: Icon
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.chores_buddy),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(90.dp)
                )
            }

            // Column 2: Home Text
            Box(
                modifier = Modifier
                    .weight(2f)
                    .padding(horizontal = 1.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = titleResId), // Use the provided title resource
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 1.dp)
                )
            }

            // Column 3: Empty
            Spacer(modifier = Modifier.weight(1f))
        }

        // Top Divider Line
        Spacer(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(Color(0xFF1964BD))
        )

        // Content
        Box(
            modifier = Modifier
                .weight(5f) // This will take the 5 parts of the available space
                .fillMaxWidth()
//                .verticalScroll(rememberScrollState())
        ) {
            content()
        }


        // Bottom Divider Line
        Spacer(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(Color(0xFF1964BD))
        )

        // Bottom Bar
        Row(
            modifier = Modifier
                .weight(1f)
                .background(Color(0xFF003891))
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Home Button
            Icon(
                painter = painterResource(id = R.drawable.home_button),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .weight(1f)
                    .size(80.dp)
                    .clickable {
                        val entry = navController.currentBackStackEntry
                        val id = entry?.destination?.id
                        val route = entry?.destination?.route
                        if (route != "home") {
                            navController.navigate("home")
                        }
                    }
            )

            Spacer(modifier = Modifier.width(8.dp))

            // User Icon
            Icon(
                painter = painterResource(id = R.drawable.user),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .weight(1f)
                    .size(80.dp)
                    .clickable {
                        val entry = navController.currentBackStackEntry
                        val route = entry?.destination?.route
                        if (route != "profile") {
                            navController.navigate("profile")
                        }
                    }

            )

            Spacer(modifier = Modifier.width(8.dp))
            Box (
                contentAlignment = Alignment.Center,
                modifier = Modifier.weight(1f)){
                var expandedMenu by remember {
                    mutableStateOf(false)
                }
                // List Icon
                Icon(
                    painter = painterResource(id = R.drawable.list),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(80.dp)
                        .clickable { //expandedMenu = true/*navController.navigate("addChore")*/
                            val entry = navController.currentBackStackEntry
                            val route = entry?.destination?.route
                            if (route != "History") {
                                navController.navigate("History")
                            }
                             }
                )

            }

        }
    }
}
