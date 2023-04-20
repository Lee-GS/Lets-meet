package com.example.letsmeet

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.letsmeet.authorization.registerDialog
import com.example.letsmeet.authorization.signIn
import com.example.letsmeet.authorization.signUp
import com.example.letsmeet.mainScreen.MainUi

@Composable
fun Navigation(navController: NavController){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.SignInScreen.route)
    {
        composable(route = Screen.SignInScreen.route){
            signIn(modifier = Modifier.padding(8.dp), navController = navController)
        }
        composable(route = Screen.SignUpScreen.route){
            signUp(modifier = Modifier.padding(20.dp), navController = navController)
        }
        composable(route = Screen.DialogScreen.route){
            registerDialog(navController = navController)
        }
        composable(route = Screen.MainScreen.route){
            MainUi()
        }
    }
}