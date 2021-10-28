package com.example.app_go.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_go.R
import com.example.app_go.models.request.ArticleRequest
import de.hdodenhof.circleimageview.CircleImageView

class ProfileAdapter(var postsUser : List<ArticleRequest>): RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_posts_profile, parent, false))
    }

    override fun onBindViewHolder(holder: ProfileAdapter.ViewHolder, position: Int) {
        holder.apply {
            profileName.text = postsUser[position].author.name
            postTitle.text = postsUser[position].title
            postDesc.text = postsUser[position].body
            profilePhoto.setImageBitmap(postsUser[position].author.image)
            line.text
            map.setImageResource(R.drawable.trip_map)
        }
    }

    override fun getItemCount(): Int {
        return postsUser.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var profilePhoto : CircleImageView = itemView.findViewById(R.id.iv_profile_photo)
        var profileName: TextView = itemView.findViewById(R.id.tv_profile_name)
        var postTitle: TextView = itemView.findViewById(R.id.tv_post_title)
        var postDesc: TextView = itemView.findViewById(R.id.tv_post_desc)
        var line: TextView = itemView.findViewById(R.id.tv_line)
        var map: ImageView = itemView.findViewById(R.id.btn_map_trip_last)
    }

}