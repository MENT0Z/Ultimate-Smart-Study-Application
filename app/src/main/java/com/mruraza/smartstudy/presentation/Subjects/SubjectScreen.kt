package com.mruraza.smartstudy.presentation.Subjects

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mruraza.smartstudy.domain.model.Subjects
import com.mruraza.smartstudy.presentation.Tasks.TaskScreenNavArgs
import com.mruraza.smartstudy.presentation.components.AddOrUpdateDialog
import com.mruraza.smartstudy.presentation.components.CountingCard
import com.mruraza.smartstudy.presentation.components.Dialog
import com.mruraza.smartstudy.presentation.components.StudySessionList
import com.mruraza.smartstudy.presentation.components.spacer
import com.mruraza.smartstudy.presentation.components.taskList
import com.mruraza.smartstudy.presentation.destinations.taskScreenRouteDestination
import com.mruraza.smartstudy.sessions
import com.mruraza.smartstudy.tasks
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

// for all the arguments we need to take here we have to make one dataclass and pass it here
data class SubjectScreenNavArgs(
    val subjectId: Int
)

@Destination(navArgsDelegate = SubjectScreenNavArgs::class)
@Composable
fun subjectScreenRoute(
    navigator: DestinationsNavigator
) {
    val viewModel: SubjectViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    SubjectScreen(
        state = state,
        event = viewModel::onEvent,
        onTaskCardClick = { taskId ->
            val navArgs = TaskScreenNavArgs(taskId = taskId, subjectId = null)
            navigator.navigate(taskScreenRouteDestination(navArgs))
        },
        onBackButtonClick = { navigator.navigateUp() },
        onAddTaskButtonClick = {
            val navArgs = TaskScreenNavArgs(taskId = null, subjectId = null)
            navigator.navigate(taskScreenRouteDestination(navArgs))
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubjectScreen(
    state: SubjectState,
    event: (SubjectEvent) -> Unit,
    onBackButtonClick: () -> Unit,
    onAddTaskButtonClick: () -> Unit,
    onTaskCardClick: (Int?) -> Unit,
) {
    val listState = rememberLazyListState()
    val isExpanded by remember {
        derivedStateOf { listState.firstVisibleItemIndex == 0 }
    }
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    var isDeleteButtonCLick by rememberSaveable { mutableStateOf(false) }

    var isDeleteSubjectButtonCLick by rememberSaveable { mutableStateOf(false) }

    var isAddSubjectDialogOpen by rememberSaveable {
        mutableStateOf(false)
    }

//    var subjectName by rememberSaveable {
//        mutableStateOf("")
//
//    }
//
//    var goalHour by rememberSaveable {
//        mutableStateOf("")
//    }
//
//    var selectedColors by rememberSaveable {
//        mutableStateOf(Subjects.subjectCardColors.random())
//    }


    Dialog(
        isOpen = isDeleteButtonCLick,
        onDismissRequest = { isDeleteButtonCLick = false },
        onConfirmation = {
            event(SubjectEvent.DeleteSession)
            isDeleteButtonCLick = false
        },
        dialogTitle = "Delete Session",
        dialogText = "Are you sure you want to delete this session?"
    )

    Dialog(
        isOpen = isDeleteSubjectButtonCLick,
        onDismissRequest = { isDeleteSubjectButtonCLick = false },
        onConfirmation = {
            event(SubjectEvent.DeleteSubject)
            isDeleteSubjectButtonCLick = false
            onBackButtonClick()
        },
        dialogTitle = "Delete Subject",
        dialogText = "Are you sure you want to delete this Subject?\nAll of your progress will be deleted and it can't be undone"
    )


    AddOrUpdateDialog(
        isOpen = isAddSubjectDialogOpen,
        onDismissRequest = { isAddSubjectDialogOpen = false },
        onConfirmation = {
            event(SubjectEvent.UpdateSubject)
            isAddSubjectDialogOpen = false
        },
        dialogTitle = "Add/Update Subject",
        subjectName = state.subjectName,
        onSubjectNameChange = { event(SubjectEvent.OnSubjectNameChange(it)) },
        goalHours = state.goalStudyHours,
        onGoalHoursChange = { event(SubjectEvent.OnGoalStudyHoursChange(it)) },
        selectedColor = state.subjectCardColors,
        onColorChange = { event(SubjectEvent.OnSubjectCardColorChange(it)) }
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SubjectScreenTopBar(
                title = state.subjectName,
                onBackButtonClick = onBackButtonClick,
                onDeleteButtonClick = { isDeleteSubjectButtonCLick = true },
                onEditButtonClick = { isAddSubjectDialogOpen = true },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onAddTaskButtonClick() },
                icon = { Icon(imageVector = Icons.Default.Add, contentDescription = "Add") },
                text = { Text(text = "Add task") },
                expanded = isExpanded
            )
        }
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.padding(it).fillMaxSize()
        ) {
            item {
                countCardSection(
                    modifier = Modifier.padding(12.dp),
                    studiedHour = state.studiedHours.toString(),
                    goalHour = state.goalStudyHours,
                    progress = state.progress
                )
            }

            taskList(
                sectionTitle = "UPCOMING TASKS",
                task = state.upcomingTasks,
                onCheckBoxClick = {
                    event(SubjectEvent.OnTaskIsCompleteChange(it))
                },
                onTaskCardClick = onTaskCardClick
            )

            taskList(
                sectionTitle = "COMPLETED TASKS",
                task = state.completedTasks,
                onCheckBoxClick = {
                    SubjectEvent.OnTaskIsCompleteChange(it)
                },
                onTaskCardClick = onTaskCardClick
            )

            StudySessionList(
                sectionTitle = "RECENT STUDY SESSION",
                session = state.recentSessions,
                onSessionDeleteButtonClick = {
                    event(SubjectEvent.OnDeleteSessionButtonClick(it))
                    isDeleteButtonCLick = true
                }
            )

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectScreenTopBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    title: String,
    onBackButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit
) {


    LargeTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackButtonClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Arrow"
                )
            }
        },
        actions = {
            IconButton(onClick = { onDeleteButtonClick() }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Subject"
                )
            }
            IconButton(onClick = { onEditButtonClick() }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Subject"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )

}

@Composable
fun countCardSection(
    modifier: Modifier = Modifier,
    studiedHour: String,
    goalHour: String,
    progress: Float
) {

    val progressInPrecentage = remember(progress) {
        (progress * 100).toInt().coerceIn(0, 100)
    }

    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CountingCard(
            modifier = Modifier.weight(1f),
            count = goalHour,
            headingText = "Goal Study Hours"
        )
        spacer(width = 8)
        CountingCard(
            modifier = Modifier.weight(1f),
            count = studiedHour,
            headingText = "Studied Hours"
        )
        spacer(width = 8)
        Box(
            modifier = Modifier.size(75.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = 1f,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = progress,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
            )
            Text(text = "$progressInPrecentage%")
        }
    }
}

