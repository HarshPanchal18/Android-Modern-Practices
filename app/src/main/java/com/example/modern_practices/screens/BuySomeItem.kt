package com.example.modern_practices.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.modern_practices.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuySomeItem() {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = Color(0xFF6a88F7),
                contentColor = Color.Black
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add button"
                )
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.buy_some_items),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Description
                Text(
                    text = "Tap the button below to shop for items.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    letterSpacing = 1.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                )

                Spacer(modifier = Modifier.height(20.dp))

            }
        }
    }
}
