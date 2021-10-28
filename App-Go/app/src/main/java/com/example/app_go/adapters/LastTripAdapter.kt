package com.example.app_go.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.app_go.R
import com.example.app_go.models.Trip
import com.example.app_go.screens.HomeScreen
import com.example.app_go.screens.LastTripScreen
import com.example.app_go.screens.PostMapScreen


class LastTripAdapter(var last : List<Trip>): RecyclerView.Adapter<LastTripAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastTripAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_last_trip, parent, false))
    }

    override fun onBindViewHolder(holder: LastTripAdapter.ViewHolder, position: Int) {
        holder.apply {
            partida.text = last[position].startingPoint
            destino.text =last[position].endPoint
            postar
            linha
            btnMap
        }
    }

    override fun getItemCount(): Int {
        return last.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var partida : TextView = itemView.findViewById(R.id.tv_partida)
        var destino : TextView = itemView.findViewById(R.id.tv_destino)
        var postar : ImageButton = itemView.findViewById(R.id.ib_postar)
        var linha : TextView = itemView.findViewById(R.id.tv_line)
        var btnMap: Button = itemView.findViewById(R.id.btn_map_trip_last)

        init {
            btnMap.setOnClickListener {
                val c = itemView.context as LastTripScreen
                val intent = Intent(c, PostMapScreen::class.java)
                intent.putExtra("location1Lat", last[position].latitudeStart.toDouble())
                intent.putExtra("location1Lng", last[position].longitudeStart.toDouble())
                intent.putExtra("location2Lat", last[position].latitudeDestiny.toDouble())
                intent.putExtra("location2Lng", last[position].longitudeDestiny.toDouble())
                intent.putExtra("name", "you")
                startActivity(c, intent, null)
            }
        }
    }
}