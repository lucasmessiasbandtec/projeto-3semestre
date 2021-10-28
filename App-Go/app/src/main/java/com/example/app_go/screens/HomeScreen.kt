package com.example.app_go.screens

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_go.R
import com.example.app_go.models.request.ArticleRequest
import com.example.app_go.models.request.AuthorRequest
import com.example.app_go.models.response.ResponseArticle
import com.example.app_go.sessionManager.SessionManager
import com.example.app_go.utils.PostList
import com.example.appclienterest.ConexaoApiGo
import com.jaeger.library.StatusBarUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.schedule


class HomeScreen() : AppCompatActivity() {

    var leaveApp: Boolean = false
    lateinit var pref: SharedPreferences
    val postList = PostList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        StatusBarUtil.setTranslucent(this)

        pref = getSharedPreferences("autenticacao", Context.MODE_PRIVATE)
        postList.list.clear()

        val apiGoTravel = ConexaoApiGo.criar()

        apiGoTravel.get(token = "Bearer ${SessionManager(this).fetchAuthToken()}")?.enqueue(object : Callback<ResponseArticle> {
            override fun onResponse(call: Call<ResponseArticle>, response: Response<ResponseArticle>) {
                response.body().let {
                    it?.data?.articles?.map {
                        val baseImage: String? = it.author.image
                        var decodedByte: Bitmap? = null

                        if (baseImage != null) {
                            val decodedString: ByteArray = Base64.decode(baseImage, Base64.DEFAULT)
                            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

                        } else {
                            decodedByte = BitmapFactory.decodeResource(resources, R.drawable.default_profile_photo)
                        }
                        val post = ArticleRequest(AuthorRequest("1", decodedByte, it.author.name, it.author.email), it.title, it.body, it.itinerary)

                        postList.addToHomeRV(post)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseArticle>, t: Throwable) {
                Toast.makeText(baseContext, "Não consegui conectar ao servidor :(", Toast.LENGTH_SHORT).show()
            }
        })

        val myRecyclerView: RecyclerView = findViewById(R.id.myRecyclerView)

        myRecyclerView.adapter = postList.adapter
        myRecyclerView.layoutManager = LinearLayoutManager(this)

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

}