package com.example.letsmeet.navigationDrawer

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.letsmeet.MainActivity
import com.example.letsmeet.authorization.AuthFireBase
import com.example.letsmeet.navigationDrawer.FriendData.friends
import com.example.letsmeet.room.database.FriendDatabase
import com.example.letsmeet.room.entity.FriendData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object FriendData {
    val friends = mutableStateListOf<String>()
}


fun rebuildFriendData(check: Boolean, name: String) {
    val db = AuthFireBase.firestore.collection("users")
    if (AuthFireBase.email != null) {
        if (check) {
            friends.add(name)
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("room db 삽입", name)
                FriendDatabase.getInstance(MainActivity.instance)!!.friendDao()
                    .insertFriend(
                        FriendData(name)
                    )
            }
            insertFriend(AuthFireBase.email!!,name,db)
            insertRequestFriend(name,AuthFireBase.email!!,db)
        }
        removeFriend(AuthFireBase.email!!,name,db)
    }
}
fun insertFriend(name : String, friend : String, path : CollectionReference ){
    path.document(name)
        .update("friendlist", FieldValue.arrayUnion(friend))
        .addOnSuccessListener {
            Log.d("친구 목록 업데이트 성공", name + "추가")
        }
}

fun insertRequestFriend(name : String, friend : String, path : CollectionReference ){
    path.document(name)
        .update("friendlist", FieldValue.arrayUnion(friend))
        .addOnSuccessListener {
            Log.d("SUCCESS", "요청한 유저의 친구 목록에 현유저 추가 성공")
        }
}

fun removeFriend(name : String, friend : String, path : CollectionReference ){
    path.document(name)
        .update("friendlist", FieldValue.arrayRemove(friend))
        .addOnSuccessListener {
            Log.d("친구 목록 업데이트 성공", name + "추가")
        }
}



