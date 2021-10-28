package com.example.app_go.adapters

import android.app.Activity
import android.app.RemoteInput
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.app_go.R
import com.example.app_go.models.Trip
import com.example.app_go.screens.PostScreen
import com.example.app_go.screens.Post_tripSelector

class TripsAdapter(var trips: List<Trip>): RecyclerView.Adapter<TripsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_trip_selector, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TripsAdapter.ViewHolder, position: Int) {
        holder.apply {
            start.text = trips[position].startingPoint
            end.text = trips[position].endPoint
            locationLogo.setImageResource(R.drawable.ic_baseline_location_on_24)
        }
    }

    override fun getItemCount(): Int {
        return trips.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var start : TextView = itemView.findViewById(R.id.tv_trip_start)
        var end : TextView = itemView.findViewById(R.id.tv_trip_end)
        var locationLogo : ImageView = itemView.findViewById(R.id.iv_location)

        init {
            itemView.setOnClickListener {
                val c = itemView.context as Post_tripSelector
                val intent = Intent(c, PostScreen::class.java)
                val id = trips[position].id
                intent.putExtra("idTrip" , id)
                startActivity(c, intent, null)
            }
        }
    }

}