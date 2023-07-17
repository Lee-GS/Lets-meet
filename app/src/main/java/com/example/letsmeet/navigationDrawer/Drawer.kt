package com.example.letsmeet.navigationDrawer

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.letsmeet.MainActivity
import com.example.letsmeet.R
import com.example.letsmeet.authorization.AuthFireBase
import com.example.letsmeet.navigationDrawer.FriendData.friends
import com.example.letsmeet.room.database.FriendDatabase
import com.example.letsmeet.room.entity.FriendData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


fun addFriend() {
    CoroutineScope(Dispatchers.IO).launch {
        AuthFireBase.firestore.collection("users").document(AuthFireBase.email!!).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val name = document.get("friendlist")
                    if (name != null) {
                        name as ArrayList<String>
                        for (element in name) {
                            Log.d("친구 리스트 추가", element)
                            friends.add(element)
                        }
                    }
                }
            }
    }
}


@Composable
fun FriendList(modifier: Modifier) {
    addFriend()
    val friends = remember {
        friends
    }
    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "App Icon",
        )
        LazyColumn(
            state = LazyListState()
        )
        {
            items(friends) {
                    item -> Text(text = item)
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun FriendListPreview() {
    FriendList(modifier = Modifier)
}

