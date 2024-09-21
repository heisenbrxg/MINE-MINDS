package com.tl.mineminds

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tl.mineminds.ui.screen.LoginScreen
import com.tl.mineminds.ui.screen.ScreenNames
import com.tl.mineminds.ui.theme.MineMindsTheme


class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initSharedPref(getSharedPref())
        enableEdgeToEdge()
        setContent {
            MineMindsTheme {
                Surface() {

                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = ScreenNames.LOGIN.routeName) {
                        composable(ScreenNames.LOGIN.routeName,
                            exitTransition = { slideOutHorizontally(targetOffsetX = {-it}) }
                        ) {
                            LoginScreen(viewModel::onUsernameEntered)
                        }
                        composable(ScreenNames.MAIN.routeName,
                            enterTransition = { slideInHorizontally(initialOffsetX = {it}) })
                        {

                        }
                    }
                    viewModel.currentRoute.observeForever {
                        navController.navigate(it) {
                            popUpTo(ScreenNames.LOGIN.routeName) {inclusive = true}
                        }
                    }
                }
            }
        }
    }

    private fun getSharedPref(): SharedPreferences {
        val sharedPref = getSharedPreferences("MineMinds", MODE_PRIVATE)
        return sharedPref
    }
}