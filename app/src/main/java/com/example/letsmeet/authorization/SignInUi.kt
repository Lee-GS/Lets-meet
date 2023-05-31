package com.example.letsmeet.authorization

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.letsmeet.MainActivity
import com.example.letsmeet.Screen
import com.example.letsmeet.authorization.AuthFireBase.Companion.auth
import com.example.letsmeet.authorization.AuthFireBase.Companion.checkAuth
import com.example.letsmeet.authorization.AuthFireBase.Companion.email
import com.example.letsmeet.authorization.ui.theme.LetsMeetTheme

@Composable
fun signIn(modifier: Modifier,navController: NavController) {
    val context = LocalContext.current
    var email = rememberSaveable{
        mutableStateOf("")
    }
    var pw = rememberSaveable {
        mutableStateOf("")
    }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(value = email.value,
            onValueChange = {emailValue -> email.value = emailValue},
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            label = {Text("Email")},
            modifier = modifier
                .fillMaxWidth()
        )
        TextField(value = pw.value,
            onValueChange = {pwValue -> pw.value = pwValue},
            label = { Text("PW") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = modifier
                .fillMaxWidth()
                .imePadding()

        )
        Button(onClick = { if(email.value != "" && pw.value !=" ") login(email.value,pw.value,context,navController) },
            modifier = modifier.fillMaxWidth()) {
            Text(text = "로그인")

        }
        Button(onClick = { navController.navigate(route = Screen.SignUpScreen.route)},
            modifier = modifier.fillMaxWidth()
        ) {
            Text(text = "회원가입")
        }
    }
}

fun login(email: String, password: String, context: Context,navController: NavController){
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { it ->
            if(it.isSuccessful){
                if (checkAuth()){
                    AuthFireBase.email=email
                    Toast.makeText(context,"로그인 되었습니다!",Toast.LENGTH_SHORT).show()
                    Log.d("현재 유저:","${AuthFireBase.email}")
                    navController.navigate(Screen.MainScreen.route){
                        popUpTo("signin_screen"){inclusive = true}
                    }
                }else{

                }
            }else{
                Toast.makeText(context,"로그인 실패",Toast.LENGTH_SHORT).show()
            }
        }
}


@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun signInPreview() {
    signIn(modifier = Modifier.padding(20.dp), navController = rememberNavController())
}
