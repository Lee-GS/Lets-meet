package com.example.letsmeet

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.letsmeet.authorization.AuthFireBase
import com.example.letsmeet.navigationDrawer.FriendData
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(drawerState: DrawerState, scope: CoroutineScope, visible : MutableState<Boolean>) {
    val isDropDownMenuExpanded = remember {
        mutableStateOf(false)
    }
    val openFriendDialog = remember {
        mutableStateOf(false)
    }
    val openContentsDialog = remember {
        mutableStateOf(false)
    }
    CenterAlignedTopAppBar(
        title = { Text(text = "USW") },
        navigationIcon = {
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                Icon(Icons.Default.Person, contentDescription = "Person")
            }
        },
        actions = {
            IconButton(onClick = {
                isDropDownMenuExpanded.value = !isDropDownMenuExpanded.value
            }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
            DropdownMenu(
                expanded = isDropDownMenuExpanded.value,
                onDismissRequest = { isDropDownMenuExpanded.value = false },
                modifier = Modifier.wrapContentSize()
            ) {
                DropdownMenuItem(
                    text = { Text(text = "공유하기") },
                    onClick = {
                        openContentsDialog.value = true
                        isDropDownMenuExpanded.value = false
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "친구추가 하기") },
                    onClick = {
                        openFriendDialog.value = true
                        isDropDownMenuExpanded.value = false
                    }
                )
            }
        }
    )
    if (openFriendDialog.value) {
        AddFriendsDialog { openFriendDialog.value = false }

    }
    if (openContentsDialog.value) {
        shareContentsDialog(onChange = { openContentsDialog.value = false }, visible = visible)
    }

}

fun requestFriend(email : String){
    AuthFireBase.firestore.collection("users").document(email).update("friendrequest", FieldValue.arrayUnion(
        AuthFireBase.email)).addOnSuccessListener {
        Log.d("SUCCESS","친구추가 전송 성공 ${AuthFireBase.email}")
        Toast.makeText(MainActivity.applicationContext(),"친구요청을 보냈습니다!",Toast.LENGTH_SHORT).show()
    }
}


@Composable
fun AddFriendsDialog(onChange: () -> Unit) {
    val friend = rememberSaveable {
        mutableStateOf("")
    }
    AlertDialog(
        onDismissRequest = { onChange() },
        title = {
            Text(
                text = "친구 추가",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column() {
                TextField(
                    value = friend.value,
                    onValueChange = { friendValue -> friend.value = friendValue },
                    placeholder = { Text(text = "이메일을 입력하세요") }
                )
                TextButton(
                    onClick = {
                        requestFriend(friend.value)
                        onChange()
                    }) {
                    Text(
                        text = "추가하기",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onChange() }) {
                Text(text = "닫기")
            }
        }
    )
}

@Composable
fun shareContentsDialog(onChange: () -> Unit, visible : MutableState<Boolean>){
    val isButtonClicked = remember {
        mutableStateOf(false)
    }
    val buttonColor = if(isButtonClicked.value) Color.Blue else Color.Magenta

    AlertDialog(
        onDismissRequest = { onChange()},
        title = {
            Text(
                text = "공유할 친구를 선택해주세요!",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        },
        text = {
            LazyColumn(){
                items(FriendData.friends) {
                    Button(
                       onClick = {
                           ShareList.shareList.add(it)
                           isButtonClicked.value != isButtonClicked.value
                       }
                    ){
                        Text(text = it, textAlign = TextAlign.Center)
                    }

                }
            }
        },
        confirmButton = {
            TextButton(onClick = { visible.value = !visible.value}) {
                Text(text = "선택완료")
            }
        },
        dismissButton = {
            TextButton(onClick = { onChange() }) {
                Text(text = "닫기")
            }
        }

    )
}

object ShareList{
    val shareList = mutableListOf<String>()
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun AddFriendsDialogPreview() {
    AddFriendsDialog { }
}


@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun shareContentsDialogPreview() {
    val para = remember {
        mutableStateOf<Boolean>(true)
    }
    shareContentsDialog({},para)
}