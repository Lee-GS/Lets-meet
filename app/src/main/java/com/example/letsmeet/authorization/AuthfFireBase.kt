package com.example.letsmeet.authorization

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class AuthfFireBase {
    companion object{
        lateinit var auth: FirebaseAuth
        var email: String? = null
        fun checkAuth(): Boolean {
            val currentUser = auth.currentUser
            return currentUser?.let {
                email = currentUser.email
                currentUser.isEmailVerified
            } ?: let {
                false
            }
        }
    }
}