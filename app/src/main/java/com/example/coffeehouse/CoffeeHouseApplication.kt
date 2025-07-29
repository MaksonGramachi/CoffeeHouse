package com.example.coffeehouse

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CoffeeHouseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("58f766be-5c8a-48cb-a34c-9a2c2702eb77")
        MapKitFactory.initialize(this)
    }
}
