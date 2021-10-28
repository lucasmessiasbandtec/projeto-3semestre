package com.example.app_go.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.app_go.R
import com.example.app_go.models.request.ArticleRequest
import com.example.app_go.screens.HomeScreen
import com.example.app_go.screens.PostMapScreen
import com.example.app_go.screens.PostScreen
import com.example.app_go.screens.Post_tripSelector
import com.like.LikeButton
import de.hdodenhof.circleimageview.CircleImageView


class HomeAdapter(var posts : List<ArticleRequest>): RecyclerView.Adapter<HomeAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false))
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.apply {
            profileName.text = posts[position].author.name
            postTitle.text = posts[position].title
            postDesc.text = posts[position].body
            profilePhoto.setImageBitmap(posts[position].author.image)
            line.text
            btnMap
            likeButton.isLiked
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var profilePhoto : CircleImageView = itemView.findViewById(R.id.iv_profile_photo)
        var profileName: TextView = itemView.findViewById(R.id.tv_profile_name)
        var postTitle: TextView = itemView.findViewById(R.id.tv_post_title)
        var postDesc: TextView = itemView.findViewById(R.id.tv_post_desc)
        var line: TextView = itemView.findViewById(R.id.tv_line)
        var btnMap: Button = itemView.findViewById(R.id.btn_map_trip)
        var likeButton: LikeButton = itemView.findViewById(R.id.like_button)

        init {
            btnMap.setOnClickListener {
                val c = itemView.context as HomeScreen
                val intent = Intent(c, PostMapScreen::class.java)
                intent.putExtra("location1Lat", posts[position].itinerary?.latitudeStart?.toDouble())
                intent.putExtra("location1Lng", posts[position].itinerary?.longitudeStart?.toDouble())
                intent.putExtra("location2Lat", posts[position].itinerary?.latitudeDestiny?.toDouble())
                intent.putExtra("location2Lng", posts[position].itinerary?.longitudeDestiny?.toDouble())
                intent.putExtra("name", posts[position].author.name)
                startActivity(c, intent, null)
            }
        }
    }
}