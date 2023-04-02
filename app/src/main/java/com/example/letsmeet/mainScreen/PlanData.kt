package com.example.letsmeet.mainScreen

data class PlanData(
    val date: String = " ",
    val contents: ContentData
)

data class ContentData(
    val time: String = " ",
    val place: String = " ",
    val plan: String = " "
)
