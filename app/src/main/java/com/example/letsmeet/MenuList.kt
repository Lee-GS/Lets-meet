package com.example.letsmeet

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Menu(isDropDownMenuExpanded : Boolean){
    DropdownMenu(
        expanded = isDropDownMenuExpanded,
        onDismissRequest = { isDropDownMenuExpanded},
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
}

@Preview(showBackground = false, backgroundColor = 0xFFF0EAE2)
@Composable
fun MenuPreview(){
    Menu(isDropDownMenuExpanded = true)
}