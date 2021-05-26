package com.example.l3z1

import java.io.Serializable

class User(data: String): Serializable {
    var id: Int = 0
    var username: String = ""
    var fullname: String = ""
    var email: String = ""

    init {
        id = data.substring(0, data.indexOf("/")).toInt()
        var tmp = data.substring(1 + id.toString().length)
        username = tmp.substring(0, tmp.indexOf("/"))
        tmp = tmp.substring(1 + username.length)
        fullname = tmp.substring(0, tmp.indexOf("/"))
        email = tmp.substring(1 + fullname.length)
    }

    constructor(newId: Int, newUsername: String, newFullname: String, newEmail: String) : this(
        "$newId/$newUsername/$newFullname/$newEmail"
    )
}