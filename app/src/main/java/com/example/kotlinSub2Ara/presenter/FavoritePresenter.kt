package com.example.kotlinSub2Ara.presenter

import com.example.kotlinSub2Ara.database.Favorite
import com.example.kotlinSub2Ara.database.MyDatabaseOpenHelper
import com.example.kotlinSub2Ara.FavoriteView
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync

class FavoritePresenter (private val view: FavoriteView) {
    fun getFavorite(database: MyDatabaseOpenHelper) {
        doAsync {
            database.use {
                val result = select(Favorite.MATCH_FAVORITE)
                val favorite = result.parseList(classParser<Favorite>())
                view.showFavorite(favorite)
            }
        }
    }
}