package com.example.letsmeet.navigationDrawer

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.letsmeet.MyApplication
import com.example.letsmeet.R
import com.example.letsmeet.navigationDrawer.FriendData.Companion.friends
import com.example.letsmeet.room.database.FriendDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


fun addFriend(){
    val db = FriendDatabase.getInstance(MyApplication.ApplicationContext())
    CoroutineScope(Dispatchers.IO).launch {
        friends.addAll(db!!.friendDao().getAll())
        Log.d("친구목록",friends.toString())
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
                it
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun FriendListPreview() {
    FriendList(modifier = Modifier)
}

