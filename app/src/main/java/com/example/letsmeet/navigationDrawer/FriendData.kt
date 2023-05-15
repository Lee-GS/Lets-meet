package com.example.letsmeet.navigationDrawer

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.letsmeet.authorization.AuthFireBase
import com.google.firebase.firestore.FieldValue


fun rebuildFriendData(check : Boolean) {
    val friendRequest = mutableListOf<String>()
    val db = AuthFireBase.firestore
    if (AuthFireBase.email != null) {
        db.collection("users").document(AuthFireBase.email!!).get().addOnSuccessListener { document ->
            if (document != null) {
                friendRequest.add(document.get("friendrequest").toString())
                Log.d("Succcess", friendRequest.toString())
                if (check) {
                    for (i in 0 until friendRequest.size){
                        friends.add(friendRequest[i])
                    }
                    friendRequest.clear()
                }
                db.collection("users").document(AuthFireBase.email!!).update("friendrequest",friendRequest).addOnSuccessListener {
                    Log.d("SUCCESS","친구 승인 목록 삭제 성공")
                }
                db.collection("users").document(AuthFireBase.email!!).update("friendlist", friends).addOnSuccessListener {
                    Log.d("SUCCESS","친구 목록 업데이트 성공")
                }
                Log.d("친구 목록:", friends.toString())
            }
        }
    }
}