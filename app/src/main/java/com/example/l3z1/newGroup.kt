package com.example.l3z1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class newGroup : AppCompatActivity() {

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_group)
        user = intent!!.extras!!.getSerializable("user") as User

    }
}