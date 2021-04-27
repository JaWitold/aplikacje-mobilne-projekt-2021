package com.example.l3z1

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.util.*


class AddNewTask : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private var minute = 0
    private var hour = 0
    private var day = 0
    private var month = 0
    private var year = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_task)

        findViewById<Button>(R.id.editTextTime).setOnClickListener {
            val cal : Calendar = Calendar.getInstance()
            DatePickerDialog(this, this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

    }

    fun addNewTask(view: View) {
        val title:String = findViewById<TextView>(R.id.editTextTitle).text.toString()
        var time:String = findViewById<TextView>(R.id.editTextTime).text.toString()

        val selectedOption: Int = findViewById<RadioGroup>(R.id.radio)!!.checkedRadioButtonId
        val radioButton = findViewById<RadioButton>(selectedOption)
        
        
        if(time.equals("when")) time = ""

        val myIntent: Intent = Intent();
        myIntent.putExtra("title", title)
        myIntent.putExtra("time", time)
        myIntent.putExtra("img", radioButton.text.toString())
        

        setResult(1, myIntent)
        finish();
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        this.year = year
        this.month = month + 1
        day = dayOfMonth

        val cal : Calendar = Calendar.getInstance()

        TimePickerDialog(this, this, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        hour = hourOfDay
        this.minute = minute
        findViewById<Button>(R.id.editTextTime).text = formatDate()
    }

    private fun formatDate(): String {
        return "$year-" + addLeadingZero(month) + "-" + addLeadingZero(day) + " " + addLeadingZero(hour) + ":" + addLeadingZero(minute)
    }

    private fun addLeadingZero(time: Int): String {
        val tmp:String = time.toString()
        if(tmp.length == 1) return "0$tmp"
        return tmp
    }


}