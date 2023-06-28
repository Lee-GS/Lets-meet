package com.example.letsmeet.navigationDrawer

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.letsmeet.MainActivity
import com.example.letsmeet.R
import com.example.letsmeet.navigationDrawer.FriendData.Companion.friends
import com.example.letsmeet.room.database.FriendDatabase
import com.example.letsmeet.room.entity.FriendData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


fun addFriend(){
    val db = FriendDatabase.getInstance(MainActivity.applicationContext())
    CoroutineScope(Dispatchers.IO).launch {
        friends = db!!.friendDao().getAll() as ArrayList<FriendData>
        Log.d("친구 목록","$friends")
    }
}


@Composable
fun FriendList(modifier: Modifier) {
    Log.d("drawer","flag")
    addFriend()
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
                item: FriendData -> Text(text = item.name )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun FriendListPreview() {
    FriendList(modifier = Modifier)
}

