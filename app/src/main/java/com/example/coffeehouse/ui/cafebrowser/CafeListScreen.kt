package com.example.coffeehouse.ui.cafebrowser

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.coffeehouse.R
import com.example.coffeehouse.domain.model.CoffeeHouse
import com.example.coffeehouse.ui.cafebrowser.components.CoffeeHouseCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CafeListScreen(
    cafeList: List<CoffeeHouse>,
    currentPoint: CoffeeHouse.Point?,
    updateBrowserState: () -> Unit,
    onNavigateNext: (Long) -> Unit,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            Column() {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.cafe_list_title),
                            color = colorResource(id = R.color.brown_dark),
                            fontWeight = FontWeight.Bold,
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { onNavigateBack() },
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_back),
                                contentDescription = "",
                                tint = colorResource(id = R.color.brown_dark),
                            )
                        }
                    },
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = colorResource(id = R.color.gray),
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = colorResource(id = R.color.gray),
                    modifier = Modifier.padding(top = 1.dp)
                )
            }
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)

            ) {
                LazyColumn(
                    modifier = Modifier
                        .widthIn(max = 500.dp)
                        .align(Alignment.Center)
                        .padding(start = 16.dp, end = 16.dp),
                    contentPadding = PaddingValues(bottom = 32.dp + 48.dp)
                ) {
                    items(cafeList) {
                        CoffeeHouseCard(
                            modifier = Modifier.padding(bottom = 6.dp).clickable(onClick = { onNavigateNext(it.id) }),
                            coffeeHouse = it,
                            currentPoint = currentPoint,
                        )
                    }
                }
                Button(
                    onClick = {
                        updateBrowserState()
                    },
                    shape = RoundedCornerShape(24.5.dp),
                    modifier = Modifier
                        .widthIn(max = 500.dp)
                        .align(Alignment.BottomCenter)
                        .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.button_brown))
                ) {
                    Text(
                        text = stringResource(R.string.button_title_map),
                        color = colorResource(id = R.color.brown_light)
                    )
                }
            }
        },
    )
}

@PreviewScreenSizes
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val coffeeHouse = CoffeeHouse(id = 1, name = "Синабон", point = CoffeeHouse.Point(1.1, 1.1))
    CafeListScreen(
        listOf<CoffeeHouse>(
            coffeeHouse,
            coffeeHouse,
            coffeeHouse,
            coffeeHouse,
            coffeeHouse,
            coffeeHouse,
            coffeeHouse,
            coffeeHouse,
            coffeeHouse,
            coffeeHouse,
            coffeeHouse,
            coffeeHouse
        ),
        CoffeeHouse.Point(null ,null),
        {},{}, {}
    )
}