package com.example.l3z1.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import com.example.l3z1.MainActivity
import com.example.l3z1.R
import com.example.l3z1.User
import com.vishnusivadas.advanced_httpurlconnection.PutData

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val progressbar = findViewById<ProgressBar>(R.id.progressbar)

        findViewById<Button>(R.id.login).setOnClickListener {

            val fields = arrayOf("username", "password")
            val data = arrayOf("username", "password")

            if(!(findViewById<EditText>(R.id.username).text.toString().isEmpty() || findViewById<EditText>(R.id.password).text.toString().isEmpty())) {
                progressbar.visibility = View.VISIBLE

                data[0] = findViewById<EditText>(R.id.username).text.toString()
                data[1] = findViewById<EditText>(R.id.password).text.toString()

                Handler(Looper.getMainLooper()).postDelayed({

                    val putData =
                            PutData("http://daoehremvz.cfolks.pl/login.php", "POST", fields, data)
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            progressbar.visibility = View.GONE

                            val result: String = putData.result
                            if(result.startsWith("logged")) {
                                Log.i("myLog", result)
                                val myIntent = Intent(this, MainActivity::class.java)
                                val user = User(result.substring(7));
                                myIntent.putExtra("user", user)
                                startActivity(myIntent)
                                finish()
                            } else {
                                Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }, 100)
            } else {
                Toast.makeText(this, "żadne pole nie może być puste", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<TextView>(R.id.register).setOnClickListener {
            val myIntent = Intent(this, SignUp::class.java)
            startActivity(myIntent)
        }
    }
}