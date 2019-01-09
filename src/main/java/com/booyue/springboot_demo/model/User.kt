package com.booyue.springboot_demo.model

class User {

    var id: Int = 0
    var name: String = ""
    var password: String = ""

    constructor()

    constructor(id: Int, name: String, password: String) {
        this.id = id
        this.name = name
        this.password = password
    }

    override fun toString(): String {
        return "User(id=$id, name='$name', password='$password')"
    }


}