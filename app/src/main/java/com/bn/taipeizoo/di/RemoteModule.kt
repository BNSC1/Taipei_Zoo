package com.bn.taipeizoo.di

import com.bn.taipeizoo.conf.NetworkConfig
import com.bn.taipeizoo.data.remote.TaipeiZooApiService
import com.bn.taipeizoo.util.remote.DebugInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(DebugInterceptor())
            .connectTimeout(NetworkConfig.API_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(NetworkConfig.API_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NetworkConfig.API_TIMEOUT, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideTaipeiZooApiService(
        gson: Gson,
        okHttpClient: OkHttpClient,
    ): TaipeiZooApiService =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(NetworkConfig.TAIPEI_ZOO_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(TaipeiZooApiService::class.java)
}