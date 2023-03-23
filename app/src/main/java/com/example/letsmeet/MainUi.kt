package com.example.letsmeet

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.letsmeet.ui.theme.Pink40
import com.example.letsmeet.ui.theme.Purple40
import com.example.letsmeet.ui.theme.Purple80
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUi(navController: NavController){
    Surface(color = Purple80) {
        Scaffold(
            topBar = { MyAppBar() },
            content = {
                innerPadding ->
                LazyColumn(
                    contentPadding = innerPadding,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally){
                    items(100) {it ->  Text(text = "$it item") }
                }
            }
        )
    }
}

@Composable
fun MyAppBar(){
    CenterAlignedTopAppBar(
        title = { Text(text = "USW") },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Person, contentDescription = "Person")
            }
        },
        actions = {
            IconButton(onClick = { /* Handle action icon click */ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    )

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanList(){
    var date = rememberSaveable {
        mutableStateOf("")
    }
    Card(modifier = Modifier.padding(8.dp) ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Purple40)
                .padding(8.dp)
        ) {
            TextField(
                value = date.value,
                onValueChange = {dateValue -> date.value = dateValue},
                placeholder = { Text(text = "ex) 2/28")},
                modifier = Modifier.width(100.dp))
            LazyColumn{
                item {
                    TimeLine(modifier = Modifier)
                    TimeLine(modifier = Modifier)
                    TimeLine(modifier = Modifier)
                }
            }
        }
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

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun PlanListPreview(){
    PlanList()
}
