package com.example.kotlinSub2Ara.Api

import com.example.kotlinSub2Ara.BuildConfig
import com.example.kotlinSub2Ara.api.SportDbService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SportDbRepository {
    fun create(): SportDbService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
        return retrofit.create(SportDbService::class.java)
    }
}