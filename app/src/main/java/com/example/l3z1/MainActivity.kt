package com.example.l3z1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.Serializable
import kotlin.random.Random


class MainActivity: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
    private var list = arrayListOf<Task>()
    private var listToShow = list
    private var sortByTime:Boolean = false
    private var sortByImportance:Boolean = false
    private val bottomNavigationMenuView: BottomNavigationView = findViewById(R.id.bottom_nav)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.RecyclerView)

        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false)
        recyclerView.layoutManager = layoutManager
        display(list)
    }

    private fun generateTasks(): ArrayList<Task> {
        val list:ArrayList<Task> = arrayListOf<Task>()


        for (i in 0..100) {
            val img: Int = when(Random.nextInt(3)) {
                0 -> R.drawable.importance_low
                1 -> R.drawable.importance_med
                else -> R.drawable.importance_high
            }
           list.add(Task("title$i", i.toString(), img))
        }
        return list
    }

    fun addNewTask(view: View) {
        val intent = Intent(this, AddNewTask::class.java)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data?.getStringExtra("title").toString() != "" && data?.getStringExtra("title") != null) {

            val img: Int = when(data.getStringExtra("img").toString()) {
                "low priority" -> R.drawable.importance_low
                "medium priority" -> R.drawable.importance_med
                else -> R.drawable.importance_high
            }

            list.add(Task(data.getStringExtra("title").toString(), data.getStringExtra("time").toString(), img))
            display(sortByDate())
        }
    }

    private fun display(list: ArrayList<Task>) {
        adapter = RecyclerAdapter(list)
        recyclerView.adapter = adapter
    }

    fun showToDo(view: View) {
        display(sortByDate())
    }

    fun sortByImportance(view: View) {
        display(sortByImportance())
    }

    private fun sortByDate(): ArrayList<Task> {

        val newlist = list
        sortByTime = !sortByTime

        if(sortByTime)
            newlist.sortBy { it.time }
        else
            newlist.sortByDescending { it.time }

        return newlist
    }

    private fun sortByImportance(): ArrayList<Task> {

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

}