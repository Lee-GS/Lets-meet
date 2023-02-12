package com.example.letsmeet.authorization

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.letsmeet.authorization.ui.theme.ui.theme.LetsMeetTheme

class SignUpUi : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LetsMeetTheme {
                // A surface container using the 'background' color from the theme
            }
        }
    }
}

@Composable
fun signUp(modifier: Modifier) {

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
            Text(text = "회원가입하기")
        }

    }
}


@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun signUpPreview() {
    signUp(modifier = Modifier.padding(20.dp))
}