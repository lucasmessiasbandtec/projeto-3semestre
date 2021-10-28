package com.example.app_go.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_go.R
import com.example.app_go.models.Filter
import com.example.app_go.models.Itinerary
import com.example.app_go.screens.HomeScreen
import com.example.app_go.screens.ItinerarioScreen
import com.squareup.picasso.Picasso

class ItinerarioAdapter(var trip : List<Filter>): RecyclerView.Adapter<ItinerarioAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItinerarioAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_itenerario, parent, false))
    }

    override fun onBindViewHolder(holder: ItinerarioAdapter.ViewHolder, position: Int) {
        holder.apply {
            titulo.text = trip[position].name
            rua.text =trip[position].longitude
            val url = trip[position].image.toString()
            val c = itemView.context as ItinerarioScreen
            Picasso.with(c).load(url).resize(132, 72).into(mapa)
        }
    }

    override fun getItemCount(): Int {
        return trip.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titulo : TextView = itemView.findViewById(R.id.tv_nome_viagem)
        var rua : TextView = itemView.findViewById(R.id.tv_nome_local)
        var mapa : ImageView = itemView.findViewById(R.id.iv_map_stop)
    }
}