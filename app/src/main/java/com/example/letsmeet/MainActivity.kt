package com.example.letsmeet

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.letsmeet.authorization.signIn
import com.example.letsmeet.navigationDrawer.addFriend
import com.example.letsmeet.ui.theme.LetsMeetTheme
import kotlinx.coroutines.coroutineScope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LetsMeetTheme {
                Navigation()
            }
        }
        instance = this
    }

    companion object {
        lateinit var instance: MainActivity
        fun applicationContext() : Context {
            return instance.applicationContext
        }
    }

}

