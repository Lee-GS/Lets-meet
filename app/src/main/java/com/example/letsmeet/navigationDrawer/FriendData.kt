package com.example.letsmeet.navigationDrawer

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.letsmeet.authorization.AuthFireBase
import com.example.letsmeet.navigationDrawer.FriendData.Companion.friends


class FriendData {
    companion object{
        val friends = mutableStateListOf<String>()
    }
}


fun rebuildFriendData(check : Boolean) {
    val friendRequest = mutableStateListOf<String>()
    val db = AuthFireBase.firestore
    if (AuthFireBase.email != null) {
        db.collection("users").document(AuthFireBase.email!!).get().addOnSuccessListener { document ->
            if (document != null) {
                friendRequest.add(document.get("friendrequest").toString())
                Log.d("Succcess", friendRequest.toString())
                if (check) {
                    for (i in 0 until friendRequest.size){
                        if (friendRequest[i] != "[]"||friendRequest[i]!="null") {
                            friends.add(friendRequest[i])
                        }
                    }
                    friendRequest.clear()
                }
                db.collection("users").document(AuthFireBase.email!!).update("friendrequest",friendRequest).addOnSuccessListener {
                    Log.d("SUCCESS","친구 승인 목록 삭제 성공")
                }
                db.collection("users").document(AuthFireBase.email!!).update("friendlist", friends).addOnSuccessListener {

                    Log.d("친구 목록 업데이트 성공", friends.toString() )
                }
                Log.d("친구 목록", friends.toString())
            }
        }
    }
}