package com.example.letsmeet.navigationDrawer

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.letsmeet.MainActivity
import com.example.letsmeet.authorization.AuthFireBase
import com.example.letsmeet.navigationDrawer.FriendData.friends
import com.example.letsmeet.room.database.FriendDatabase
import com.example.letsmeet.room.entity.FriendData
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object FriendData {
    val friends = mutableStateListOf<String>()
}


fun rebuildFriendData(check: Boolean, name: String) {
    val db = AuthFireBase.firestore
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
            db.collection("users").document(AuthFireBase.email!!)
                .update("friendlist", FieldValue.arrayUnion(name))
                .addOnSuccessListener {
                    Log.d("친구 목록 업데이트 성공", name + "추가")
                }
            db.collection("users").document(name)
                .update("friendlist", FieldValue.arrayUnion(AuthFireBase.email))
                .addOnSuccessListener {
                    Log.d("SUCCESS", "요청한 유저의 친구 목록에 현유저 추가 성공")
                }
        }
        db.collection("users").document(AuthFireBase.email!!)
            .update("friendrequest", FieldValue.arrayRemove(name))
            .addOnSuccessListener {
                Log.d("SUCCESS", "친구 승인 목록 삭제 성공")
            }
    }
}


