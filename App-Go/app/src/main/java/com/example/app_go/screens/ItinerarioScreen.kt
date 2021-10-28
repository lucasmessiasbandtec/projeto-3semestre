package com.example.app_go.screens

import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_go.R
import com.example.app_go.adapters.ItinerarioAdapter
import com.example.app_go.adapters.LastTripAdapter
import com.example.app_go.models.Filter
import com.example.app_go.models.Itinerary
import com.example.app_go.models.Trip
import com.example.app_go.models.response.ResponseItinerary
import com.example.app_go.sessionManager.SessionManager
import com.example.app_go.utils.GoogleMapDTO
import com.example.appclienterest.ConexaoApiGo
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.libraries.places.api.Places
import com.google.gson.Gson
import com.jaeger.library.StatusBarUtil
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItinerarioScreen : AppCompatActivity() {

    var itineraryList = mutableListOf<Trip>()
    val filterList = mutableListOf<Filter>()

    lateinit var btnMap:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itinerario_screen)
        StatusBarUtil.setTranslucent(this)

        val apiGoTravel = ConexaoApiGo.criar()
        btnMap = findViewById(R.id.btn_map_trip)

        var viagemAtual: Trip

        filterList.clear()

        apiGoTravel.getItinerary(token = "Bearer ${SessionManager(this).fetchAuthToken()}").enqueue(object : Callback<ResponseItinerary> {
            override fun onResponse(call: Call<ResponseItinerary>, response: Response<ResponseItinerary>) {
                response.body()?.data?.itinerarys?.map {
                    val itinerary = Trip(
                        it.endPoint,
                        it.filters,
                        it.id,
                        it.latitudeDestiny,
                        it.latitudeStart,
                        it.longitudeDestiny,
                        it.longitudeStart,
                        it.startingPoint
                    )
                    itineraryList.add(itinerary)
                }
                viagemAtual = itineraryList[itineraryList.size -1]
                for(i in viagemAtual.filters!!){
                    filterList.add(i)
                }

                val adapter = ItinerarioAdapter(filterList)
                val recycler : RecyclerView = findViewById(R.id.rvItinerary)
                recycler.adapter = adapter
                recycler.layoutManager = LinearLayoutManager(baseContext)

                btnMap.setOnClickListener{
                    val intent = Intent(baseContext, PostMapScreen::class.java)
                    intent.putExtra("location1Lat", viagemAtual.latitudeStart.toDouble())
                    intent.putExtra("location1Lng", viagemAtual.longitudeStart.toDouble())
                    intent.putExtra("location2Lat", viagemAtual.latitudeDestiny.toDouble())
                    intent.putExtra("location2Lng", viagemAtual.longitudeDestiny.toDouble())
                    intent.putExtra("name", "você")
                    ContextCompat.startActivity(baseContext, intent, null)
                }
            }

            override fun onFailure(call: Call<ResponseItinerary>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun back_home(view: View) {
        val intent = Intent(this, HomeScreen::class.java)
        startActivity(intent)
    }
    fun last_trip(view: View) {
        val intent = Intent(this, LastTripScreen::class.java)
        startActivity(intent)
    }

    fun profile(view: View) {
        val intent = Intent (this, ProfileScreen::class.java)
        startActivity(intent)
    }

    fun create_trip(view: View) {
        val intent = Intent(this, CreateTripScreen::class.java)
        startActivity(intent)
    }
}