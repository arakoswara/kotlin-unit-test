package com.example.kotlinSub2Ara.presenter

import com.example.kotlinSub2Ara.CoroutineContextProvider
import com.example.kotlinSub2Ara.EventView
import com.example.kotlinSub2Ara.model.EventList
import com.example.kotlinSub2Ara.api.ApiRepository
import com.example.kotlinSub2Ara.api.TheSportDbApi
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EventPresenter (private val view: EventView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getEventList(league: String?, idTarget: String) {

        GlobalScope.launch (context.main) {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDbApi.getEvents(league, idTarget)).await(),
                EventList::class.java
            )

            view.showEventList(data.events)
        }
    }
}