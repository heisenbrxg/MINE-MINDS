package com.tl.mineminds.entity

data class LearningMaterial(
    val subjectId: Int,
    val lessonId: Int,
    val title: String,
    val imageResUrl: String,
    val content: String
)