package com.example.kotlinSub2Ara.presenter

import com.example.kotlinSub2Ara.BuildConfig
import com.example.kotlinSub2Ara.CoroutineContextProvider
import com.example.kotlinSub2Ara.EventView
import com.example.kotlinSub2Ara.TestContextProvider
import com.example.kotlinSub2Ara.api.ApiRepository
import com.example.kotlinSub2Ara.model.Event
import com.example.kotlinSub2Ara.model.EventList
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.*

class EventPresenterTest {

    @Mock
    private lateinit var view: EventView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var presenter: EventPresenter

    @Mock
    private lateinit var apiResponse: Deferred<String>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = EventPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getEventList() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventList(events)
        val league = BuildConfig.NEXT_MATCH
        val idLeague = "4328"

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson("", EventList::class.java)
            ).thenReturn(response)

            presenter.getEventList(league, idLeague)
            Mockito.verify(view).showEventList(events)
        }
    }

    @Test
    fun getEventListPast() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventList(events)
        val league = BuildConfig.LAST_MATCH
        val idLeague = "4328"

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson("", EventList::class.java)
            ).thenReturn(response)

            presenter.getEventList(league, idLeague)
            Mockito.verify(view).showEventList(events)
        }
    }
}