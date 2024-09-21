package com.tl.mineminds.entity

data class QuizData(
    val qId: Int,
    val qTitle: String,
    val qDescription: String,
    val qImageUrl: String,
    val qOptionList: List<OptionData>
)


data class OptionData(val optionId: Int, val optionName: String)
