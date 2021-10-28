package com.example.app_go.screens

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.app_go.R
import com.example.app_go.models.Trip
import com.example.app_go.models.request.NewArticleRequest
import com.example.app_go.models.request.NewTripRequest
import com.example.app_go.models.response.ResponseUser
import com.example.app_go.sessionManager.SessionManager
import com.example.appclienterest.ConexaoApiGo
import com.jaeger.library.StatusBarUtil
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PostScreen : AppCompatActivity() {
    lateinit var returnIntentId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_screen)
        StatusBarUtil.setTransparent(this)
        StatusBarUtil.setTranslucent(this)

        var profileImage : CircleImageView = findViewById(R.id.profile_image)
        var profileNameText : TextView = findViewById(R.id.profile_name)

        returnIntentId = intent.getStringExtra("idTrip").toString()

        val apiGoTravel = ConexaoApiGo.criar()

        apiGoTravel.getCurrentUser(token = "Bearer ${SessionManager(this).fetchAuthToken()}").enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                val baseImage = response.body()?.data?.user?.image
                val profileName = response.body()?.data?.user?.name
                var decodedByte: Bitmap? = null

                if(baseImage != null) {
                    val decodedString: ByteArray = Base64.decode(baseImage, Base64.DEFAULT)
                    decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

                }
                else {

                    decodedByte = BitmapFactory.decodeResource(resources, R.drawable.default_profile_photo)
                }
                profileImage.setImageBitmap(decodedByte)
                profileNameText.text = profileName
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun back_home(view: View) {
        val intent = Intent(this, HomeScreen::class.java)
        startActivity(intent)
    }

    fun addTrip(view: View) {
        val intent = Intent(this, Post_tripSelector::class.java)
        startActivity(intent)
    }


    fun publish(view: View) {
        val intent = Intent(this, HomeScreen::class.java)

        val title: EditText = findViewById(R.id.et_title)
        val body: EditText = findViewById(R.id.et_body)

        val apiGoTravel = ConexaoApiGo.criar()

        val newpost = NewArticleRequest(title = title.text.toString(),"Bla", body = body.text.toString(), itinerary = returnIntentId )

        apiGoTravel.createPost(token = "Bearer ${SessionManager(this).fetchAuthToken()}", newpost).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code().toString() == "200") {
                    Toast.makeText(baseContext, "Publicado com sucesso", Toast.LENGTH_SHORT).show()
                    startActivity(intent)

                } else {
                    Toast.makeText(baseContext, "Ocorreu um erro ao publicar", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(baseContext, "Ocorreu um erro ao se conectar com a API", Toast.LENGTH_SHORT).show()
            }
        })
        }

    }