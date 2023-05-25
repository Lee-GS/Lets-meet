package com.example.letsmeet.navigationDrawer

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.letsmeet.authorization.AuthFireBase
import com.example.letsmeet.mainScreen.MainUi
import com.google.firebase.firestore.FieldValue

@Composable
fun acceptFriend(name:String, onChange: () -> Unit){
    AlertDialog(
        onDismissRequest = { onChange() },
        title = {
            Text(
                text = "친구 요청이 왔습니다!",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column() {
                Text(
                    text = "$name",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 20.sp
                )
                TextButton(
                    onClick = {
                        rebuildFriendData(true,name)
                        onChange()
                    }) {
                    Text(
                        text = "수락하기",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                rebuildFriendData(false,name)
                onChange()
            }) {
                Text(text = "거절하기")
            }
        }
    )
}

fun checkFriend(check : Boolean){
    val db = AuthFireBase.firestore
    if (AuthFireBase.email != null) {
            db.collection("users").document(AuthFireBase.email!!).get().addOnSuccessListener { document ->
                if (document != null) {
                    val _fname = document.get("friendrequest")
                    Log.d("Succcess", _fname.toString())
                    if (check) {
                        db.collection("users").document(AuthFireBase.email!!).update(
                            "friendlist", FieldValue.arrayUnion(_fname)).addOnSuccessListener {
                            //friends.add(_fname.toString())
                            Log.d("SUCCESS", "친구 추가 성공")
                        }
                    }
                    db.collection("users").document(AuthFireBase.email!!).update("friendrequest", FieldValue.arrayRemove(_fname)).addOnSuccessListener {
                        Log.d("SUCCESS","친구 승인 목록 삭제 성공")
                    }
                }
            }
    }
}



@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun acceptFriendPreview(){
    acceptFriend("name"){}
}
