package com.example.letsmeet.mainScreen

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.letsmeet.navigationDrawer.FriendList
import com.example.letsmeet.MyAppBar
import com.example.letsmeet.authorization.AuthFireBase
import com.example.letsmeet.navigationDrawer.acceptFriend
import com.example.letsmeet.navigationDrawer.db
import com.example.letsmeet.ui.theme.Purple40

val currentEmail = AuthFireBase.auth.currentUser?.email

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUi() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var fname by remember { mutableStateOf("") }
    var opendialog by remember {
        mutableStateOf(false)
    }
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
                    items(10) { PlanList() }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { },
                    content = {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
                    }
                )
            }


        )
    }

    if (currentEmail != null) {
        LaunchedEffect(currentEmail) {
            db.collection("users").document(currentEmail).get().addOnSuccessListener { document ->
                if (document != null) {
                    val _fname = document.get("friendrequest").toString()
                    fname = _fname
                    Log.d("Succcess", "$_fname")
                }
            }
        }
        if (fname.isNotEmpty()){
            opendialog=true
        }
        if(opendialog){
            acceptFriend(fname) {
                fname = ""
                opendialog = false
            }
        }

    }

}

@Composable
fun TimeLine(modifier: Modifier) {
    val time = rememberSaveable {
        mutableStateOf("")
    }
    val place = rememberSaveable {
        mutableStateOf("")
    }
    val plan = rememberSaveable {
        mutableStateOf("")
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    )
    {
        TextField(
            value = time.value,
            onValueChange = { timeValue -> time.value = timeValue },
            label = { Text("시간") },
            modifier = modifier.width(100.dp)
        )
        TextField(
            value = place.value,
            onValueChange = { placeValue -> place.value = placeValue },
            label = { Text("장소") },
            modifier = modifier.width(100.dp)
        )
        TextField(
            value = plan.value,
            onValueChange = { planValue -> plan.value = planValue },
            label = { Text("계획") },
            modifier = modifier.width(100.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanList() {
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
                items(10) {
                    TimeLine(modifier = Modifier)
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
fun TimeLinePreview() {
    TimeLine(modifier = Modifier.padding(8.dp))
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun PlanListPreview() {
    PlanList()
}
