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
import com.example.letsmeet.MyAppBar
import com.example.letsmeet.authorization.AuthFireBase
import com.example.letsmeet.navigationDrawer.FriendList
import com.example.letsmeet.navigationDrawer.acceptFriend
import com.example.letsmeet.room.database.ContentDatabase
import com.example.letsmeet.room.entity.ContentData
import com.example.letsmeet.ui.theme.Purple40
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUi() {
    val count = remember { mutableStateOf(0) }
    var fname = remember { mutableStateListOf<String>() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val opendialog = remember { mutableStateOf(false) }
    val openfloating = remember { mutableStateOf(false) }
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
                    items(0) {
                        PlanList(count, flag)
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { openfloating.value = true },
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
            opendialog.value = true
            for (i in 0 until fname.size) {
                if (opendialog.value) {
                    acceptFriend(fname[i]) {
                        opendialog.value = false
                        fname.remove(fname[i])
                    }
                }
            }

        }

        if (openfloating.value) {
            addPlanList(flag) {
                openfloating.value = false
            }
        }
    }


}

@Composable
fun addPlanList(flag: MutableState<Boolean>, onChange: () -> Unit) {
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
            PlanList(num, flag)
        },
        confirmButton = {
            TextButton(onClick = {
                flag.value = true
                Log.d("value1", flag.toString())
                CoroutineScope(Dispatchers.IO).launch {
                    getContents(flag)
                }
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
fun TimeLine(
    modifier: Modifier,
    flag: MutableState<Boolean>,
    timeList: MutableList<String>,
    planList: MutableList<String>
) {
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
            placeholder = { Text("시간") },
            modifier = modifier.width(100.dp)
        )
        TextField(
            value = plan.value,
            onValueChange = { planValue -> plan.value = planValue },
            placeholder = { Text("계획", textAlign = TextAlign.Center) },
            modifier = modifier.width(250.dp)
        )
    }
    Log.d("time1", time.value)
    if (flag.value) {
        timeList.add(time.value)
        planList.add((plan.value))
        Log.d(
            "시간,계획",
            time.value + " " + timeList.toList() + " " + planList.toList() + " " + plan.value
        )
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanList(count: MutableState<Int>, flag: MutableState<Boolean>) {
    val date = rememberSaveable {
        mutableStateOf("")
    }
    val timeList = remember {
        mutableStateListOf<String>()
    }
    val planList = remember {
        mutableStateListOf<String>()
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
                    TimeLine(modifier = Modifier.padding(1.dp), flag, timeList, planList)
                }
            }
            Log.d("날짜1", date.value)
            Log.d("tl,pl1", (timeList + planList).toList().toString())
        }
        Log.d("tl,pl2", (timeList + planList).toList().toString())
        if (flag.value) {
            CoroutineScope(Dispatchers.IO).launch {
                delay(1000L)
                Log.d("room db 삽입 날짜", date.value + " " +timeList.toList() +" "+ planList.toList())
                ContentDatabase.getInstance(MainActivity.instance)!!.contentDao()
                    .insertDate(
                        ContentData(date.value, timeList, planList)
                    )
            }

        }
    }
}



suspend fun getContents(flag: MutableState<Boolean>) {
    delay(2000L)
    val db = ContentDatabase.getInstance(MainActivity.applicationContext())
    val contents = db!!.contentDao().getAll() as ArrayList<ContentData>
    flag.value = false
    Log.d("contents", "$contents")

}


@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun MainUiPreview() {
    MainUi()
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun AddPlanListPreview() {
    val arg = remember {
        mutableStateOf(false)
    }
    addPlanList(arg) { }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun TimeLinePreview() {
    val arg = remember {
        mutableStateOf(false)
    }
    val list1 = mutableListOf<String>()
    val list2 = mutableListOf<String>()
    TimeLine(modifier = Modifier.padding(8.dp), arg, list1, list2)
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun PlanListPreview() {
    val arg = remember {
        mutableStateOf(3)
    }
    val arg2 = remember {
        mutableStateOf(false)
    }
    PlanList(arg, arg2)
}
