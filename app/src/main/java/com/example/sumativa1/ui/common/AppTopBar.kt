package com.example.sumativa1.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(title: String, nav: NavController, canNavigateBack: Boolean = true) {
    CenterAlignedTopAppBar(
        title = {

            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = { nav.navigateUp() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Atrás")
                }
            }
        }
    )
}
