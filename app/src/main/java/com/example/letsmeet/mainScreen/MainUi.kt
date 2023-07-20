package com.example.letsmeet.mainScreen

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.letsmeet.MainActivity
import com.example.letsmeet.MyAppBar
import com.example.letsmeet.authorization.AuthFireBase
import com.example.letsmeet.navigationDrawer.FriendList
import com.example.letsmeet.navigationDrawer.acceptFriend
import com.example.letsmeet.room.database.ContentDatabase
import com.example.letsmeet.room.entity.ContentData
import com.example.letsmeet.ui.theme.Purple40
import kotlinx.coroutines.*


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUi() {
    val fname = remember { mutableStateListOf<String>() }
    val visible = remember { mutableStateOf(false) }
    val flag = remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val contentsDeffered = scope.async(Dispatchers.IO) {
        getContents()
    }
    val _contents = runBlocking { contentsDeffered.await() }
    val contents = remember { mutableStateListOf<ContentData>() }
    for(i in _contents.reversed()){ contents.add(i) }
    val openDialog = remember { mutableStateOf(false) }
    val openFloating = remember { mutableStateOf(false) }
    ModalNavigationDrawer(
        drawerContent = { FriendList() },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = { MyAppBar(drawerState, scope, visible) },
            content = { innerPadding ->
                LazyColumn(
                    Modifier.fillMaxWidth(),
                    contentPadding = innerPadding,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    items(contents) {
                        MainPlanList(it,visible)
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { openFloating.value = true },
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
                        }
                    }
                }
        }
        if (fname.isNotEmpty()) {
            openDialog.value = true
            for (i in 0 until fname.size) {
                if (openDialog.value) {
                    acceptFriend(fname[i]) {
                        openDialog.value = false
                        fname.remove(fname[i])
                    }
                }
            }

        }

        if (openFloating.value) {
            addPlanList(contents,flag){
                openFloating.value = false
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPlanList(contents: ContentData, visible : MutableState<Boolean>) {
    val combinedList = contents.time.zip(contents.plan) { time, plan ->
        time to plan
    }
    var ischeked = rememberSaveable {
        mutableStateOf(false)
    }
    Card(modifier = Modifier.padding(8.dp)) {
        Column(
            modifier = Modifier
                .background(Purple40)
                .padding(8.dp)
        ) {
            Row() {
                Text(
                    text = contents.date,
                    color = Color.White,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(start = 165.dp)

                )
                if(visible.value) {
                    Checkbox(
                        checked = ischeked.value,
                        onCheckedChange = {ischeked.value = it},
                        //colors = CheckboxDefaults.colors(Color.LightGray),
                        modifier = Modifier
                            .padding(start = 110.dp)
                    )
                }
            }
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(combinedList) { (time, plan) ->
                    Row {
                        Text(
                            text = time,
                            color = Color.White,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp
                        )
                        Text(
                            text = plan,
                            color = Color.Green,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun addPlanList(contents: SnapshotStateList<ContentData>,flag: MutableState<Boolean>, onChange: () -> Unit,) {
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
            PlanList(num, flag, contents)
        },
        confirmButton = {
            TextButton(onClick = {
                flag.value = true
                CoroutineScope(Dispatchers.IO).launch {
                    delay(2000L)
                    flag.value = false
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
    planList: MutableList<String>,
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
fun PlanList(count: MutableState<Int>, flag: MutableState<Boolean>, contents: SnapshotStateList<ContentData>) {
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
        }
        if (flag.value) {
            CoroutineScope(Dispatchers.IO).launch {
                delay(1000L)
                Log.d(
                    "room db 삽입 날짜",
                    date.value + " " + timeList.toList() + " " + planList.toList()
                )
                ContentDatabase.getInstance(MainActivity.instance)!!.contentDao()
                    .insertDate(
                        ContentData(date.value, timeList, planList)
                    )
            }
            contents.add(0,ContentData(date.value, timeList, planList))
        }
    }
}


suspend fun getContents(): List<ContentData> {
    delay(2000L)
    val db = ContentDatabase.getInstance(MainActivity.applicationContext())
    val contents = db!!.contentDao().getAll()
    Log.d("contents", "$contents")
    return contents
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
    //addPlanList(arg) { }
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
   // PlanList(arg, arg2)
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun MainPlanList() {
    val a = listOf<String>("123", "345")
    val b = listOf<String>("123", "345")
    val c = remember {
        mutableStateOf<Boolean>(true)
    }
    MainPlanList(ContentData("!23", a, b),c)
}
