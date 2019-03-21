package com.example.kotlinSub2Ara

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.kotlinSub2Ara.adapter.FavoriteAdapter
import com.example.kotlinSub2Ara.database.Favorite
import com.example.kotlinSub2Ara.database.database
import com.example.kotlinSub2Ara.presenter.FavoritePresenter
import kotlinx.android.synthetic.main.activity_favorite.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh

class Favorite : AppCompatActivity(), FavoriteView {

    private lateinit var presenter: FavoritePresenter
    private lateinit var favAdapter: FavoriteAdapter
    private val favorites: MutableList<Favorite> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        club_list.layoutManager = LinearLayoutManager(this)
        favAdapter = FavoriteAdapter(favorites, this) {
            startActivity<DetailEvent>(
                "idEvent" to it.idEvent
            )
        }

        swipe.onRefresh {
            presenter.getFavorite(database)
        }

        club_list.adapter = favAdapter
        presenter = FavoritePresenter(this)
        presenter.getFavorite(database)
    }

    override fun showFavorite(data: List<Favorite>) {
        swipe.isRefreshing = false

        favorites.clear()
        favorites.addAll(data)
        favAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        presenter.getFavorite(database)
    }
}
