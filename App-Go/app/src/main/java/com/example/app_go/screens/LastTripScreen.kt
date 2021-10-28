package com.example.app_go.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_go.R
import com.example.app_go.adapters.LastTripAdapter
import com.example.app_go.models.Trip
import com.example.app_go.models.response.ResponseItinerary
import com.example.app_go.sessionManager.SessionManager
import com.example.appclienterest.ConexaoApiGo
import com.jaeger.library.StatusBarUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LastTripScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_last_trip_screen)
        StatusBarUtil.setTranslucent(this)

        val apiGoTravel = ConexaoApiGo.criar()

        val itineraryList = mutableListOf<Trip>()

        val adapter = LastTripAdapter(itineraryList)

        apiGoTravel.getItinerary(token = "Bearer ${SessionManager(this).fetchAuthToken()}").enqueue(object : Callback<ResponseItinerary> {
            override fun onResponse(call: Call<ResponseItinerary>, response: Response<ResponseItinerary>) {
                response.body()?.data?.itinerarys?.asReversed()?.map {
                    val itinerary = Trip(it.endPoint, it.filters, it.id, it.latitudeDestiny, it.latitudeStart, it.longitudeDestiny, it.longitudeStart, it.startingPoint)
                    itineraryList.add(itinerary)
                    adapter.notifyItemInserted(itineraryList.size - 1)
                }
            }

            override fun onFailure(call: Call<ResponseItinerary>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        val myRecyclerLastTrip : RecyclerView = findViewById(R.id.myRecyclerLastTrip)
        myRecyclerLastTrip.adapter = adapter
        myRecyclerLastTrip.layoutManager = LinearLayoutManager(this)
    }

    fun home_last(view: View){
        val intent = Intent(this, HomeScreen::class.java)
        startActivity(intent)
    }

    fun createTrip_last(view: View){
        val intent = Intent(this, CreateTripScreen::class.java)
        startActivity(intent)
    }

    fun itinerario_last(view: View){
        val intent = Intent(this, ItinerarioScreen::class.java)
        startActivity(intent)
    }

    fun post_last(view: View){
        val intent = Intent(this, PostScreen::class.java)
        startActivity(intent)
    }

    fun profile(view: View) {
        val intent = Intent (this, ProfileScreen::class.java)
        startActivity(intent)
    }

}