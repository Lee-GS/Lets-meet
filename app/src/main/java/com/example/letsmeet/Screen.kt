package com.example.letsmeet

sealed class Screen(val route: String){
    object SignInScreen: Screen("signin_screen")
    object SignUpScreen: Screen("signup_screen")
}
