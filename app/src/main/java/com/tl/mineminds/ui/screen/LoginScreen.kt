package com.tl.mineminds.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.tl.mineminds.R

@OptIn(ExperimentalCoilApi::class)
@Composable
fun LoginScreen(onUsernameEntered:(String) -> Unit) {
    var userName by rememberSaveable { mutableStateOf("") }
    Box {
        Image(painter = rememberImagePainter(R.drawable.login_illustration), contentDescription = "", contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
        Box(modifier = Modifier
            .fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Column(modifier = Modifier.safeContentPadding()) {
                TextField(modifier = Modifier.fillMaxWidth(), value = userName, onValueChange = {userName = it}, label = {Text("Enter username")} )
                Spacer(modifier = Modifier.height(16.dp))
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    //do validations here
                    onUsernameEntered(userName)
                }) {
                    Text(text = "Login")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}