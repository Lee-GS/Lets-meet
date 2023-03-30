package com.example.letsmeet

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(drawerState: DrawerState, scope: CoroutineScope) {
    var isDropDownMenuExpanded by remember {
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
                    text = { Text(text = "공유하기")},
                    onClick = { /*TODO*/ }
                )
                DropdownMenuItem(
                    text = { Text(text = "친구추가 하기")},
                    onClick = { /*TODO*/ }
                )
            }
        },

        )
}

