package com.example.modern_practices.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.modern_practices.R

@Composable
fun CameraPermission() {

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.camera),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Description
            Text(
                text = "Camera Access",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                letterSpacing = 2.sp,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                text = "Please allow access to your camera to take photos",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                    .fillMaxWidth(),
                letterSpacing = 1.sp,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
            )

            Spacer(modifier = Modifier.height(24.dp))

            val cornerRadius = 16.dp
            val gradientColor = listOf(Color(0xFFff669f), Color(0xFFff8961))

            GradientButton(
                gradientColors = gradientColor,
                cornerRadius = cornerRadius,
                nameButton = "Allow",
                roundedCornerShape = RoundedCornerShape(20.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                modifier = Modifier.width(150.dp),
                onClick = {}
            ) {
                Text(text = "Cancel", style = MaterialTheme.typography.labelLarge)
            }

            Spacer(modifier = Modifier.height(24.dp))

        }
    }
}

@Composable
fun GradientButton(
    gradientColors: List<Color>,
    cornerRadius: Dp,
    nameButton: String,
    roundedCornerShape: RoundedCornerShape
) {
    Button(
        onClick = {},
        modifier = Modifier.padding(horizontal = 5.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(cornerRadius)
    ) {
        Box(
            modifier = Modifier
                .width(150.dp)
                .background(
                    brush = Brush.horizontalGradient(colors = gradientColors),
                    shape = roundedCornerShape
                )
                .clip(roundedCornerShape)
                /*.background(
                    brush = Brush.linearGradient(colors = gradientColors),
                    shape = RoundedCornerShape(cornerRadius)
                )*/
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = nameButton,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}
