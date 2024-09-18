package com.mruraza.smartstudy.presentation.Subjects

import androidx.compose.ui.graphics.Color
import com.mruraza.smartstudy.domain.model.Sessions
import com.mruraza.smartstudy.domain.model.Subjects
import com.mruraza.smartstudy.domain.model.Task

data class SubjectState(
    val currentSubjectId: Int? = null,
    val subjectName: String = "",
    val goalStudyHours: String = "",
    val subjectCardColors: List<Color> = Subjects.subjectCardColors.random(),
    val studiedHours: Float = 0f,
    val progress: Float = 0f,
    val recentSessions: List<Sessions> = emptyList(),
    val upcomingTasks: List<Task> = emptyList(),
    val completedTasks: List<Task> = emptyList(),
    val session: Sessions? = null,
    val isLoading:Boolean = false
)
