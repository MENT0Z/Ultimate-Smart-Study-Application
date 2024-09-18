package com.mruraza.smartstudy.presentation.dashboard

import androidx.compose.ui.graphics.Color
import com.mruraza.smartstudy.domain.model.Sessions
import com.mruraza.smartstudy.domain.model.Subjects

data class DashBoardStates(
    val totalSubjectCount :Int = 0,
    val totalStudiedHour:Float = 0f,
    val totalGoalStudyHour:Float = 0f,
    val subjects :List<Subjects> = emptyList(),
    val subjectName:String = "",
    val goalStudyHour:String = "",
    val subjectCardColors:List<Color> = Subjects.subjectCardColors.random(),
    val sessions: Sessions?=null
)
