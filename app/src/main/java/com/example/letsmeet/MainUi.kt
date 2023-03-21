package com.example.letsmeet

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.sql.Time

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUi(navController: NavController){
    Scaffold(
        modifier = Modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "USW") },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.Menu, contentDescription = "menu")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle action icon click */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        }
    ){innerPadding ->
        TimeLine(Modifier.padding(innerPadding))
    }
}

@Composable
fun TimeLine(modifier: Modifier){
    var time = rememberSaveable {
        mutableStateOf("")
    }
    var place = rememberSaveable {
        mutableStateOf("")
    }
    var plan = rememberSaveable {
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
            onValueChange = { timeValue -> time.value = timeValue},
            label = { Text("시간") },
            modifier = modifier.width(100.dp)
        )
        TextField(
            value = place.value,
            onValueChange = { placeValue -> place.value = placeValue},
            label = { Text("장소") },
            modifier = modifier.width(100.dp)
        )
        TextField(
            value = plan.value,
            onValueChange = { planValue -> plan.value = planValue},
            label = { Text("계획") },
            modifier = modifier.width(100.dp)
        )
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun MainUiPreview(){
    MainUi(navController = rememberNavController())
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun TimeLinePreview(){
    TimeLine(modifier = Modifier.padding(8.dp))
}

