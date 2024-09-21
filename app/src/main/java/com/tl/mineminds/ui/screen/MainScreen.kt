package com.tl.mineminds.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.tl.mineminds.MainViewModel
import com.tl.mineminds.R

@Composable
fun MainScreen(viewModel: MainViewModel) {
    Box(modifier = Modifier.padding(top = 48.dp)) {
        val mainScreenRoute by viewModel.mainScreenRoute.observeAsState()
        when(mainScreenRoute) {
            ScreenNames.SUBJECTS.routeName -> {
                val subjects by viewModel.subjects.observeAsState()
                Column {
                    Image(painter = rememberImagePainter(R.drawable.profile_dummy2), contentDescription = "", contentScale = ContentScale.Crop, modifier = Modifier
                        .fillMaxWidth()
                        .height(380.dp))
                    SubjectScreen(subjects!!, viewModel)
                }

            }
            ScreenNames.LESSONS.routeName -> {
                LessonScreen(lessonItems = emptyList(), viewModel)
            }
            ScreenNames.LEARNING_MATERIAL.routeName -> {
                LearningMaterialScreen(
                    viewModel.selectedSubjectId.value!!,
                    viewModel.selectedLessonId.value!!,
                    viewModel
                )
            }
            ScreenNames.QUIZ.routeName -> {

            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadSubjects()
    }
}