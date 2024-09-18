package com.mruraza.smartstudy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import com.mruraza.smartstudy.domain.model.Sessions
import com.mruraza.smartstudy.domain.model.Subjects
import com.mruraza.smartstudy.domain.model.Task
import com.mruraza.smartstudy.presentation.NavGraphs
import com.mruraza.smartstudy.ui.theme.SmartStudyTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartStudyTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}

// this is dummy list for our subjects
val subjects = listOf(
    Subjects(name = "English", goalHours = 200f, colors = Subjects.subjectCardColors[0].map { it.toArgb() }, subjectId = 1),
    Subjects(name = "Nepali", goalHours = 20f, colors = Subjects.subjectCardColors[1].map { it.toArgb() }, subjectId = 2),
    Subjects(name = "Social", goalHours = 100f, colors = Subjects.subjectCardColors[2].map { it.toArgb() },subjectId = 3),
    Subjects(name = "EPH", goalHours = 10f, colors = Subjects.subjectCardColors[3].map { it.toArgb() },subjectId = 4),
    Subjects(name = "Health", goalHours = 30f, colors = Subjects.subjectCardColors[4].map { it.toArgb() },subjectId = 5),
    Subjects(name = "Maths", goalHours = 50f, colors = Subjects.subjectCardColors[0].map { it.toArgb() },subjectId = 6),
    Subjects(name = "Science", goalHours = 400f, colors = Subjects.subjectCardColors[1].map { it.toArgb() },subjectId = 7),
)

val tasks = listOf (
    Task(
        title = "Study App",
        description = "Finish Study App",
        dueDate = 0L,
        priority = 0,
        relatedToSubject = "English",
        isCompleted = false,
        taskSubjectId = 1,
        taskID = 1
    ),
    Task(
        title = "POE ",
        description = " Study POE",
        dueDate = 0L,
        priority = 2,
        relatedToSubject = "Engineering",
        isCompleted = false,
        taskSubjectId = 1,
        taskID = 1
    ),
    Task(
        title = "Physics HW",
        description = "Complete homework of physics",
        dueDate = 0L,
        priority = 1,
        relatedToSubject = "Physics",
        isCompleted = true,
        taskSubjectId = 1,
        taskID = 1
    ),
    Task(
        title = "Maths Assignment",
        description = "Assignment of Maths",
        dueDate = 0L,
        priority = 3,
        relatedToSubject = "Maths",
        isCompleted = true,
        taskSubjectId = 1,
        taskID = 1
    ),
    Task(
        title = "play football",
        description = "play football with friends",
        dueDate = 0L,
        priority = 1,
        relatedToSubject = "Additional",
        isCompleted = false,
        taskSubjectId = 1,
        taskID = 1
    ),
)

val sessions = listOf(
    Sessions(1, "Math", 1678886400000,36000, 1),
    Sessions(1, "Physics", 1678972800000, 18000, 2),
    Sessions(2, "Biology", 1679059200000, 72000, 3),
    Sessions(2, "Chemistry", 1679145600000, 54000, 4),
    Sessions(3, "History", 1679232000000, 90000, 5)
)

