package com.example.letsmeet

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.letsmeet.authorization.AuthFireBase
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.reflect.typeOf


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(drawerState: DrawerState, scope: CoroutineScope, visible : MutableState<Boolean>) {
    val isDropDownMenuExpanded = remember {
        mutableStateOf(false)
    }
    val opendialog = remember {
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
                        visible.value = !visible.value
                        isDropDownMenuExpanded.value = false
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "친구추가 하기") },
                    onClick = {
                        opendialog.value = true
                        isDropDownMenuExpanded.value = false
                    }
                )
            }
        }
    )
    if (opendialog.value) {
        AddFriendsDialog()
        { opendialog.value = false }
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
    var friend = rememberSaveable {
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

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun AddFriendsDialogPreview() {
    AddFriendsDialog { }
}