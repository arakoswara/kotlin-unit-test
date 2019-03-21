package com.example.kotlinSub2Ara

import com.example.kotlinSub2Ara.database.Favorite

interface FavoriteView {
    fun showFavorite(data: List<Favorite>)
}