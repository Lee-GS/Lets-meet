package com.example.letsmeet

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.letsmeet.authorization.SignInUi
import com.example.letsmeet.authorization.signIn

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SignInScreen.route){
        Composable(route = Screen.SignInScreen.route){
        }
    }
}