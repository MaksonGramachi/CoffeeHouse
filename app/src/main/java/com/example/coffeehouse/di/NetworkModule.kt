package com.example.coffeehouse.di


import com.example.coffeehouse.data.TokenDataStore
import com.example.coffeehouse.data.api.CoffeeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideAuthInterceptor(
        dataStore: TokenDataStore
    ) : Interceptor = Interceptor { chain ->
        runBlocking {
            val request = chain.request()

            val modifiedRequest = request.newBuilder()
                .apply {
                    if (!request.url.pathSegments.contains("auth")) {
                        header("Authorization", "Bearer ${dataStore.accessToken.first()}")
                    }
                }
                .build()
            chain.proceed(modifiedRequest)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: Interceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()



    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://212.41.30.90:35005/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): CoffeeApi {
        return retrofit.create(CoffeeApi::class.java)
    }
}