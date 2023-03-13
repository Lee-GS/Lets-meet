package com.example.letsmeet

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MainUi(){
    Scaffold(bottomBar = {
        BottomAppBar() {
            
        }
    }) {

    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun MainUiPreview(){
    MainUi()
}