package com.example.sunbelttestapp.mainActivities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.activeandroid.ActiveAndroid

import com.example.sunbelttestapp.R
import com.example.sunbelttestapp.info.Post
import com.example.sunbelttestapp.info.User
import com.example.sunbelttestapp.models.PostDB
import com.example.sunbelttestapp.models.UserDB
import com.example.sunbelttestapp.placeHolder.PostHolder
import com.example.sunbelttestapp.placeHolder.UserHolder


import retrofit2.Call

import retrofit2.Callback

import retrofit2.Response

import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory


class SplashActivity : AppCompatActivity() {

    private var handler: Handler? = null
    private var postDB: PostDB? = null
    private var userDB: UserDB? = null
    private var postDBList: List<PostDB>? = null
    private var userDBList: List<UserDB>? = null
    private var context: Context? = null
    private var retrofit: Retrofit? = null
    private val baseUrl = "https://jsonplaceholder.typicode.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        ActiveAndroid.initialize(this)
        context = this
        postDB = PostDB()
        userDB = UserDB()
        postDBList = postDB!!.postList
        userDBList = userDB!!.userList

        if (userDBList!!.isEmpty() || postDBList!!.isEmpty()) {
            retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            downloadInfo()
            handler = Handler()
            handler!!.postDelayed({
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        } else {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun downloadInfo() {
        val postHolder: PostHolder = retrofit!!.create(PostHolder::class.java)
        val userHolder: UserHolder = retrofit!!.create(UserHolder::class.java)
        val postCall: Call<List<Post>> = postHolder.post
        val userCall: Call<List<User>> = userHolder.users
        postCall.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (!response.isSuccessful) {
                    Toast.makeText(context, "Respuesta: " + response.code(),
                            Toast.LENGTH_LONG).show()
                    return
                }
                val postList: List<Post> = response.body()!!
                val postDB = PostDB()
                for (post in postList) {
                    postDB.savePost(post.getUserId(),
                            post.getId(),
                            post.getTitle(),
                            post.getText())
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Toast.makeText(context, "Respuesta: $t",
                        Toast.LENGTH_LONG).show()
            }
        })
        userCall.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (!response.isSuccessful) {
                    Toast.makeText(context, "Respuesta: " + response.code(),
                            Toast.LENGTH_LONG).show()
                    return
                }
                val userList: List<User> = response.body()!!
                val userDB = UserDB()
                for (user in userList) {
                    userDB.saveUser(user.getId(),
                            user.getName(),
                            user.getEmail(),
                            user.getPhone())
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(context, "Respuesta: $t",
                        Toast.LENGTH_LONG).show()
            }
        })
    }
}