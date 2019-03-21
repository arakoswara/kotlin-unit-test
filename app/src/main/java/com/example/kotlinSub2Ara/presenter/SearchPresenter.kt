package com.example.kotlinSub2Ara.presenter

import android.util.Log
import com.example.kotlinSub2Ara.CoroutineContextProvider
import com.example.kotlinSub2Ara.EventView
import com.example.kotlinSub2Ara.api.SearchRepository
import com.example.kotlinSub2Ara.api.TheSportDbApi
import com.example.kotlinSub2Ara.model.EventList
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchPresenter (private val view: EventView,
                       private val apiRepository: SearchRepository,
                       private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getList(param: String?) {
        GlobalScope.launch (context.main) {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDbApi.getSearch(param)).await(),
                EventList::class.java
            )

            Log.d("DATA ", data.toString())
        }
    }
}