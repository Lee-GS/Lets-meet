package com.example.letsmeet.authorization

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun dialogContent(navController: NavController){
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom,
    ){
        Spacer(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth()
        )
        Text(
            text = "회원가입이 완료 되었습니다!",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        )
        Spacer(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth()
        )
        Button(
            onClick = {
                navController.popBackStack("signin_screen",inclusive = false)
                      },
            modifier = Modifier.padding(5.dp)

        ) {
            Text(text = "확인")
        }


    }
}

@Composable
fun registerDialog(navController: NavController){
        Dialog(
            onDismissRequest = {},
        ) {
            Surface(
                modifier = Modifier
                    .width(300.dp)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(12.dp),
                color = Color.White

            ) {
                dialogContent(navController)
            }
        }
    }

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun dialogContentPreview() {
    dialogContent(navController = rememberNavController())
}