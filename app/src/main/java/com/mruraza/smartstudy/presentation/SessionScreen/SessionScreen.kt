package com.mruraza.smartstudy.presentation.SessionScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mruraza.smartstudy.presentation.components.ButtonWithText
import com.mruraza.smartstudy.presentation.components.Dialog
import com.mruraza.smartstudy.presentation.components.StudySessionList
import com.mruraza.smartstudy.presentation.components.SubjectListBottomSheet
import com.mruraza.smartstudy.sessions
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch


@Destination()
@Composable
fun SessionScreenRoute(
    navigator: DestinationsNavigator
){

    val viewModel:SessionViewModel = hiltViewModel()

    SessionScreen(
        onBackButtonClick = {navigator.navigateUp()}
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SessionScreen(
    onBackButtonClick: () -> Unit
) {

    val scope = rememberCoroutineScope()

    var isDeleteDialogOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var relatedSubjetBottomSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()
    Dialog(
        isOpen = isDeleteDialogOpen,
        onDismissRequest = { isDeleteDialogOpen = false },
        onConfirmation = { isDeleteDialogOpen = false },
        dialogTitle = "Delete Session",
        dialogText = "Are you sure you want to delete this session?"
    )
    SubjectListBottomSheet(
        isOpen = relatedSubjetBottomSheetOpen,
        sheetState = sheetState,
        subjectList = listOf(),
        onSubjectClick = { scope.launch {
            sheetState.hide()
        }.invokeOnCompletion { if(!sheetState.isVisible) relatedSubjetBottomSheetOpen=false } },
        onDismissRequest = { relatedSubjetBottomSheetOpen = false }
    )

    Scaffold(
        topBar = {
            topBarOfSessionScreen(
                title = "Study Session",
                onBackButtonClick = {onBackButtonClick()}
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(paddingValues)) {
            item {
                timerStudySession()
            }
            item {
                relatedSubSection(
                    isBottomSheetOpen = { relatedSubjetBottomSheetOpen = it }
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    ButtonWithText(text = "Start", onClick = {})
                    ButtonWithText(text = "Cancel", onClick = {})
                    ButtonWithText(text = "Finish", onClick = {})
                }
            }
            StudySessionList(
                sectionTitle = "Study Session History",
                session = sessions,
                onSessionDeleteButtonClick = { isDeleteDialogOpen = true }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBarOfSessionScreen(
    modifier: Modifier = Modifier,
    title: String,
    onBackButtonClick: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { onBackButtonClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Arrow Back"
                )
            }
        }
    )
}

@Composable
fun timerStudySession(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth().aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(250.dp)
                .clip(CircleShape)
                .border(width = 4.dp, shape = CircleShape, color = Color.Gray)
        )
        Text(text = "00:00:00", style = MaterialTheme.typography.headlineLarge)

    }
}

@Composable
fun relatedSubSection(
    isBottomSheetOpen:(Boolean)->Unit
) {
    Text(
        text = "Related To Subject",
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
        style = MaterialTheme.typography.headlineSmall,
    )
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "English",
            style = MaterialTheme.typography.bodyLarge,
        )
        IconButton(onClick = {isBottomSheetOpen(true)}) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Select Subject"
            )
        }
    }
}