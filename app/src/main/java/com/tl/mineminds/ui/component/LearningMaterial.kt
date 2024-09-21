package com.tl.mineminds.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.tl.mineminds.R
import com.tl.mineminds.entity.LearningMaterial

@OptIn(ExperimentalCoilApi::class)
@Composable
fun LearningMaterial(learningMaterial: LearningMaterial) {
    val scrollable = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollable)) {
        Text(text = learningMaterial.title)
        Image(painter = rememberImagePainter(R.drawable.login_illustration), contentDescription = "", contentScale = ContentScale.Crop, modifier = Modifier
            .fillMaxWidth()
            .height(80.dp))
        Text(text = learningMaterial.content)
    }
}