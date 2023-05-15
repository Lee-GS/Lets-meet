package com.example.letsmeet.authorization

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class AuthFireBase : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()
    }



    companion object{
        lateinit var auth: FirebaseAuth
        lateinit var firestore : FirebaseFirestore
        var email: String? = null
        fun checkAuth(): Boolean {
            if (!::auth.isInitialized) {
                auth = Firebase.auth
            }
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

