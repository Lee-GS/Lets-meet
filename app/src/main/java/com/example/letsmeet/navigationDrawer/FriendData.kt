package com.example.letsmeet.navigationDrawer

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.letsmeet.MainActivity
import com.example.letsmeet.authorization.AuthFireBase
import com.example.letsmeet.navigationDrawer.FriendData.Companion.friends
import com.example.letsmeet.room.database.FriendDatabase
import com.example.letsmeet.room.entity.FriendData
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FriendData {
    companion object{
        lateinit var friends : ArrayList<com.example.letsmeet.room.entity.FriendData>
    }
}


fun rebuildFriendData(check : Boolean, name : String) {
    var friendRequest : ArrayList<String>
    val db = AuthFireBase.firestore
    if (AuthFireBase.email != null) {
        db.collection("users").document(AuthFireBase.email!!).get().addOnSuccessListener { document ->
            if (document != null) {
                friendRequest= document.get("friendrequest") as ArrayList<String>
                Log.d("친구요청 목록", friendRequest.toString())
                if (check && friendRequest.isNotEmpty()) {
                    for (i in 0 until friendRequest.size){
                        CoroutineScope(Dispatchers.IO).launch{
                            Log.d("room db 삽입",friendRequest[i])
                            FriendDatabase.getInstance(MainActivity.instance)!!.friendDao().insertFriend(
                                FriendData(friendRequest[i])
                            )
                            withContext(Dispatchers.Main){
                                for (i in 0 until friends.size) {
                                    db.collection("users").document(AuthFireBase.email!!)
                                        .update("friendlist", FieldValue.arrayUnion(friends[i].name))
                                        .addOnSuccessListener {
                                            Log.d("친구 목록 업데이트 성공", friends.toString())
                                        }
                                }
                                db.collection("users").document(name).update("friendlist", FieldValue.arrayUnion(AuthFireBase.email)).addOnSuccessListener {
                                    Log.d("SUCCESS","요청한 유저의 친구 목록에 현유저 추가 성공")
                                }
                                db.collection("users").document(AuthFireBase.email!!).update("friendrequest",FieldValue.arrayRemove(name)).addOnSuccessListener {
                                    Log.d("SUCCESS","친구 승인 목록 삭제 성공")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}