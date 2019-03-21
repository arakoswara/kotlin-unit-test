package com.example.kotlinSub2Ara.database

data class Favorite (
    val id: Int,
    val idEvent: String?,
    val strLeague: String?,
    val strHomeTeam: String?,
    val strAwayTeam: String?,
    val intHomeScore: String?,
    val intAwayScore: String?,
    val strDate: String?,
    val idHome: String?,
    val idAway: String?) {

    companion object {
        const val MATCH_FAVORITE: String = "MATCH_FAVORITE"
        const val ID: String = "ID_"
        const val ID_EVENT: String = "ID_EVENT"
        const val STR_LEAGUE: String = "STR_LEAGUE"
        const val STR_HOME_TEAM: String = "STR_HOME_TEAM"
        const val STR_AWAY_TEAM: String = "STR_AWAY_TEAM"
        const val INT_HOME_SCORE: String = "INT_HOME_SCORE"
        const val INT_AWAY_SCORE: String = "INT_AWAY_SCORE"
        const val STR_DATE: String = "STR_DATE"
        const val ID_HOME: String = "ID_HOME"
        const val ID_AWAY: String = "ID_AWAY"
    }
}