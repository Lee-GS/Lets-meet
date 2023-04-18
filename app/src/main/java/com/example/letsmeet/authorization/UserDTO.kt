package com.example.letsmeet.authorization

data class UserDTO(
    val name : String = " ",
    val email : String = " ",
    val password : String = " ",
    val friendList : MutableList<String>,
    val friendrequest : MutableList<String> = mutableListOf()
)
