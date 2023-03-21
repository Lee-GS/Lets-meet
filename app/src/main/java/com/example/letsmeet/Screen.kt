package com.example.letsmeet

sealed class Screen(val route: String){
    object SignInScreen: Screen("signin_screen")
    object SignUpScreen: Screen("signup_screen")
    object DialogScreen: Screen("dialog_screen")
    object MainScreen: Screen("Main_screen")

}
