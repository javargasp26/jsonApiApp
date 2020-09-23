package com.example.sunbelttestapp.mainActivities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunbelttestapp.R
import com.example.sunbelttestapp.adapters.UserAdapter
import com.example.sunbelttestapp.models.PostDB
import com.example.sunbelttestapp.models.UserDB
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    var context: Context? = null
    var postDB: PostDB? = null
    var userDB: UserDB? = null
    var postDBList: List<PostDB>? = null
    var userDBList: List<UserDB>? = null
    var retrofit: Retrofit? = null
    var recyclerViewSearchResults: RecyclerView? = null
    var userAdapter: UserAdapter? = null
    var editTextSearch: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this
        postDB = PostDB()
        userDB = UserDB()

        postDBList = postDB!!.postList
        userDBList = userDB!!.userList

        initComponents()

        initListeners()

        buildUserAdapter()
    }

    private fun initComponents() {
        editTextSearch = findViewById(R.id.editTextSearch)
        recyclerViewSearchResults = findViewById(R.id.recyclerViewSearchResults)
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerViewSearchResults!!.layoutManager = linearLayoutManager
    }

    private fun buildUserAdapter() {
        val filter = editTextSearch!!.text.toString().trim { it <= ' ' }
        val userList: List<UserDB>
        userList = userDB!!.getFilteredUserList(filter)
        userAdapter = UserAdapter(userList, context)

        userAdapter!!.setOnItemClickListener( object: UserAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                val userId = userList[position].userId
                getPost(userId)
            }
        })
        recyclerViewSearchResults!!.adapter = userAdapter
        if (userList.isEmpty()) {
            Toast.makeText(context, "List is empty",
                    Toast.LENGTH_LONG).show()
        }
    }

    private fun initListeners() {
        editTextSearch!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                try {
                    buildUserAdapter()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    fun getPost(userId: Int) {
        val intent = Intent(context, PostActivity::class.java)
        intent.putExtra("userId", userId)
        context!!.startActivity(intent)
        //finish();
    }

}