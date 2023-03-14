package com.example.letsmeet.authorization

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.letsmeet.authorization.AuthFireBase.Companion.auth
import com.example.letsmeet.authorization.ui.theme.ui.theme.LetsMeetTheme

@Composable
fun signUp(modifier: Modifier, navController: NavController) {
    var email = rememberSaveable{
        mutableStateOf("")
    }
    var pw = rememberSaveable {
        mutableStateOf("")
    }
    var name = rememberSaveable {
        mutableStateOf("")
    }
    var pw_check = rememberSaveable {
        mutableStateOf("")
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(value = name.value,
            onValueChange = {nameValue -> name.value = nameValue},
            label = { Text("이름") },
            modifier = modifier
                .fillMaxWidth()
        )

        TextField(value = email.value,
            onValueChange = {emailValue -> email.value = emailValue},
            label = { Text("이메일") },
            modifier = modifier
                .fillMaxWidth()
        )

        TextField(value = pw.value,
            onValueChange = {pwValue -> pw.value = pwValue},
            label = { Text("비밀번호") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = modifier
                .fillMaxWidth()
        )

        TextField(value = pw_check.value,
            onValueChange = {pw_checkValue -> pw_check.value = pw_checkValue},
            label = { Text("비밀먼호 확인") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = modifier
                .fillMaxWidth()
        )

        if(pw.value == pw_check.value && pw.value != "" && pw_check.value != ""){
            Text(text = "비밀번호가 일치합니다", color = Color.Blue)
        }
        else if(pw.value != pw_check.value && pw.value != "" && pw_check.value != ""){
            Text(text = "비밀번호가 일치하지 않습니다.", color = Color.Red)
        }

        Button(onClick = { if (pw == pw_check){
            register(email,pw)
        } }, modifier = modifier.fillMaxWidth()) {
            Text(text = "회원가입하기")
        }

    }

}

fun register(id : MutableState<String>, password : MutableState<String> ){
    auth.createUserWithEmailAndPassword(id.toString(),password.toString())
        .addOnCompleteListener{task ->
            if(task.isSuccessful){
                auth.currentUser?.sendEmailVerification()
                    ?.addOnCompleteListener { sendTask ->
                        if(sendTask.isSuccessful){

                        }
                        else{
                            Log.e("[ERROR]","error",task.exception)
                        }
                    }
            }

        }

}




@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun signUpPreview() {
    signUp(modifier = Modifier.padding(20.dp), navController = rememberNavController())
}