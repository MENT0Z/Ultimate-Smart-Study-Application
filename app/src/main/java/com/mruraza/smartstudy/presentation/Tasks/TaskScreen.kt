package com.mruraza.smartstudy.presentation.Tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mruraza.smartstudy.Utils.Priority
import com.mruraza.smartstudy.Utils.changeMillisToString
import com.mruraza.smartstudy.presentation.components.ButtonWithText
import com.mruraza.smartstudy.presentation.components.Dialog
import com.mruraza.smartstudy.presentation.components.SubjectListBottomSheet
import com.mruraza.smartstudy.presentation.components.TaskCheckBox
import com.mruraza.smartstudy.presentation.components.TaskDatePicker
import com.mruraza.smartstudy.presentation.components.spacer
import com.mruraza.smartstudy.subjects
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import java.time.Instant

data class TaskScreenNavArgs(
    val taskId:Int?,
    val subjectId:Int?
)

@Destination(navArgsDelegate = TaskScreenNavArgs::class)
@Composable
fun taskScreenRoute(
    navigator: DestinationsNavigator
) {
    val viewModel:TaskViewModel = hiltViewModel()
    taskScreen(
        onBackButtonClick = {navigator.navigateUp()}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun taskScreen(
    onBackButtonClick: () -> Unit
) {

    val scope = rememberCoroutineScope()

    var title by rememberSaveable {
        mutableStateOf("")
    }
    var description by rememberSaveable {
        mutableStateOf("")
    }
    var isOpenDialog by rememberSaveable {
        mutableStateOf(false)
    }

    var isOpenDatePicker by rememberSaveable{
        mutableStateOf(false)
    }
    var datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )
    val sheetState = rememberModalBottomSheetState()
    var sheetOpen by rememberSaveable{
        mutableStateOf(false)
    }
    var taskTitleError by rememberSaveable { mutableStateOf<String?>(null) }
    taskTitleError = when {
        title.isBlank() -> "Please Enter Title"
        title.length < 3 -> "Title too short"
        title.length > 30 -> "Title too large"
        else -> null
    }

    Dialog(
        isOpen = isOpenDialog,
        onDismissRequest = { isOpenDialog = false },
        onConfirmation = { isOpenDialog = false },
        dialogTitle = "Delete Task",
        dialogText = "Are you sure you want to delete this task?"
    )

    TaskDatePicker(
        state = datePickerState,
        isOpen = isOpenDatePicker,
        onDismissRequest = {isOpenDatePicker = false},
        onConfirmation = {isOpenDatePicker=false}
    )

    SubjectListBottomSheet(
        isOpen = sheetOpen,
        sheetState = sheetState,
        subjectList = subjects,
        onSubjectClick = {
            scope.launch {
                sheetState.hide()
            }.invokeOnCompletion { if(!sheetState.isVisible) sheetOpen=false }
        },
        onDismissRequest = {sheetOpen=false}
    )

    Scaffold(
        topBar = {
            topBar(
                modifier = Modifier,
                title = "Task",
                isCompleted = true,
                isTaskExist = true,
                checkBoxBoundaryColor = Color.Green,
                onBackButtonClick = onBackButtonClick,
                onCheckBoxClick = {},
                onDeleteButtonClick = { isOpenDialog = true }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it).verticalScroll(rememberScrollState())) {
            outLinedTextFieldSection(
                modifier = Modifier.padding(horizontal = 12.dp),
                title = title,
                description = description,
                onTitleChange = { title = it },
                onDescriptionChange = { description = it },
                taskTitleError = taskTitleError
            )
            spacer(height = 10)
            Text(text = "Due Date", modifier = Modifier.padding(top = 12.dp, start = 12.dp), fontWeight = FontWeight.Medium)
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = datePickerState.selectedDateMillis.changeMillisToString(),
                    style = MaterialTheme.typography.bodyMedium
                )
                IconButton(onClick = {isOpenDatePicker = true}) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Due Date range"
                    )
                }
            }

            spacer(height = 10)
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = "Priority",
                style = MaterialTheme.typography.bodyMedium , fontWeight = FontWeight.Medium
            )
            spacer(height = 10)
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)) {
                Priority.entries.forEach { priority ->
                    priorityButton(
                        modifier = Modifier.weight(1f),
                        label = priority.title,
                        backgroundColor = priority.color,
                        borderColor = if (priority == Priority.MEDIUM) {
                            Color.White
                        } else {
                            Color.Transparent
                        },
                        labelColor = if (priority == Priority.MEDIUM) {
                            Color.White
                        } else {
                            Color.White.copy(alpha = 0.7f)
                        },
                        onClick = {}
                    )
                }
            }

            spacer(height = 10)
            Text(text = "Related To Subject", modifier = Modifier.padding(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "English",
                    style = MaterialTheme.typography.bodyMedium
                )
                IconButton(onClick = {sheetOpen=true}) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Select Subject"
                    )
                }
            }

            ButtonWithText(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                text = "Save Task",
                enabled = taskTitleError == null,
                onClick = {}
            )



        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun topBar(
    modifier: Modifier = Modifier,
    title: String,
    isCompleted: Boolean,
    isTaskExist: Boolean,
    checkBoxBoundaryColor: Color,
    onBackButtonClick: () -> Unit,
    onCheckBoxClick: () -> Unit,
    onDeleteButtonClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
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
            if (isTaskExist) {
                TaskCheckBox(
                    isCompleted = isCompleted,
                    borderColor = checkBoxBoundaryColor,
                    onCheckBoxClick = onCheckBoxClick
                )
            }
            IconButton(onClick = { onDeleteButtonClick() }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Subject"
                )
            }
        },
    )
}

@Composable
fun outLinedTextFieldSection(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    taskTitleError: String?,
    taskDescriptionError: String? = null
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = onTitleChange,
            label = { Text(text = "Title") },
            singleLine = true,
            isError = taskTitleError != null && title.isNotBlank(),
            supportingText = { Text(text = taskTitleError.orEmpty()) }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text(text = "Description") },
        )
    }


}

@Composable
fun priorityButton(
    modifier: Modifier = Modifier,
    label: String,
    backgroundColor: Color,
    borderColor: Color,
    labelColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.background(backgroundColor).clickable { onClick() }.padding(5.dp)
            .border(1.dp, borderColor, RoundedCornerShape(5.dp)).padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, color = labelColor)
    }
}