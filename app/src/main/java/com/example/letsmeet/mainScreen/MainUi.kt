package com.example.letsmeet.mainScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.letsmeet.MainActivity
import com.example.letsmeet.navigationDrawer.FriendList
import com.example.letsmeet.MyAppBar
import com.example.letsmeet.authorization.AuthFireBase
import com.example.letsmeet.navigationDrawer.acceptFriend
import com.example.letsmeet.room.database.ContentDatabase
import com.example.letsmeet.room.database.FriendDatabase
import com.example.letsmeet.room.entity.ContentData
import com.example.letsmeet.room.entity.FriendData
import com.example.letsmeet.ui.theme.Purple40
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUi() {
    val count =  remember { mutableStateOf(3) }
    var fname = remember { mutableStateListOf<String>() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var opendialog by remember { mutableStateOf(false) }
    var openfloating by remember { mutableStateOf(false) }
    val flag = remember { mutableStateOf(false) }
    ModalNavigationDrawer(
        drawerContent = { FriendList(modifier = Modifier) },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = { MyAppBar(drawerState, scope) },
            content = { innerPadding ->
                LazyColumn(
                    Modifier.fillMaxWidth(),
                    contentPadding = innerPadding,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    items(3) {
                        PlanList(count,flag)
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { openfloating = true },
                    content = {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
                    }
                )
            }


        )
    LaunchedEffect(AuthFireBase.email) {
        AuthFireBase.firestore.collection("users").document(AuthFireBase.email!!).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val _fname = document.get("friendrequest") as ArrayList<String>
                    for (element in _fname) {
                        fname.add(element)
                        Log.d("fr1", fname.toString())
                    }
                }
            }
    }
        if (fname.isNotEmpty()) {
            opendialog = true
            for (i in 0 until fname.size) {
                if (opendialog){
                    acceptFriend(fname[i]) {
                        opendialog = false
                        fname.remove(fname[i])
                    }
                }
            }

        }

        if (openfloating) {
            addPlanList(flag) {
                openfloating = false
            }
        }
    }


}

@Composable
fun addPlanList(flag : MutableState<Boolean> ,onChange: () -> Unit) {
    val num = remember {
        mutableStateOf(1)
    }

    AlertDialog(
        onDismissRequest = { onChange() },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "일정 추가하기",
                    textAlign = TextAlign.Center
                )
                IconButton(
                    onClick = { num.value++ },
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
                }
            }
        },
        text = {
            PlanList(num,flag)
        },
        confirmButton = {
            TextButton(onClick = {
                flag.value = true
                flag.value = false
                onChange()
            }) {
                Text(text = "추가")
            }
        },
        dismissButton = {
            TextButton(onClick = { onChange() }) {
                Text(text = "닫기")
            }
        }
    )
}


@Composable
fun TimeLine(modifier: Modifier, flag : MutableState<Boolean>) {
    val time = rememberSaveable {
        mutableStateOf("")
    }
    val plan = rememberSaveable {
        mutableStateOf("")
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    )
    {
        TextField(
            value = time.value,
            onValueChange = { timeValue -> time.value = timeValue },
            placeholder =  { Text("시간") },
            modifier = modifier.width(100.dp)
        )
        TextField(
            value = plan.value,
            onValueChange = {  planValue -> plan.value = planValue  },
            placeholder = { Text("계획", textAlign = TextAlign.Center) },
            modifier = modifier.width(250.dp)
        )
    }
    if (flag.value) {
        LaunchedEffect(time) {
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("room db 삽입", time.toString() + plan.toString())
                ContentDatabase.getInstance(MainActivity.instance)!!.contentDao()
                    .insertPlan(
                        ContentData(time.toString()),
                        ContentData(plan.toString())
                    )
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanList(count: MutableState<Int>, flag: MutableState<Boolean>) {
    val date = rememberSaveable {
        mutableStateOf("")
    }

    Card(modifier = Modifier.padding(8.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Purple40)
                .padding(8.dp)
        ) {
            TextField(
                value = date.value,
                onValueChange = { dateValue -> date.value = dateValue },
                placeholder = { Text(text = "ex) 2/28") },
                modifier = Modifier.width(100.dp)
            )
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                items(count.value) {
                    TimeLine(modifier = Modifier.padding(1.dp), flag)
                }
            }
        }
        if (flag.value) {
            LaunchedEffect(date) {
                CoroutineScope(Dispatchers.IO).launch {
                    Log.d("room db 삽입", date.toString())
                    ContentDatabase.getInstance(MainActivity.instance)!!.contentDao()
                        .insertDate(
                            ContentData(date.toString())
                        )
                }

            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun MainUiPreview() {
    MainUi()
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun AddPlanListPreview() {
    val arg  = remember {
        mutableStateOf(false)
    }
    addPlanList(arg) { }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun TimeLinePreview() {
    val arg  = remember {
        mutableStateOf(false)
    }
   TimeLine(modifier = Modifier.padding(8.dp),arg)
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun PlanListPreview() {
    val arg  = remember {
        mutableStateOf(3)
    }
    val arg2  = remember {
        mutableStateOf(false)
    }
    PlanList(arg,arg2)
}
