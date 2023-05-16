package com.example.connectus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val postList : ArrayList<PostData> ):RecyclerView.Adapter<MyAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.post_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentPost = postList[position]
        holder.caption?.text=currentPost.caption
        holder.username.text = currentPost.username
        holder.profilePic.setImageResource(R.drawable.zenitsu)
        holder.postPic.setImageResource(R.drawable.mikey)
    }

    class MyViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val caption : TextView? = itemView.findViewById(R.id.captionEt)
        val username : TextView = itemView.findViewById(R.id.userName)
        val profilePic : ImageView = itemView.findViewById(R.id.profilePic)
        val postPic : ImageView = itemView.findViewById(R.id.postImg)
    }

}
