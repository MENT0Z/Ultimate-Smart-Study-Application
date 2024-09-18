package com.mruraza.smartstudy.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mruraza.smartstudy.R
import com.mruraza.smartstudy.domain.model.Sessions
import com.mruraza.smartstudy.domain.model.Subjects
import com.mruraza.smartstudy.domain.model.Task
import com.mruraza.smartstudy.presentation.Subjects.SubjectScreenNavArgs
import com.mruraza.smartstudy.presentation.Tasks.TaskScreenNavArgs
import com.mruraza.smartstudy.presentation.components.AddOrUpdateDialog
import com.mruraza.smartstudy.presentation.components.ButtonWithText
import com.mruraza.smartstudy.presentation.components.CountingCard
import com.mruraza.smartstudy.presentation.components.Dialog
import com.mruraza.smartstudy.presentation.components.StudySessionList
import com.mruraza.smartstudy.presentation.components.SubjectCard
import com.mruraza.smartstudy.presentation.components.taskList
import com.mruraza.smartstudy.presentation.destinations.SessionScreenRouteDestination
import com.mruraza.smartstudy.presentation.destinations.subjectScreenRouteDestination
import com.mruraza.smartstudy.presentation.destinations.taskScreenRouteDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun DashBoardRoute(
    navigator: DestinationsNavigator
) {
    // obj of the dashboardViewmodel
    val dashBoardViewModel: DashBoardViewModel = hiltViewModel()
    val state by dashBoardViewModel.state.collectAsStateWithLifecycle()
    val task by dashBoardViewModel.tasks.collectAsStateWithLifecycle()
    val recentSessions by dashBoardViewModel.sessions.collectAsStateWithLifecycle()
    DashBoardScreen(
        states = state,
        task = task,
        recentSession=recentSessions,
        onEvents = dashBoardViewModel::onEvent,
        onTaskCardClick = { taskId ->
            val navArgs = TaskScreenNavArgs(taskId = taskId, subjectId = null)
            navigator.navigate(taskScreenRouteDestination(navArgs))
        },
        onSubjectCardClick = { subjectId ->
            subjectId?.let {
                val navArgs = SubjectScreenNavArgs(subjectId)
                navigator.navigate(subjectScreenRouteDestination(navArgs))
            }
        },
        onStartSessionButtonClick = {
            navigator.navigate(SessionScreenRouteDestination())
        }
    )

}

@Composable
private fun DashBoardScreen(
    states: DashBoardStates,
    task:List<Task>,
    recentSession:List<Sessions>,
    onEvents: (DashBoardEvents) -> Unit,
    onTaskCardClick: (Int?) -> Unit,
    onSubjectCardClick: (Int?) -> Unit,
    onStartSessionButtonClick: () -> Unit
) {
    var isDeleteButtonCLick by rememberSaveable { mutableStateOf(false) }
    var isAddSubjectDialogOpen by rememberSaveable {
        mutableStateOf(false)
    }
//    var subjectName by rememberSaveable{
//        mutableStateOf("")
//
//    }
//    var goalHour by rememberSaveable{
//        mutableStateOf("")
//    }
//    var selectedColors by rememberSaveable{
//        mutableStateOf(Subjects.subjectCardColors.random())
//    }


    Dialog(
        isOpen = isDeleteButtonCLick,
        onDismissRequest = { isDeleteButtonCLick = false },
        onConfirmation = { isDeleteButtonCLick = false },
        dialogTitle = "Delete Session",
        dialogText = "Are you sure you want to delete this session?"
    )


    AddOrUpdateDialog(
        isOpen = isAddSubjectDialogOpen,
        onDismissRequest = {
            isAddSubjectDialogOpen = false
            onEvents(DashBoardEvents.DeleteSession)
        },
        onConfirmation = {
            onEvents(DashBoardEvents.SaveSubject)
            isAddSubjectDialogOpen = false
        },
        dialogTitle = "Add/Update Subject",
        subjectName = states.subjectName,
        onSubjectNameChange = { onEvents(DashBoardEvents.onSubjectNameChange(it)) },
        goalHours = states.goalStudyHour,
        onGoalHoursChange = { onEvents(DashBoardEvents.onGoalHourChange(it)) },
        selectedColor = states.subjectCardColors,
        onColorChange = { onEvents(DashBoardEvents.onSubjectCardColorChange(it)) }
    )

    Scaffold(topBar = { TopBar(modifier = Modifier) }) {
        LazyColumn(modifier = Modifier.padding(it).fillMaxSize()) {
            item {
                CountCardArea(
                    modifier = Modifier,
                    subjectCount = states.totalSubjectCount.toString(),
                    studiedHour = states.totalStudiedHour.toString(),
                    goalHour = states.totalGoalStudyHour.toString()
                )
            }

            item {
                SubjectCardSection(
                    subjectList = states.subjects,
                    onAddButtonClick = { isAddSubjectDialogOpen = true },
                    onSubjectCardClick = onSubjectCardClick
                )
            }

            item {
                ButtonWithText(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 18.dp),
                    text = "Start Study Session",
                    onClick = onStartSessionButtonClick
                )
            }

            taskList(
                sectionTitle = "UPCOMING TASKS",
                task = task,
                onCheckBoxClick = {onEvents(DashBoardEvents.onTaskIsCompleteChange(it))},
                onTaskCardClick = onTaskCardClick
            )

            StudySessionList(
                sectionTitle = "RECENT STUDY SESSION",
                session = recentSession,
                onSessionDeleteButtonClick = {
                    onEvents(DashBoardEvents.onDeleteSessionButtonClick(it))
                    isDeleteButtonCLick = true }
            )


        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = "Study Smart",
            style = MaterialTheme.typography.headlineLarge
        )
    })

}


@Composable
fun CountCardArea(
    modifier: Modifier,
    subjectCount: String,
    studiedHour: String,
    goalHour: String
) {
    Row(modifier = modifier.fillMaxSize().padding(horizontal = 12.dp)) {
        CountingCard(
            modifier = Modifier.weight(1f),
            count = subjectCount,
            headingText = "Subject Counts"
        )
        Spacer(modifier = Modifier.width(12.dp))

        CountingCard(
            modifier = Modifier.weight(1f),
            count = studiedHour,
            headingText = "Studied Hour"
        )

        Spacer(modifier = Modifier.width(12.dp))

        CountingCard(
            modifier = Modifier.weight(1f),
            count = goalHour,
            headingText = "Goal Hour"
        )


    }
}


@Composable
fun SubjectCardSection(
    modifier: Modifier = Modifier, subjectList: List<Subjects>,
    onAddButtonClick: () -> Unit,
    onSubjectCardClick: (Int?) -> Unit
) {
    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "SUBJECTS",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
            )
            IconButton(
                onClick = { onAddButtonClick() },
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Subjects Add Button"
                )
            }

        }
        if (subjectList.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
            ) {
                items(subjectList) { subject ->
                    SubjectCard(
                        subjectName = subject.name,
                        gradiendColor = subject.colors.map { Color(it) },
                        onClick = { onSubjectCardClick(subject.subjectId) }
                    )
                }

            }
        } else {
            Image(
                modifier = Modifier.size(120.dp).align(Alignment.CenterHorizontally),
                painter = painterResource(R.drawable.books),
                contentDescription = "please Add Books"
            )
            Text(
                text = "You don't have any subjects\nClick + button to add Subjects",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }

}
