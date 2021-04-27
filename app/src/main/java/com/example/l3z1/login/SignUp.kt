package com.example.l3z1.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import com.example.l3z1.MainActivity
import com.example.l3z1.R
import com.vishnusivadas.advanced_httpurlconnection.PutData

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val progressbar = findViewById<ProgressBar>(R.id.progressbar)

        findViewById<Button>(R.id.register).setOnClickListener {

            val fields = arrayOf("fullname", "email", "username", "password")
            val data = arrayOf("fullname", "email", "username", "password")

            if(!(findViewById<EditText>(R.id.fullname).text.toString().isEmpty() || findViewById<EditText>(R.id.email).text.toString().isEmpty() || findViewById<EditText>(R.id.username).text.toString().isEmpty() || findViewById<EditText>(R.id.password).text.toString().isEmpty())) {
                progressbar.visibility = View.VISIBLE

                data[0] = findViewById<EditText>(R.id.fullname).text.toString()
                data[1] = findViewById<EditText>(R.id.email).text.toString()
                data[2] = findViewById<EditText>(R.id.username).text.toString()
                data[3] = findViewById<EditText>(R.id.password).text.toString()

                Handler(Looper.getMainLooper()).postDelayed({

                    val putData: PutData =
                        PutData("http://192.168.0.111/server/signup.php", "POST", fields, data)
                    if (putData.startPut()) {
                        if (putData.onComplete()) {

                            progressbar.visibility = View.GONE
                            val result: String = putData.result
                            if(result == "Sign Up Success") {
                                val myIntent = Intent(this, Login::class.java)
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

        findViewById<TextView>(R.id.login).setOnClickListener {
            val myIntent = Intent(this, Login::class.java)
            startActivity(myIntent)
        }

    }
}