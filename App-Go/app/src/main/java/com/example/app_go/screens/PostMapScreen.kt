package com.example.app_go.screens

import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.app_go.R
import com.example.app_go.utils.GoogleMapDTO
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.libraries.places.api.Places
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class PostMapScreen : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_map_screen)

        val tv_title: TextView = findViewById(R.id.map_title)

        val authorName = intent.getStringExtra("name")
        tv_title.text = (resources.getString(R.string.veja_a_viagem_postada) + " $authorName")

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapPostTrip) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            map = it

            Places.initialize(this, "AIzaSyCtrLnvf05yT8XXuk19chqOXp8egn7aPHA")

            val location1Lat = intent.getDoubleExtra("location1Lat", 0.0)
            val location1Lng = intent.getDoubleExtra("location1Lng", 0.0)

            val location1 = LatLng(location1Lat, location1Lng)

            map.addMarker(MarkerOptions().position(location1).title("Você está aqui"))
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location1, 10f))

            val location2Lat = intent.getDoubleExtra("location2Lat", 0.0)
            val location2Lng = intent.getDoubleExtra("location2Lng", 0.0)

            val location2 = LatLng(location2Lat, location2Lng)

            map.addMarker(MarkerOptions().position(location2).title("Você irá para cá"))
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location2, 10f))


            val URL = getDirectionURL(location1,location2)

            GetDirection(URL).execute()
        })

    }

    fun getDirectionURL(origin:LatLng,dest:LatLng) : String{
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${dest.latitude},${dest.longitude}&key=AIzaSyCtrLnvf05yT8XXuk19chqOXp8egn7aPHA&sensor=false&mode=driving"
    }

    private inner class GetDirection(val url : String) : AsyncTask<Void, Void, List<List<LatLng>>>(){
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body()!!.string()
            Log.d("GoogleMap" , " data : $data")
            val result =  ArrayList<List<LatLng>>()
            try{
                val respObj = Gson().fromJson(data, GoogleMapDTO::class.java)

                val path =  ArrayList<LatLng>()

                for (i in 0..(respObj.routes[0].legs[0].steps.size-1)){
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
            }catch (e:Exception){
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineoption = PolylineOptions()
            for (i in result.indices){
                lineoption.addAll(result[i])
                lineoption.width(10f)
                lineoption.color(Color.BLUE)
                lineoption.geodesic(true)
            }
            map.addPolyline(lineoption)
        }
    }

    fun decodePolyline(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val latLng = LatLng((lat.toDouble() / 1E5),(lng.toDouble() / 1E5))
            poly.add(latLng)
        }

        return poly
    }


    fun post(view: View) {
        val intent = Intent(this, PostScreen::class.java)
        startActivity(intent)
    }

    fun create_trip(view: View) {
        val intent = Intent(this, CreateTripScreen::class.java)
        startActivity(intent)
    }

    fun last_trip(view: View) {
        val intent = Intent(this, LastTripScreen::class.java)
        startActivity(intent)
    }

    fun itinerari(view: View) {
        val intent = Intent(this, ItinerarioScreen::class.java)
        startActivity(intent)
    }

    fun profile(view: View) {
        val intent = Intent(this, ProfileScreen::class.java)
        startActivity(intent)
    }

    override fun onMapReady(googleMaps: GoogleMap) {
        map = googleMaps
    }
}