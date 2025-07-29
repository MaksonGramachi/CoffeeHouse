package com.example.coffeehouse.data.model

import com.google.gson.annotations.SerializedName

data class CoffeeHouseMenuDto (
    @SerializedName("id")
    val id: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("price")
    val price: Float?,
)