package com.mruraza.smartstudy.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mruraza.smartstudy.domain.model.Subjects

@Composable
fun AddOrUpdateDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    selectedColor: List<Color>,
    onColorChange: (List<Color>) -> Unit,
    subjectName: String,
    goalHours: String,
    onSubjectNameChange: (String) -> Unit,
    onGoalHoursChange: (String) -> Unit,

    ) {

    var subjectNameError by rememberSaveable { mutableStateOf<String?>(null) }
    var goalHoursError by rememberSaveable { mutableStateOf<String?>(null) }

    subjectNameError = when {
        subjectName.isBlank() -> "Subject name can't be empty"
        subjectName.length > 20 -> "Subject name can't be more than 20 characters"
        subjectName.length < 3 -> "Subject name can't be less than 3 characters"
        else -> null
    }

    goalHoursError = when {
        goalHours.isBlank() -> "Goal hours can't be empty"
        goalHours.toFloatOrNull() == null -> "Invalid Number"
        goalHours.toFloat() < 1f -> "Please set at least one hour"
        goalHours.toFloat() > 1000f -> "Goal hours can't be more than 1000 hours"
        else -> null
    }


    if (isOpen) {
        AlertDialog(

            title = {
                Text(text = dialogTitle)
            },
            text = {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Subjects.subjectCardColors.forEach { colors ->
                            Box(modifier = Modifier.size(25.dp).clip(CircleShape).border(
                                width = 1.dp,
                                color = if (colors == selectedColor) Color.Black else Color.Transparent,
                                shape = CircleShape
                            )
                                .background(brush = Brush.verticalGradient(colors))
                                .clickable { onColorChange(colors) }) {
                            }
                        }
                    }

                    OutlinedTextField(
                        value = subjectName,
                        onValueChange = onSubjectNameChange,
                        label = { Text(text = "Subject Name") },
                        singleLine = true,
                        isError = subjectNameError != null && subjectName.isNotBlank(),
                        supportingText = {
                            subjectNameError?.let {
                                Text(text = it)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = goalHours,
                        onValueChange = onGoalHoursChange,
                        label = { Text(text = "Goal Hours") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = goalHoursError != null && goalHours.isNotBlank(),
                        supportingText = {
                            goalHoursError?.let {
                                Text(text = it)
                            }
                        }
                    )
                }
            },
            onDismissRequest = {
                onDismissRequest()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmation()
                    },
                    enabled = subjectNameError == null && goalHoursError == null
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }

}