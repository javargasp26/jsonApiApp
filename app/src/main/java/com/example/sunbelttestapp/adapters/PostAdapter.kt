package com.example.sunbelttestapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sunbelttestapp.R
import com.example.sunbelttestapp.models.PostDB

class PostAdapter(var context: Context?, private var postList: List<PostDB>?) : RecyclerView.Adapter<PostAdapter.PostViewHolder?>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        //inflate view
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.post_list_item, parent, false)
        return PostViewHolder(v)
    }

    override fun getItemCount(): Int {
        return postList!!.size
    }

    override fun onBindViewHolder(postViewHolder: PostViewHolder, position: Int) {
        //set visual information
        postViewHolder.title!!.text = postList?.get(position)!!.postTitle
        postViewHolder.body!!.text = postList?.get(position)!!.postText
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView? = null
        var body: TextView? = null

        init {
            title = itemView.findViewById(R.id.title)
            body = itemView.findViewById(R.id.body)
        }
    }

}