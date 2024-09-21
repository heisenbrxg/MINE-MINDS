package com.tl.mineminds.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.tl.mineminds.MainViewModel
import com.tl.mineminds.ui.component.LearningMaterialPager

@Composable
fun LearningMaterialScreen(subjectId: Int, lessonId: Int, viewModel: MainViewModel) {
    val learningMaterials by viewModel.learningMaterials.observeAsState()
    LearningMaterialPager(learningMaterials!!, viewModel)
    LaunchedEffect(Unit) {
        viewModel.loadLearningMaterials(subjectId, lessonId)
    }
}