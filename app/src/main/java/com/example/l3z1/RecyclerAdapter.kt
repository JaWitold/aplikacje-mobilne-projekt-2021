package com.example.l3z1

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.vishnusivadas.advanced_httpurlconnection.PutData

class RecyclerAdapter(val list: List<Task>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(view, list)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTime.text = list[position].time
        holder.itemTitle.text = list[position].title
        holder.itemImage.setImageResource(list[position].img)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    class ViewHolder(itemView: View, private val list: List<Task>): RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView = itemView.findViewById(R.id.image)
        var itemTitle: TextView = itemView.findViewById(R.id.title)
        var itemTime: TextView = itemView.findViewById(R.id.username)

        init {
            itemView.setOnLongClickListener{view: View ->
                if(!list[layoutPosition].done) {
                    list[layoutPosition].done = true
                    list[layoutPosition].title += " [done]"
                    list[layoutPosition].img = R.drawable.importance_done
                    list[layoutPosition].imgId = -1
                    Toast.makeText(view.context, itemTitle.text.toString() + " done", Toast.LENGTH_SHORT).show()
                    itemTitle.text = itemTitle.text.toString() + " [done]"
                    itemImage.setImageResource(list[position].img)
                    //usuń z tabeli

                    val fields = arrayOf("id")
                    val data = arrayOf(list[layoutPosition].id.toString())
                    Handler(Looper.getMainLooper()).postDelayed({
                        val putData = PutData("http://daoehremvz.cfolks.pl/removeTask.php", "POST", fields, data)
                        putData.startPut()
                        putData.onComplete()
                    }, 100)
                }
                true
            }
        }

    }

}
