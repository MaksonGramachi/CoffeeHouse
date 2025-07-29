package com.example.coffeehouse.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.coffeehouse.R
import com.example.coffeehouse.domain.model.CoffeeHouseMenu
import com.example.coffeehouse.ui.menu.components.ProductCard
import com.example.coffeehouse.ui.menu.model.ProductInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    currentMenu: List<ProductInfo>,
    plusProductCount: (Long) -> Unit,
    minusProductCount: (Long) -> Unit,
    updateMenuState: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            Column() {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.menu_title),
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
            }
            HorizontalDivider(
                thickness = 1.dp,
                color = colorResource(id = R.color.gray)
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = colorResource(id = R.color.gray),
                modifier = Modifier.padding(top = 1.dp)
            )

        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(13.dp),
                    horizontalArrangement = Arrangement.spacedBy(13.dp),
                    contentPadding = PaddingValues(bottom = 32.dp + 48.dp),
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                ) {
                    items(currentMenu) { item ->
                        ProductCard(
                            productInfo = item,
                            plusCount = { plusProductCount(item.productCard.id) },
                            minusCount = { minusProductCount(item.productCard.id) })
                    }
                }
                Button(
                    onClick = {
                        updateMenuState()
                    },
                    shape = RoundedCornerShape(24.5.dp),
                    modifier = Modifier
                        .widthIn(max = 500.dp)
                        .align(Alignment.BottomCenter)
                        .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.button_brown)),
                    enabled = !currentMenu.isEmpty()
                ) {
                    Text(
                        text = stringResource(R.string.button_title_order),
                        color = colorResource(id = R.color.brown_light)
                    )
                }
            }
        }
    )
}

@PreviewScreenSizes
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val menu = CoffeeHouseMenu(id = 1, name = "Синабон", price = 1.99f)
    val productInfo = ProductInfo(menu, 2u)
    ProductListScreen(
        listOf<ProductInfo>(
            productInfo, productInfo, productInfo, productInfo, productInfo, productInfo, productInfo, productInfo,
        ),{},{}, {}, {}
    )
}