package com.example.kotlinSub2Ara

import android.support.v7.widget.SearchView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.kotlinSub2Ara.adapter.EventAdapter
import com.example.kotlinSub2Ara.model.Event
import com.example.kotlinSub2Ara.presenter.EventPresenter
import com.example.kotlinSub2Ara.api.ApiRepository
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(),
    EventView ,
    SearchView.OnQueryTextListener,
    MenuItem.OnActionExpandListener
{
    private lateinit var eventAdapter: EventAdapter
    private lateinit var presenter: EventPresenter
    private lateinit var eventName: String
    private lateinit var target: String
    private lateinit var idTarget: String
    private var menuItem: Menu? = null
    private val events: MutableList<Event> = mutableListOf()

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        return true
    }

    override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        getSearch(newText)

        return true
    }

    fun getSearch(text: String?) {
        val adapter = eventAdapter
        if (adapter != null) {
            adapter.events = events.filter {
                it.strHomeTeam!!.contains(text.toString().capitalize()) or it.strAwayTeam!!.contains(text.toString().capitalize())
            }
            if (adapter.itemCount == 0) {
                toast("No data")
            } else {
                adapter.notifyDataSetChanged()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        club_list.layoutManager = LinearLayoutManager(this)
        eventAdapter = EventAdapter(events, this) {
            startActivity<DetailEvent>("idEvent" to it.idEvent)
        }
        club_list.adapter = eventAdapter

        val spinnerItem = resources.getStringArray(R.array.menu)
        val spinnerAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, spinnerItem)
        spinner.adapter = spinnerAdapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = EventPresenter(this, request, gson)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                eventName = spinner.selectedItem.toString()

                if (eventName == "Next Match") {
                    target = BuildConfig.NEXT_MATCH
                    idTarget = "4328"
                }
                else if (eventName == "Last Match")
                {
                    target = BuildConfig.LAST_MATCH
                    idTarget = "4328"
                }
                else if (eventName == "Favorite")
                {
                    startActivity<Favorite>()
                }

                 presenter.getEventList(target, idTarget)
            }
        }
    }

    override fun showEventList(data: List<Event>) {
        events.clear()
        events.addAll(data)
        eventAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        menuItem = menu

        val searchItem = menu?.findItem(R.id.search)
        MenuItemCompat.setShowAsAction(
            searchItem,
            MenuItemCompat.SHOW_AS_ACTION_ALWAYS or MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)

        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setOnQueryTextListener(this)

        super.onCreateOptionsMenu(menu)

        return true
    }
}
