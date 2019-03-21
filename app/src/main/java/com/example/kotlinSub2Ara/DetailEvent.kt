package com.example.kotlinSub2Ara

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.example.kotlinSub2Ara.Api.SportDbRepository
import com.example.kotlinSub2Ara.api.EventRepository
import com.example.kotlinSub2Ara.database.Favorite
import com.example.kotlinSub2Ara.database.database
import com.example.kotlinSub2Ara.model.Event
import com.example.kotlinSub2Ara.model.TeamDetail
import com.example.kotlinSub2Ara.presenter.DetailPresenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_event.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailEvent : AppCompatActivity(), DetailView {

    private lateinit var presenter: DetailPresenter
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var events: Event
    private lateinit var idEvent: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        idEvent = intent.getStringExtra("idEvent")
        presenter = DetailPresenter(this, EventRepository())
        presenter.getDetail(idEvent)

        checkFavorite(idEvent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite, menu)
        menuItem = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.favorite -> {

                toast(isFavorite.toString())
                if (isFavorite) {
                    removeFromFavorite()
                } else {
                    addToFavorite()
                }

                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onDataError() {

    }

    override fun onDataLoaded(data: Event) {
        events = data
        setFavorite()

        val oldValue = "; "
        val oldValue2 = ";"
        val newValue = "\n"

        infoText.text = "${data.strLeague} - ${data.strDate}"
        clubNameHome.text = data.strHomeTeam
        clubNameAway.text = data.strAwayTeam
        yellowHome.text = data.strHomeYellowCards.toString().replace(oldValue2, newValue)
        yellowAway.text = data.strAwayYellowCards.toString().replace(oldValue2, newValue)
        redHome.text = data.strHomeRedCards.toString().replace(oldValue2, newValue)
        redAway.text = data.strAwayRedCards.toString().replace(oldValue2, newValue)
        goalHome.text = data.strHomeGoalDetails.toString().replace(oldValue2, newValue)
        goalAway.text = data.strAwayGoalDetails.toString().replace(oldValue2, newValue)
        shotHome.text = data.intHomeShots.toString()
        shotAway.text = data.intAwayShots.toString()
        keeperHome.text = data.strHomeLineupGoalkeeper.toString().replace(oldValue, newValue)
        keeperAway.text = data.strAwayLineupGoalkeeper.toString().replace(oldValue, newValue)
        DefenderHome.text = data.strHomeLineupDefense.toString().replace(oldValue, newValue)
        DefenderAway.text = data.strAwayLineupDefense.toString().replace(oldValue, newValue)
        midFieldHome.text = data.strHomeLineupMidfield.toString().replace(oldValue, newValue)
        midFieldAway.text = data.strAwayLineupMidfield.toString().replace(oldValue, newValue)
        forwardHome.text = data.strHomeLineupForward.toString().replace(oldValue, newValue)
        forwardAway.text = data.strAwayLineupForward.toString().replace(oldValue, newValue)
        subHome.text = data.strHomeLineupSubstitutes.toString().replace(oldValue, newValue)
        subAway.text = data.strAwayLineupSubstitutes.toString().replace(oldValue, newValue)

        if (data.intHomeScore != null && data.intAwayScore != null) {
            score.text = "${data.intHomeScore} : ${data.intAwayScore}"
        }


        val sportDbRepository = SportDbRepository.create()
        sportDbRepository.getDetail(BuildConfig.DETAIL_TEAM+data.idHomeTeam).enqueue(object:
            Callback<TeamDetail> {
            override fun onFailure(call: Call<TeamDetail>, t: Throwable) {}

            override fun onResponse(call: Call<TeamDetail>, response: Response<TeamDetail>) {
                Picasso.get()
                    .load(response.body()?.teams?.get(0)?.strTeamBadge.toString())
                    .resize(100, 100)
                    .into(homeLogo)
            }
        })

        sportDbRepository.getDetail(BuildConfig.DETAIL_TEAM + data.idAwayTeam).enqueue(object:
            Callback<TeamDetail> {
            override fun onFailure(call: Call<TeamDetail>, t: Throwable) {}

            override fun onResponse(call: Call<TeamDetail>, response: Response<TeamDetail>) {
                Picasso.get()
                    .load(response.body()?.teams?.get(0)?.strTeamBadge.toString())
                    .resize(100, 100)
                    .into(logoAway)
            }
        })
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    Favorite.MATCH_FAVORITE,
                    Favorite.ID_EVENT to events.idEvent,
                    Favorite.STR_LEAGUE to events.strLeague,
                    Favorite.STR_HOME_TEAM to events.strHomeTeam,
                    Favorite.STR_AWAY_TEAM to events.strAwayTeam,
                    Favorite.INT_HOME_SCORE to events.intHomeScore,
                    Favorite.INT_AWAY_SCORE to events.intAwayScore,
                    Favorite.STR_DATE to events.strDate,
                    Favorite.ID_HOME to events.idHomeTeam,
                    Favorite.ID_AWAY to events.idAwayTeam
                )
            }
            toast(R.string.add_to_favorite)
        }
        catch (e : SQLiteConstraintException) {
            toast(e.localizedMessage)
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(Favorite.MATCH_FAVORITE,
                    "ID_EVENT = {id}",
                    "id" to idEvent)
            }
            toast(R.string.remove_from_favorite)
        }
        catch (e: SQLiteConstraintException)
        {
            toast(e.localizedMessage)
        }
    }

    private fun checkFavorite(id_event: String) {
        database.use {
            val result = select(Favorite.MATCH_FAVORITE).whereArgs("(ID_EVENT = {id})", "id" to id_event.toInt())
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun setFavorite() {
        if (isFavorite) {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_favorite)
        }else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star)
        }
    }
}
