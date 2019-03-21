package com.example.kotlinSub2Ara.api

import com.example.kotlinSub2Ara.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyRetrofit {
    fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    fun <T> createService(service: Class<T>): T {
        return initRetrofit().create(service)
    }
}