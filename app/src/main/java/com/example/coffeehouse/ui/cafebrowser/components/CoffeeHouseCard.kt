package com.example.coffeehouse.ui.cafebrowser.components

import android.location.Location
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
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
import com.example.coffeehouse.domain.model.CoffeeHouse

@Composable
fun CoffeeHouseCard(
    modifier: Modifier = Modifier,
    coffeeHouse: CoffeeHouse,
    currentPoint: CoffeeHouse.Point?,
) {

    val labelMeasurer = rememberTextMeasurer()
    val labelStyle = TextStyle(
        fontSize = 18.sp,
        color = colorResource(id = R.color.brown_dark),
        fontWeight = FontWeight.Bold
    )
    val textLayoutResult = labelMeasurer.measure(text = coffeeHouse.name, style = labelStyle)

    val descMeasurer = rememberTextMeasurer()
    val descStyle = TextStyle(fontSize = 14.sp, color = colorResource(id = R.color.brown_medium))
    var distanceInMeters = ""

    if (currentPoint == null || currentPoint.latitude == null || currentPoint.longitude == null ||
        coffeeHouse.point == null || coffeeHouse.point.latitude == null || coffeeHouse.point.longitude == null
    ) {
        distanceInMeters = stringResource(R.string.placeholder_for_distance)
    } else {
        val currentLocation = Location("currentPoint").apply {
            latitude = currentPoint.latitude
            longitude = currentPoint.longitude
        }
        val coffeeHouseLocation = Location("coffeeHousePoint").apply {
            latitude = coffeeHouse.point.latitude
            longitude = coffeeHouse.point.longitude
        }
        distanceInMeters =
            currentLocation.distanceTo(coffeeHouseLocation).toInt().toString() + stringResource(
                R.string.meters_for_distance
            )
    }
    val descLayoutResult = descMeasurer.measure(text = distanceInMeters, style = descStyle)
    val color = colorResource(id = R.color.brown_light)

    val shadowColor = colorResource(id = R.color.bottom_shadow)
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .drawBehind {
                drawRoundRect(
                    color = shadowColor,
                    topLeft = Offset(0f, 2.dp.toPx()),
                    size = Size(size.width, size.height),
                    cornerRadius = CornerRadius(5.dp.toPx())
                )
            }
    ) {
        drawRoundRect(
            color = color,
            cornerRadius = CornerRadius(x = 5.dp.toPx(), y = 5.dp.toPx())
        )

        drawText(
            textMeasurer = labelMeasurer,
            text = coffeeHouse.name,
            style = labelStyle,
            topLeft = Offset(
                x = 10.dp.toPx(),
                y = center.y - center.y / 3 - textLayoutResult.size.height / 2,
            )
        )

        drawText(
            textMeasurer = descMeasurer,
            text = distanceInMeters,
            style = descStyle,
            topLeft = Offset(
                x = 10.dp.toPx(),
                y = center.y + center.y / 3 - descLayoutResult.size.height / 2,
            )
        )
    }
}

@PreviewScreenSizes
@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    CoffeeHouseCard(Modifier.padding(bottom =6 .dp), CoffeeHouse(1,"Sinabon", null),null)
}