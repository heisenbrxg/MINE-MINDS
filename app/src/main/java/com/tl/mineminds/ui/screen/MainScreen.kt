package com.tl.mineminds.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tl.mineminds.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel) {
    Box(modifier = Modifier.padding(top = 48.dp)) {
        val mainScreenRoute by viewModel.mainScreenRoute.observeAsState()
        when(mainScreenRoute) {
            ScreenNames.SUBJECTS.routeName -> {
                val subjects by viewModel.subjects.observeAsState()
                SubjectScreen(subjects!!, viewModel)
            }
            ScreenNames.LESSONS.routeName -> {

            }
            ScreenNames.LEARNING_MATERIAL.routeName -> {
                LearningMaterialScreen(viewModel)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadSubjects()
    }
}