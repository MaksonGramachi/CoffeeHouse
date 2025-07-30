package com.example.coffeehouse.ui.menu

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeehouse.R
import com.example.coffeehouse.domain.model.CoffeeHouseMenu
import com.example.coffeehouse.ui.menu.components.ProductOrderCard
import com.example.coffeehouse.ui.menu.model.ProductInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    currentMenu: List<ProductInfo>,
    plusProductCount: (Long) -> Unit,
    minusProductCount: (Long) -> Unit,
    updateMenuState: () -> Unit,
    onNavigateNext: () -> Unit = {}, // перемещение на экран оплаты
) {
    BackHandler(enabled = true) {
        updateMenuState()
    }

    Scaffold(
        topBar = {
            Column() {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.order_title),
                            color = colorResource(R.color.brown_dark),
                            fontWeight = FontWeight.Bold,
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { updateMenuState() },
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_back),
                                contentDescription = "",
                                tint = colorResource(R.color.brown_dark),
                            )
                        }
                    },
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = colorResource(R.color.gray),
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = colorResource(R.color.gray),
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
                Column(modifier = Modifier.align(Alignment.Center)) {
                    LazyColumn(
                        modifier = Modifier
                            .widthIn(max = 500.dp)
                            .align(Alignment.CenterHorizontally)
                            .padding(start = 16.dp, end = 16.dp)
                            .weight(1f),
                    ) {
                        items(currentMenu) { item ->
                            ProductOrderCard(
                                modifier = Modifier.padding(bottom = 6.dp),
                                productInfo = item,
                                plusCount = { plusProductCount(item.productCard.id) },
                                minusCount = { minusProductCount(item.productCard.id) })

                        }
                    }
                    Box(modifier = Modifier.weight(1f).align(Alignment.CenterHorizontally)) {
                        Text(
                            text = stringResource(R.string.order_time_title),
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.brown_dark),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Button(
                    onClick = { onNavigateNext() },
                    shape = RoundedCornerShape(24.5.dp),
                    modifier = Modifier
                        .widthIn(max = 500.dp)
                        .align(Alignment.BottomCenter)
                        .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.button_brown))
                ) {
                    Text(
                        text = stringResource(R.string.button_title_order),
                        color = colorResource(R.color.brown_light)
                    )
                }
            }
        })
}
@PreviewScreenSizes
@Preview(showBackground = true)
@Composable
fun OrderPreview() {
    val menu = CoffeeHouseMenu(id = 1, name = "Синабон", price = 1.99f)
    val productInfo = ProductInfo(menu, 2u)
    OrderScreen(
        listOf(
        productInfo, productInfo, productInfo, productInfo, productInfo,

    ), {}, {}, {}, {})
}
