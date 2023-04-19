package com.example.letsmeet

import android.util.Log
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
import com.example.letsmeet.mainScreen.currentEmail
import com.example.letsmeet.navigationDrawer.db
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.reflect.typeOf


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(drawerState: DrawerState, scope: CoroutineScope) {
    var isDropDownMenuExpanded by remember {
        mutableStateOf(false)
    }
    var opendialog by remember {
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
                isDropDownMenuExpanded = !isDropDownMenuExpanded
            }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
            DropdownMenu(
                expanded = isDropDownMenuExpanded,
                onDismissRequest = { isDropDownMenuExpanded = false },
                modifier = Modifier.wrapContentSize()
            ) {
                DropdownMenuItem(
                    text = { Text(text = "공유하기") },
                    onClick = {
                        isDropDownMenuExpanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "친구추가 하기") },
                    onClick = {
                        opendialog = true
                        isDropDownMenuExpanded = false
                    }
                )
            }
        }
    )
    if (opendialog) {
        AddFriendsDialog()
        { opendialog = false }
    }

}

fun requestFriend(email : String){
    db.collection("users").document(email).update("friendrequest", FieldValue.arrayUnion(
        currentEmail)).addOnSuccessListener {
        Log.d("SUCCESS","친구추가 전송 성공 $currentEmail")
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