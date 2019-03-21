package com.example.kotlinSub2Ara

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.MenuItem
import com.example.kotlinSub2Ara.`interface`.SearchInfView
import com.example.kotlinSub2Ara.adapter.EventAdapter
import com.example.kotlinSub2Ara.api.ApiRepository
import com.example.kotlinSub2Ara.model.Event
import com.example.kotlinSub2Ara.presenter.SearchPresenter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.startActivity

class SearchActivity : AppCompatActivity(),
    SearchInfView,
    SearchView.OnQueryTextListener,
    MenuItem.OnActionExpandListener {

    private lateinit var searchPresenter: SearchPresenter



    override fun onQueryTextSubmit(p0: String?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getList(data: List<Event>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
