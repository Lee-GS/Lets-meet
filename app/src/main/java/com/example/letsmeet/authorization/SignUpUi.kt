package com.example.letsmeet.authorization

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.letsmeet.Screen
import com.example.letsmeet.authorization.AuthFireBase.Companion.auth
import com.example.letsmeet.authorization.AuthFireBase.Companion.firestore

@Composable
fun signUp(modifier: Modifier, navController: NavController) {
    val context = LocalContext.current
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
    var friendList = mutableListOf<String>()


    Column(
        Modifier.fillMaxSize().background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(value = name.value,
            onValueChange = {nameValue -> name.value = nameValue},
            label = { Text("이름") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = modifier
                .fillMaxWidth()
        )

        TextField(value = email.value,
            onValueChange = {emailValue -> email.value = emailValue},
            label = { Text("이메일") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = modifier
                .fillMaxWidth()
        )

        TextField(value = pw.value,
            onValueChange = {pwValue -> pw.value = pwValue},
            label = { Text("비밀번호") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
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
        var userDTO = UserDTO(name.value,email.value,pw.value,friendList)

        Button(
            onClick = {
            if (pw.value == pw_check.value){
                register(email.value,pw.value,navController,context)
                firestore.collection("users").document(email.value).set(userDTO)
                } },
            modifier = modifier.fillMaxWidth()) {
            Text(text = "회원가입하기")
        }
    }
}

fun register(id : String, password : String, navController: NavController, context: Context ){
    auth.createUserWithEmailAndPassword(id,password)
        .addOnCompleteListener{task ->
            if(task.isSuccessful){
                auth.currentUser?.sendEmailVerification()
                    ?.addOnCompleteListener { sendtask ->
                        if(sendtask.isSuccessful){

                            Toast.makeText(context,"회원가입에 성공하였습니다." +
                                    "전송된 이메일을 확인해 주세요!",Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(context,"메일 전송실패!",Toast.LENGTH_SHORT).show()
                        }
                    }
                navController.navigate(Screen.DialogScreen.route)
            }
            else{
                Toast.makeText(context,"회원가입에 실패하였습니다.",Toast.LENGTH_SHORT).show()
                Log.e("[ERROR]","error",task.exception)
            }
        }
}





@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun signUpPreview() {
    signUp(modifier = Modifier.padding(20.dp), navController = rememberNavController())
}



