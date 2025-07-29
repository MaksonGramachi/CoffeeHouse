package com.example.coffeehouse.ui.menu.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeehouse.R
import com.example.coffeehouse.domain.model.CoffeeHouseMenu
import com.example.coffeehouse.ui.menu.model.ProductInfo

@Composable
fun ProductOrderCard(
    modifier: Modifier = Modifier,
    productInfo: ProductInfo,
    plusCount: () -> Unit,
    minusCount: () -> Unit,
) {
    if(productInfo.productCard.price != null) {
        val productNameMeasurer = rememberTextMeasurer()
        val productNameStyle = TextStyle(fontSize = 18.sp, color = colorResource(id = R.color.brown_dark), fontWeight = FontWeight.Bold)
        val productNameLayoutResult = productNameMeasurer.measure(text = productInfo.productCard.name, style = productNameStyle)

        val priceMeasurer = rememberTextMeasurer()
        val priceStyle = TextStyle(fontSize = 16.sp, color = colorResource(id = R.color.brown_medium))
        val priceInfo = productInfo.productCard.price.toInt().toString() + stringResource(R.string.rubles_for_price)
        val priceLayoutResult = priceMeasurer.measure(text = priceInfo, style = priceStyle)

        val color = colorResource(id = R.color.brown_light)
        val shadowColor = colorResource(id = R.color.bottom_shadow)
        Box(modifier = modifier.drawBehind {
            drawRoundRect(
                color = shadowColor,
                topLeft = Offset(0f, 2.dp.toPx()),
                size = Size(size.width, size.height),
                cornerRadius = CornerRadius(5.dp.toPx())
            )
        }) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
            ) {
                drawRoundRect(
                    color = color,
                    cornerRadius = CornerRadius(x = 5.dp.toPx(), y = 5.dp.toPx())
                )
                drawText(
                    textMeasurer = productNameMeasurer,
                    text = productInfo.productCard.name,
                    style = productNameStyle,
                    topLeft = Offset(
                        x = 10.dp.toPx(),
                        y = center.y - center.y / 3 - productNameLayoutResult.size.height / 2,
                    )
                )

                drawText(
                    textMeasurer = priceMeasurer,
                    text = priceInfo,
                    style = priceStyle,
                    topLeft = Offset(
                        x = 10.dp.toPx(),
                        y = center.y + center.y / 3 - priceLayoutResult.size.height / 2,
                    )
                )
            }

            Row(modifier = modifier.align(Alignment.CenterEnd)) {
                IconButton(
                    onClick = {  minusCount() },
                    modifier = modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_minus),
                        contentDescription = "",
                        tint = colorResource(id = R.color.brown_dark),
                    )
                }
                Text(
                    text = productInfo.productCount.toString(),
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.brown_dark),
                    modifier = modifier.align(Alignment.CenterVertically),
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = { plusCount() },
                    modifier = modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_plus),
                        contentDescription = "",
                        tint = colorResource(id = R.color.brown_dark)
                    )
                }
            }
        }
    }
}


@PreviewScreenSizes
@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    ProductOrderCard(
        Modifier,
        ProductInfo(CoffeeHouseMenu(1, "Latte", "", 399f), 1u),
        {}, {})
}
