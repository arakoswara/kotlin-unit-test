package com.example.kotlinSub2Ara.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinSub2Ara.Api.SportDbRepository
import com.example.kotlinSub2Ara.BuildConfig
import com.example.kotlinSub2Ara.model.Event
import com.example.kotlinSub2Ara.model.TeamDetail
import com.example.kotlinSub2Ara.R
import com.squareup.picasso.Picasso
import retrofit2.Callback
import kotlinx.android.synthetic.main.item_list.view.*
import retrofit2.Call
import retrofit2.Response

class EventAdapter (
    internal var events: List<Event>,
    private val context: Context,
    private val listener : (Event) -> Unit
) :RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): EventViewHolder {
        return EventViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindEvent(events[position], listener)
    }

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindEvent(event: Event, listener: (Event) -> Unit) {

            val sportDbRepository = SportDbRepository.create()
            sportDbRepository.getDetail(BuildConfig.DETAIL_TEAM+event.idHomeTeam).enqueue(object: Callback<TeamDetail> {
                override fun onFailure(call: Call<TeamDetail>, t: Throwable) {}

                override fun onResponse(call: Call<TeamDetail>, response: Response<TeamDetail>) {
                    Picasso.get()
                        .load(response.body()?.teams?.get(0)?.strTeamBadge.toString())
                        .resize(50, 50)
                        .into(itemView.fbLogoOne)
                }
            })

            sportDbRepository.getDetail(BuildConfig.DETAIL_TEAM+event.idAwayTeam).enqueue(object: Callback<TeamDetail> {
                override fun onFailure(call: Call<TeamDetail>, t: Throwable) {}

                override fun onResponse(call: Call<TeamDetail>, response: Response<TeamDetail>) {
                    Picasso.get()
                        .load(response.body()?.teams?.get(0)?.strTeamBadge.toString())
                        .resize(50, 50)
                        .into(itemView.fbLogoTwo)
                }
            })

            itemView.leagueName.text = "${event.strLeague} - ${event.strDate}"
            itemView.fbNameOne.text = event.strHomeTeam
            itemView.ScoreOne.text = event.intHomeScore
            itemView.fbNameTwo.text = event.strAwayTeam
            itemView.ScoreTwo.text = event.intAwayScore

            itemView.setOnClickListener {listener(event)}
        }
    }
}