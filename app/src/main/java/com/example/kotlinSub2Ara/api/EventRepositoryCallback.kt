package com.example.kotlinSub2Ara.api

import com.example.kotlinSub2Ara.model.Event

interface EventRepositoryCallback<T> {
    fun onDataLoaded(data: Event)
    fun onDataError()
}