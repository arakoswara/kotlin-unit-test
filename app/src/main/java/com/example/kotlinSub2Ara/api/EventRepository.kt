package com.example.kotlinSub2Ara.api

import com.example.kotlinSub2Ara.BuildConfig
import com.example.kotlinSub2Ara.model.EventList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventRepository {
    fun getEventDetail(id: String, callback: EventRepositoryCallback<EventList>) {
        MyRetrofit
            .createService(SportDbService::class.java)
            .getEvents(BuildConfig.DETAIL_MATCH + id)
            .enqueue(object : Callback<EventList?> {
                override fun onFailure(call: Call<EventList?>, t: Throwable) {
                    callback.onDataError()
                }

                override fun onResponse(call: Call<EventList?>, response: Response<EventList?>) {
                    response?.let {
                        if (it.isSuccessful) {
                            it.body()?.events?.get(0)?.let { it1 -> callback.onDataLoaded(it1) }
                        } else {
                            callback.onDataError()
                        }
                    }
                }
            })
    }
}