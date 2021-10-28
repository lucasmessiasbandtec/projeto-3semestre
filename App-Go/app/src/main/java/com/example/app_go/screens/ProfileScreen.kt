package com.example.app_go.screens

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_go.R
import com.example.app_go.adapters.ProfileAdapter
import com.example.app_go.models.request.ArticleRequest
import com.example.app_go.models.request.AuthorRequest
import com.example.app_go.models.response.ResponseArticle
import com.example.app_go.models.response.ResponseUser
import com.example.app_go.sessionManager.SessionManager
import com.example.appclienterest.ConexaoApiGo
import com.jaeger.library.StatusBarUtil
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_screen)
        StatusBarUtil.setTranslucent(this)

        var profile_name : TextView = findViewById(R.id.tv_nome)
        var article_count : TextView = findViewById(R.id.tv_qtd_itinerarios)
        var profile_image : CircleImageView = findViewById(R.id.img_perfil)


        val apiGoTravel = ConexaoApiGo.criar()
        val articleList = mutableListOf<ArticleRequest>()

        val adapter = ProfileAdapter(articleList)

        apiGoTravel.getCurrentUser(token = "Bearer ${SessionManager(this).fetchAuthToken()}").enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                val baseImage = response.body()?.data?.user?.image
                val profileName = response.body()?.data?.user?.name
                var decodedByte: Bitmap? = null
                profile_name.text = profileName

                if(baseImage != null) {
                    val decodedString: ByteArray = Base64.decode(baseImage, Base64.DEFAULT)
                    decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

                }
                else {

                    decodedByte = BitmapFactory.decodeResource(resources, R.drawable.default_profile_photo)
                }
                profile_image.setImageBitmap(decodedByte)
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })

        apiGoTravel.getArticleMainUser(token = "Bearer ${SessionManager(this).fetchAuthToken()}")?.enqueue(object : Callback<ResponseArticle> {
            override fun onResponse(call: Call<ResponseArticle>, response: Response<ResponseArticle>) {
                response.body().let {

                    val qtd: String = it?.data?.articlesCount.toString()
                    article_count.text = qtd

                    it?.data?.articles?.asReversed()?.map {
                        val baseImage: String? = it.author.image
                        var decodedByte: Bitmap? = null

                        if (baseImage != null) {
                            val decodedString: ByteArray = Base64.decode(baseImage, Base64.DEFAULT)
                            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                        } else {
                            decodedByte = BitmapFactory.decodeResource(resources, R.drawable.default_profile_photo)
                        }
                        val post = ArticleRequest(AuthorRequest("1", decodedByte, it.author.name, it.author.email), it.title, it.body, it.itinerary)

                        articleList.add(post)
                        adapter.notifyItemInserted(articleList.size - 0)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseArticle>, t: Throwable) {
                Toast.makeText(baseContext, "Não consegui conectar ao servidor :(", Toast.LENGTH_SHORT).show()
            }
        })

        val userRv: RecyclerView = findViewById(R.id.user_rv)
        userRv.adapter = adapter
        userRv.layoutManager = LinearLayoutManager(this)
    }

    fun back_home(view: View) {val intent = Intent(this, HomeScreen::class.java)
        startActivity(intent)
    }

    fun back_last_trip(view: View) {val intent = Intent(this, LastTripScreen::class.java)
        startActivity(intent)}

    fun back_create_trip(view: View) {val intent = Intent(this, CreateTripScreen::class.java)
        startActivity(intent)}

    fun back_itinerario(view: View) {val intent = Intent(this, ItinerarioScreen::class.java)
        startActivity(intent)}

    fun loggout(view: View) {
        val sessionManager = SessionManager(this)
        sessionManager.loggout()
        val intent = Intent(this, LoginScreen::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finishAffinity()
    }
}