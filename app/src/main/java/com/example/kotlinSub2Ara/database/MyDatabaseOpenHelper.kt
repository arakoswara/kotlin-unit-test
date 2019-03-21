package com.example.kotlinSub2Ara.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper (context: Context) : ManagedSQLiteOpenHelper (context, "Favorites.db", null, 1) {

    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance (context: Context) : MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(context.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            Favorite.MATCH_FAVORITE, true,
            Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Favorite.ID_EVENT to TEXT + UNIQUE,
            Favorite.STR_LEAGUE to TEXT,
            Favorite.STR_DATE to TEXT,
            Favorite.STR_HOME_TEAM to TEXT,
            Favorite.STR_AWAY_TEAM to TEXT,
            Favorite.INT_HOME_SCORE to TEXT,
            Favorite.INT_AWAY_SCORE to TEXT,
            Favorite.ID_HOME to TEXT,
            Favorite.ID_AWAY to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(Favorite.MATCH_FAVORITE, true)
    }
}

val Context.database: MyDatabaseOpenHelper
get() = MyDatabaseOpenHelper.getInstance(applicationContext)