package com.example.l3z1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.vishnusivadas.advanced_httpurlconnection.PutData

class AddToGroup : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder>
    private var list = arrayListOf<User>()
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_group)
        user = intent!!.extras!!.getSerializable("user") as User

        recyclerView = findViewById(R.id.userRecyclerView)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
            false)
        recyclerView.layoutManager = layoutManager
        loadUsersList()
    }

    private fun display(newList: ArrayList<User>) {
        adapter = UserRecyclerAdapter(newList, user.id)
        recyclerView.adapter = adapter
    }

    private fun loadUsersList() {

        val fields = arrayOf("name")
        val data = arrayOf(findViewById<EditText>(R.id.name).text.toString())
        Handler(Looper.getMainLooper()).postDelayed({

            val putData: PutData =
                PutData("http://daoehremvz.cfolks.pl/getUsers.php", "POST", fields, data)
            if (putData.startPut()) {
                if (putData.onComplete()) {

                    val result: String = putData.result
                    if(result.startsWith("success")) {

                        val json = result.substring(7).replace("[", "{ \"list\": [").replace("]", "]}")
                        //Log.i("mylog", json)
                        val gson = GsonBuilder().create();
                        val xxx = gson.fromJson(json, UserList::class.java)

                        //Log.i("mylog", xxx.list.size.toString())
                        list = xxx.list as ArrayList<User>
                        display(list)
                    } else {
                        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }, 100)
    }

    fun load(view: View) {
        loadUsersList()
    }


}

class UserList(val list: List<User>)
