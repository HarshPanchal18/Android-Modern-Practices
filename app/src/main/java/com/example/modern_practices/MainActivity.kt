package com.example.modern_practices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.modern_practices.ui.theme.ModernPracticesTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            ModernPracticesTheme {
//                ContentView()
                ParallaxEffect()
            }
        }
    }

    //    @Preview(showBackground = true)
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ContentView(modifier: Modifier = Modifier) {
        val banners = listOf("Banner 1", "Banner 2", "Banner 3", "Banner 4", "Banner 5")

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
                HorizontalPager(
                    modifier = Modifier.height(50.dp),
                    state = rememberPagerState { banners.size },
                    pageSpacing = 15.dp,
                    contentPadding = PaddingValues(horizontal = 40.dp)
                ) { page ->
                    banners.getOrNull(page)?.let { banner ->
                        BannerItemView(banner = banner)
                    }
                }
            }
        }
    }

    @Composable
    fun BannerItemView(modifier: Modifier = Modifier, banner: String) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Cyan),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = banner,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 25.sp
            )
        }
    }


    fun Modifier.parallaxLayoutModifier(scrollState: ScrollState, rate: Int): Modifier {
        return layout { measurable, constraints -> // represents the element we want to measure and place, and constraints we need to follow.

            // measure the measurable with provided constraints
            val placeable = measurable.measure(constraints)

            // calculate the height based on scroll state and the rate
            val height = if (rate > 0) scrollState.value / rate else scrollState.value

            // Create a layout
            layout(placeable.width, placeable.height) {
                placeable.place(0, height)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ParallaxEffect(modifier: Modifier = Modifier) {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(id = R.drawable.android_logo),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .parallaxLayoutModifier(scrollState, 2),
                contentScale = ContentScale.FillWidth
            )

            Text(
                text = stringResource(id = R.string.lorem_ipsum),
                modifier = Modifier
                    .background(Color.White)
                    .padding(vertical = 20.dp, horizontal = 15.dp)
            )
        }
    }
}
