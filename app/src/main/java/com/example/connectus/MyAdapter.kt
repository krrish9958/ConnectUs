package com.example.connectus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
       var currentPost = postList[position]

        holder.caption.text=currentPost.caption
    }

    class MyViewHolder(itemView : View): RecyclerView.ViewHolder(itemView)
    {

     val caption :TextView=itemView.findViewById(R.id.captionEt)
    }

}