package com.example.letsmeet.navigationDrawer

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.letsmeet.R
import com.example.letsmeet.authorization.AuthFireBase
import com.example.letsmeet.navigationDrawer.FriendData.Companion.friends


fun addFriend(){
    val db = AuthFireBase.firestore
    db.collection("users")
        .get()
        .addOnCompleteListener {  result->
            for ( document in result.result){
                val name = document.get("friendlist").toString()
                if (name != "null" || name != "[]") {
                    friends.add(name)
                }
            }
            Log.d("Success","성공!!")
        }
        .addOnFailureListener { exeption ->
            Log.e("Fail", "실패!!!! 이유는: $exeption")
        }
}


@Composable
fun FriendList(modifier: Modifier) {
    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "App Icon",
        )
        LazyColumn(
        )
        {
            items(friends) {
                Text(it)
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun FriendListPreview() {
    FriendList(modifier = Modifier)
}

