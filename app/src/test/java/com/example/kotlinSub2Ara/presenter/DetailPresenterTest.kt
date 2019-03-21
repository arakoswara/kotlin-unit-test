package com.example.kotlinSub2Ara.presenter

import com.example.kotlinSub2Ara.DetailView
import com.example.kotlinSub2Ara.api.EventRepository
import com.example.kotlinSub2Ara.api.EventRepositoryCallback
import com.example.kotlinSub2Ara.model.Event
import com.example.kotlinSub2Ara.model.EventList
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailPresenterTest {

    @Mock
    private lateinit var view: DetailView

    @Mock
    private lateinit var presenter: DetailPresenter

    @Mock
    private lateinit var eventRepository: EventRepository

    @Mock
    private lateinit var response: Event

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        presenter = DetailPresenter(view, eventRepository)
    }

    @Test
    fun getDetail() {
        val id = "576781"

        presenter.getDetail(id)

        argumentCaptor<EventRepositoryCallback<EventList>>().apply {
            verify(eventRepository).getEventDetail(eq(id), capture())
            firstValue.onDataLoaded(response)
        }

        Mockito.verify(view).onDataLoaded(response)
    }
}