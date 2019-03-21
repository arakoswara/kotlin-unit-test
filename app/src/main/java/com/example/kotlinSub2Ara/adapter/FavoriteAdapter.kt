package com.example.kotlinSub2Ara.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinSub2Ara.Api.SportDbRepository
import com.example.kotlinSub2Ara.api.MyRetrofit
import com.example.kotlinSub2Ara.BuildConfig
import com.example.kotlinSub2Ara.database.Favorite
import com.example.kotlinSub2Ara.model.TeamDetail
import com.example.kotlinSub2Ara.R
import com.squareup.picasso.Picasso
import retrofit2.Callback
import kotlinx.android.synthetic.main.item_list.view.*
import retrofit2.Call
import retrofit2.Response

class FavoriteAdapter (
    private val events: List<Favorite>,
    private val context: Context,
    private val listener : (Favorite) -> Unit
) :RecyclerView.Adapter<FavoriteAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): EventViewHolder {
        return EventViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindEvent(events[position], listener)
    }

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindEvent(event: Favorite, listener: (Favorite) -> Unit) {

            val sportDbRepository = SportDbRepository.create()
            sportDbRepository.getDetail(BuildConfig.DETAIL_TEAM+event.idHome).enqueue(object: Callback<TeamDetail> {
                override fun onFailure(call: Call<TeamDetail>, t: Throwable) {}

                override fun onResponse(call: Call<TeamDetail>, response: Response<TeamDetail>) {
                    Picasso.get()
                        .load(response.body()?.teams?.get(0)?.strTeamBadge.toString())
                        .resize(50, 50)
                        .into(itemView.fbLogoOne)
                }
            })

            sportDbRepository.getDetail(BuildConfig.DETAIL_TEAM+event.idAway).enqueue(object: Callback<TeamDetail> {
                override fun onFailure(call: Call<TeamDetail>, t: Throwable) {}

                override fun onResponse(call: Call<TeamDetail>, response: Response<TeamDetail>) {
                    Picasso.get()
                        .load(response.body()?.teams?.get(0)?.strTeamBadge.toString())
                        .resize(50, 50)
                        .into(itemView.fbLogoTwo)
                }
            })

            itemView.leagueName.text = "${event.strLeague} - ${event.strHomeTeam}"
            itemView.fbNameOne.text = event.strAwayTeam
            itemView.ScoreOne.text = event.intAwayScore
            itemView.fbNameTwo.text = event.intHomeScore
            itemView.ScoreTwo.text = event.strDate

            itemView.setOnClickListener {listener(event)}
        }
    }
}