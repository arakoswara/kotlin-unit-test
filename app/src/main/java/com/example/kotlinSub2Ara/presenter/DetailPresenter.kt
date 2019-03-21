package com.example.kotlinSub2Ara.presenter

import com.example.kotlinSub2Ara.DetailView
import com.example.kotlinSub2Ara.api.EventRepository
import com.example.kotlinSub2Ara.api.EventRepositoryCallback
import com.example.kotlinSub2Ara.model.Event
import com.example.kotlinSub2Ara.model.EventList

class DetailPresenter (private val view: DetailView, private val eventRepository: EventRepository) {
    fun getDetail(id: String) {
        eventRepository
            .getEventDetail(id, object : EventRepositoryCallback<EventList> {
            override fun onDataLoaded(data: Event) {
                view.onDataLoaded(data)
            }

            override fun onDataError() {
                view.onDataError()
            }
        })
    }
}