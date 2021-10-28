package com.example.app_go.screens

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_go.R
import com.example.app_go.adapters.TripsAdapter
import com.example.app_go.models.Trip
import com.example.app_go.models.response.ResponseItinerary
import com.example.app_go.sessionManager.SessionManager
import com.example.appclienterest.ConexaoApiGo
import com.jaeger.library.StatusBarUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Post_tripSelector : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_trip_selector)
        StatusBarUtil.setTransparent(this)
        StatusBarUtil.setTranslucent(this)

        val apiGoTravel = ConexaoApiGo.criar()
        val tripList = mutableListOf<Trip>()
        val adapter = TripsAdapter(tripList)

        apiGoTravel.getItinerary(token = "Bearer ${SessionManager(this).fetchAuthToken()}").enqueue(object :
            Callback<ResponseItinerary> {
            override fun onResponse(call: Call<ResponseItinerary>, response: Response<ResponseItinerary>) {
                response.body()?.data?.itinerarys?.asReversed()?.map {

                    val itinerary = Trip(it.endPoint, it.filters, it.id, it.latitudeDestiny, it.latitudeStart, it.longitudeDestiny, it.longitudeStart, it.startingPoint)
                    tripList.add(itinerary)
                    adapter.notifyItemInserted(tripList.size - 1)

                }
            }

            override fun onFailure(call: Call<ResponseItinerary>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

        })

        val tripSelectorRecycler : RecyclerView = findViewById(R.id.TripSelectorRecycler)
        tripSelectorRecycler.adapter = adapter
        tripSelectorRecycler.layoutManager = LinearLayoutManager(this)
    }
}