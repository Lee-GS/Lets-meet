package com.example.letsmeet.navigationDrawer

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.letsmeet.mainScreen.currentEmail
import com.google.firebase.firestore.FieldValue


fun rebuildFriendData(check : Boolean) {
    val friendList = mutableListOf<String>()
    val friendRequest = mutableListOf<String>()

    if (currentEmail != null) {
        db.collection("users").document(currentEmail).get().addOnSuccessListener { document ->
            if (document != null) {
                friendRequest.add(document.get("friendrequest").toString())
                Log.d("Succcess", friendRequest.toString())
                if (check) {
                    for (i in 0 until friendRequest.size){
                        friendList.add(friendRequest[i])
                    }
                    friendRequest.clear()
                }
                db.collection("users").document(currentEmail).update("friendrequest",friendRequest).addOnSuccessListener {
                    Log.d("SUCCESS","친구 승인 목록 삭제 성공")
                }
                db.collection("users").document(currentEmail).update("friendlist",friendList).addOnSuccessListener {
                    Log.d("SUCCESS","친구 목록 업데이트 성공")
                }
                friends = friendList.toMutableList()
                Log.d("친구 목록:", friends.toString())

            }
        }
    }
}