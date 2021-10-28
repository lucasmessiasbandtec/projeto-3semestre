package com.example.app_go.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.Touch
import android.view.View
import android.widget.Toast
import com.example.app_go.R
import com.example.app_go.utils.CommonMaps
import com.example.appclienterest.ApiGo
import com.example.locaisproximos.Model.PlaceDetail
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_view_place.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

class ViewPlaceScreen : AppCompatActivity() {

    lateinit var mService:ApiGo
    var mPlace:PlaceDetail?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_place)

        //Init service
        mService = CommonMaps.googleApiService

        //Set empty for all text view
        place_name.text=""
        place_address.text=""
        place_open_hour.text=""

        if(CommonMaps.currentResult!!.photos != null && CommonMaps.currentResult!!.photos!!.isNotEmpty()){
            Picasso.with(this).load(getPhotoOfPlace(CommonMaps.currentResult!!.photos!![0].photo_reference!!, 1000)).into(photo)
        }


        //Load rating
        rating_bar.rating = CommonMaps.currentResult!!.rating.toFloat()
        place_name.text = CommonMaps.currentResult!!.name.toString()

        if (CommonMaps.currentResult!!.formatted_address == null){
            place_address.text = ""
        }
        else{
            place_address.text = CommonMaps.currentResult!!.formatted_address.toString()
        }

        //Load open hours
        if(CommonMaps.currentResult!!.opening_hours != null)
            place_open_hour.text="Open Now : "+ CommonMaps.currentResult!!.opening_hours!!.open_now
        else
            place_open_hour.visibility = View.GONE


        btn_add_place.setOnClickListener {
            val i = Intent (this, CreateTripScreen::class.java)

            if(CommonMaps.currentResult!!.photos!!.isNullOrEmpty()){
                i.putExtra("photo",  getPhotoOfPlace("", 1000))
            }else{
                i.putExtra("photo", getPhotoOfPlace(CommonMaps.currentResult!!.photos!![0].photo_reference!!, 1000))
            }
            i.putExtra("name", CommonMaps.currentResult!!.name.toString())
            i.putExtra("address", CommonMaps.currentResult!!.formatted_address.toString())
            setResult(RESULT_OK, i)
            finish()
        }

    }

    private fun getDetailPlaceUrl(place_id: String): String {

        val url = StringBuilder("https://maps.googleapis.com/maps/api/place/details/json")
        url.append("?place_id=$place_id")
        url.append("&key=AIzaSyCtrLnvf05yT8XXuk19chqOXp8egn7aPHA")
        return url.toString()

    }

    private fun getPhotoOfPlace(photo_reference: String, maxWidth: Int): String {

        val url = StringBuilder("https://maps.googleapis.com/maps/api/place/photo")
        url.append("?maxwidth=$maxWidth")
        url.append("&photoreference=$photo_reference")
        url.append("&key=AIzaSyCtrLnvf05yT8XXuk19chqOXp8egn7aPHA")
        return url.toString()

    }
}