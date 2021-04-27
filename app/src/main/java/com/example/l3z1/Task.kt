package com.example.l3z1

import java.io.Serializable

class Task (var title:String, var time:String, var img:Int) : Serializable {
    var done = false
    var imgId:Int = -1

    init {
        imgId = when(img) {
            R.drawable.importance_high -> 2
            R.drawable.importance_med -> 1
            else -> 0
        }
    }

}