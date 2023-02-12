package com.example.letsmeet.authorization

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.letsmeet.authorization.ui.theme.LetsMeetTheme

class SignInUi : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LetsMeetTheme {
                signIn(modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun signIn(modifier: Modifier) {

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(value = "",
            onValueChange = {},
            placeholder = { Text(text = "Email") },
            modifier = modifier
                .fillMaxWidth()
        )
        TextField(value = "",
            onValueChange = {},
            placeholder = { Text(text = "PW") },
            modifier = modifier
                .fillMaxWidth()

        )
        Button(onClick = { /*TODO*/ }, modifier = modifier.fillMaxWidth()) {
            Text(text = "로그인")
        }
        Button(onClick = { /*TODO*/ }, modifier = modifier.fillMaxWidth()) {
            Text(text = "회원가입")
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun signInPreview() {
    signIn(modifier = Modifier.padding(20.dp))
}
