package com.example.kotlinSub2Ara

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

class TestContextProvider : CoroutineContextProvider() {
    @ExperimentalCoroutinesApi
    override val main: CoroutineDispatcher = Dispatchers.Unconfined

}