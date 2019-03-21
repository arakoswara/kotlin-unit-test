package com.example.kotlinSub2Ara.api

import com.example.kotlinSub2Ara.BuildConfig

object TheSportDbApi {

    fun getSearch(param: String?) : String {
        return BuildConfig.BASE_URL + "/api/v1/json/${BuildConfig.TSDB_API_KEY}/" + BuildConfig.SERACH + param
    }

    fun getEvents(league: String?, id: String?) : String {
        return BuildConfig.BASE_URL + "/api/v1/json/${BuildConfig.TSDB_API_KEY}/" + league +"?id=" + id
    }
}