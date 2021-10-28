package com.example.appclienterest

import com.example.app_go.models.request.*
import com.example.app_go.models.response.ResponseUser
import com.example.app_go.models.response.ResponseArticle
import com.example.app_go.models.response.ResponseItinerary
import com.example.locaisproximos.Model.MyPlaces
import com.example.locaisproximos.Model.PlaceDetail
import retrofit2.Call
import retrofit2.http.*

interface ApiGo {

    @GET("article")
    fun get(@Header("Authorization") token: String ): Call<ResponseArticle>?

    @GET("article/feed/posts")
    fun getArticleMainUser(@Header("Authorization") token: String ): Call<ResponseArticle>?

    @POST("login")
    fun login(@Body request: LoginRequest): Call<Void>

    @POST("user")
    fun signup(@Body request : SignupRequest) : Call<Void>

    @GET("user")
    fun getCurrentUser(@Header("Authorization") token: String ) : Call<ResponseUser>

    @GET("itinerary")
    fun getItinerary(@Header("Authorization") token: String ) : Call<ResponseItinerary>

    @POST("article")
    fun createPost(@Header("Authorization") token: String, @Body request : NewArticleRequest) : Call<Void>

    @POST("itinerary")
    fun createTrip(@Header("Authorization") token: String, @Body request : NewTripRequest) : Call<Void>

    @GET
    fun getNearbyPlaces(@Url url:String) :Call<MyPlaces>

    @GET
    fun getDetailPlace(@Url url: String) :Call<PlaceDetail>
}