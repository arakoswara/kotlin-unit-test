package com.example.kotlinSub2Ara

import com.example.kotlinSub2Ara.model.Event

interface EventView {
    fun showEventList(data: List<Event>)
}