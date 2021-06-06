package com.example.l3z1

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.vishnusivadas.advanced_httpurlconnection.PutData
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class MainActivity: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
    private var list = arrayListOf<Task>()
    private var sortByTime:Boolean = false
    private var sortByImportance:Boolean = false
    private lateinit var user: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        user = intent!!.extras!!.getSerializable("user") as User
        recyclerView = findViewById(R.id.RecyclerView)

        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false)
        recyclerView.layoutManager = layoutManager
        loadTasks()


        //Log.i("mylog", list.size.toString())

        display(list)
        //setNotification()
    }

//    private fun generateTasks(): ArrayList<Task> {
//        val list:ArrayList<Task> = arrayListOf<Task>()
//
//
//        for (i in 0..100) {
//            val img: Int = when(Random.nextInt(3)) {
//                0 -> R.drawable.importance_low
//                1 -> R.drawable.importance_med
//                else -> R.drawable.importance_high
//            }
//           list.add(Task("title$i", i.toString(), img, 1))
//        }
//        return list
//    }

    private fun loadTasks() {
        val fields = arrayOf("id")
        val data = arrayOf(user.id.toString())

        Handler(Looper.getMainLooper()).postDelayed({

            val putData: PutData =
                PutData("http://daoehremvz.cfolks.pl/tasks.php", "POST", fields, data)
            if (putData.startPut()) {
                if (putData.onComplete()) {

                    val result: String = putData.result
                    if(result.startsWith("success")) {

                        val json = result.substring(7).replace("[", "{ \"list\": [").replace("]", "]}")
                        //Log.i("mylog", json)
                        val gson = GsonBuilder().create();
                        val xxx = gson.fromJson(json, TaskList::class.java)

                        //Log.i("mylog", xxx.list.size.toString())
                        list = xxx.list as ArrayList<Task>
                        display(list)
                        setNotification()

                    } else {
                        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }, 100)

    }

    fun addNewTask(view: View) {
        val intent = Intent(this, AddNewTask::class.java)
        intent.putExtra("userid", user.id)
        startActivity(intent)
    }

    fun addNewGroup(view: View) {
        Log.i("mylog", "click")
        val intent = Intent(this, AddToGroup::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data?.getStringExtra("title").toString() != "" && data?.getStringExtra("title") != null) {

//            val img: Int = when(data.getStringExtra("img").toString()) {
//                "Niski priorytet" -> R.drawable.importance_low
//                "Średni priorytet" -> R.drawable.importance_med
//                else -> R.drawable.importance_high
//            }
            Log.i("mylog", "jjjjjj")
            loadTasks()
            display(SortByDate())
            return;
        }
    }

    private fun display(list: ArrayList<Task>) {
        adapter = RecyclerAdapter(list)
        recyclerView.adapter = adapter
    }


    fun sortByImportance(view: View) {
        display(SortByImportance())
    }

    fun sortByDate(view: View){
        display(SortByDate())
    }

    private fun SortByDate(): ArrayList<Task> {

        val newlist = list
        sortByTime = !sortByTime

        if(sortByTime)
            newlist.sortBy { it.time }
        else
            newlist.sortByDescending { it.time }

        return newlist
    }

    private fun SortByImportance(): ArrayList<Task> {

        val newlist = list
        sortByImportance = !sortByImportance

        if(sortByImportance)
            newlist.sortBy { it.imgId }
        else
            newlist.sortByDescending { it.imgId }

        return newlist
    }

    override fun onSaveInstanceState(state: Bundle) {
        super.onSaveInstanceState(state)
        state.putSerializable("mytasks", list as Serializable)
    }

    override fun onRestoreInstanceState(state: Bundle) {
        super.onRestoreInstanceState(state)
        this.list.clear()
        this.list.addAll(state.getSerializable("mytasks") as ArrayList<Task>)
        this.display(list)
    }

    companion object {
        const val CHANNEL_ID = "mynotificationservice"
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "notification service"
            val description = "notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setNotification() {
        Log.i("mylog", "setting alarms");

        createNotificationChannel()
        val intent = Intent(this, NotificationBroadcast::class.java)
        val pending = PendingIntent.getBroadcast(this, 0, intent, 0)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager


        if(list.size > 0) {
            val time = (SortByDate().filter { it.time != "" })
            Log.i("mylog", time.toString());

            for(t in time) {
                Log.i("mylog", t.time);
                if (t.time != "") {
                    val dt = LocalDateTime.parse(t.time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

                    alarmManager.set(AlarmManager.RTC_WAKEUP, dt, pending)
                    //Log.i("mylog", "$dt");

                }
            }
        }
    }

    fun startAbout(view: View) {
        startActivity(Intent(this@MainActivity, about::class.java))
    }

}

class TaskList(val list: List<Task>)