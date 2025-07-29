package com.example.coffeehouse.ui.menu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.coffeehouse.R
import com.example.coffeehouse.domain.model.CoffeeHouseMenu
import com.example.coffeehouse.ui.menu.model.ProductInfo

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    productInfo: ProductInfo,
    plusCount: () -> Unit,
    minusCount: () -> Unit,
) {
    if (productInfo.productCard.price != null) {
        val shadowColor = colorResource(R.color.bottom_shadow)
        Column(
            modifier = modifier
                .drawBehind {
                    drawRoundRect(
                        color = shadowColor,
                        topLeft = Offset(0f, 2.dp.toPx()),
                        size = Size(size.width, size.height),
                        cornerRadius = CornerRadius(5.dp.toPx())
                    )
                }
                .background(Color.White, RoundedCornerShape(5.dp))) {
            AsyncImage(
                model = if (productInfo.productCard.imageUrl.isEmpty()) productInfo.productCard.imageUrl else painterResource(
                    id = R.drawable.ic_launcher_foreground
                ),
                contentDescription = "",
                modifier = Modifier
                    .height(135.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.FillBounds,
            )
            Text(
                text = productInfo.productCard.name,
                fontSize = 16.sp,
                color = colorResource(id = R.color.brown_medium),
                modifier = Modifier.padding(start = 11.dp, end = 11.dp, top = 10.dp),
                maxLines = 1
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = productInfo.productCard.price.toInt()
                        .toString() + stringResource(R.string.rubles_for_price),
                    fontSize = 15.sp,
                    color = colorResource(id = R.color.brown_dark),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 11.dp, top = 12.dp, bottom = 11.dp)
                )

                Row(modifier = Modifier.align(Alignment.CenterEnd)) {

                    IconButton(
                        onClick = { minusCount() },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_minus),
                            contentDescription = "",
                            tint = colorResource(id = R.color.brown_light),
                        )
                    }
                    Text(
                        text = productInfo.productCount.toString(),
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.brown_dark),
                        modifier = Modifier.align(Alignment.CenterVertically),
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        onClick = { plusCount() },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_plus),
                            contentDescription = "",
                            tint = colorResource(id = R.color.brown_light)
                        )
                    }
                }
            }
        }
    }
}

@PreviewScreenSizes
@Preview(showBackground = true)
@Composable
fun ProduceCardPreview() {
    val menu = CoffeeHouseMenu(id = 1, name = "Синабон_бон_бон_бон", price = 350.0f)
    val productInfo = ProductInfo(menu, 2u)
    ProductCard(Modifier
        .fillMaxSize()
        .shadow(2.dp, RoundedCornerShape(5.dp)),productInfo,{},{}
    )
}