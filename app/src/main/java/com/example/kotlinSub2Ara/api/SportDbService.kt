package com.example.kotlinSub2Ara.api

import com.example.kotlinSub2Ara.model.EventList
import com.example.kotlinSub2Ara.model.TeamDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface SportDbService {
    @GET
    fun getEvents(@Url url: String): Call<EventList>
    @GET
    fun getDetail(@Url url: String): Call<TeamDetail>
}