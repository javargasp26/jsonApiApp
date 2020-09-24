package com.example.sunbelttestapp.mainActivities

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunbelttestapp.R
import com.example.sunbelttestapp.adapters.PostAdapter
import com.example.sunbelttestapp.models.PostDB
import com.example.sunbelttestapp.models.UserDB

class PostActivity : AppCompatActivity() {
    //global variables
    var userId = 0
    var context: Context? = null
    var user: UserDB? = null
    var name: TextView? = null
    var phone:TextView? = null
    var email:TextView? = null
    var recyclerViewPostsResults: RecyclerView? = null
    var postDB: PostDB? = null
    var postAdapter : PostAdapter? = null
    var postList: List<PostDB>? = null
    var userDB: UserDB? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        context = this
        postDB = PostDB()
        userDB = UserDB()

        val bundle = this.intent.extras
        //check if information has been passed correctly
        if (bundle != null) {
            userId = bundle.getInt("userId") //id del Customer
        } else {
            Toast.makeText(context, "Error obteniendo el id del usuario", Toast.LENGTH_LONG).show()
            finish()
        }
        user = userDB!!.findUserById(userId)
        postList = postDB!!.getPostListByUserId(userId)
        //initialize visual components
        initComponents()
        //build recycler view adapter
        buildPostAdapter()
    }

    private fun initComponents() {
        name = findViewById(R.id.name)
        phone = findViewById(R.id.phone)
        email = findViewById(R.id.email)
        name!!.text = user!!.userName
        phone!!.text = user!!.userPhone
        email!!.text = user!!.userEmail
        recyclerViewPostsResults = findViewById(R.id.recyclerViewPostsResults)
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerViewPostsResults!!.layoutManager = linearLayoutManager
    }

    private fun buildPostAdapter() {
        postAdapter = PostAdapter(context, postList)
        recyclerViewPostsResults!!.adapter = postAdapter
    }
}