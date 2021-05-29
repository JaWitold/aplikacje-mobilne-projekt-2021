package com.example.l3z1

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.vishnusivadas.advanced_httpurlconnection.PutData

class UserRecyclerAdapter(private val list: List<User>, var user: Int): RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user, parent, false)
        return ViewHolder(view, list, user)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemFullname.text = list[position].fullname
        holder.itemUsername.text = list[position].username
//        holder.itemImage.setImageResource(list[position].img)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    class ViewHolder(itemView: View, private val list: List<User>, private val id: Int): RecyclerView.ViewHolder(itemView) {
//        var itemImage: ImageView = itemView.findViewById(R.id.image)
        var itemUsername: TextView = itemView.findViewById(R.id.username)
        var itemFullname: TextView = itemView.findViewById(R.id.fullname)

        init {
            itemView.setOnLongClickListener{view: View ->
                //add to users group or remove
                val fields = arrayOf("userId", "groupId")
                val data = arrayOf(list[layoutPosition].id.toString(), id.toString())
                Handler(Looper.getMainLooper()).postDelayed({
                    val putData = PutData("http://daoehremvz.cfolks.pl/addToGroup.php", "POST", fields, data)
                    Log.i("mylog", list[layoutPosition].id.toString())
                    if (putData.startPut()) {
                        if (putData.onComplete()) {

                            val result: String = putData.result
                            when {
                                result.startsWith("added") -> Toast.makeText(view.context, "User added to Your group", Toast.LENGTH_SHORT).show()
                                result.startsWith("removed") -> Toast.makeText(view.context, "User removed from Your group", Toast.LENGTH_SHORT).show()
                                else -> Toast.makeText(view.context, result, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }, 100)
                true
            }
        }

    }

}
