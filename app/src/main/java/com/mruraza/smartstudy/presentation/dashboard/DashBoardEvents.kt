package com.mruraza.smartstudy.presentation.dashboard

import androidx.compose.ui.graphics.Color
import com.mruraza.smartstudy.domain.model.Sessions
import com.mruraza.smartstudy.domain.model.Task

sealed class DashBoardEvents {
    data object DeleteSession:DashBoardEvents()
    data object SaveSubject:DashBoardEvents()
    data class onDeleteSessionButtonClick(val sessions: Sessions):DashBoardEvents()
    data class onTaskIsCompleteChange(val task: Task):DashBoardEvents()
    data class onSubjectCardColorChange(val colors:List<Color>):DashBoardEvents()
    data class onSubjectNameChange(val subjectName:String):DashBoardEvents()
    data class onGoalHourChange(val goalHour:String):DashBoardEvents()


}