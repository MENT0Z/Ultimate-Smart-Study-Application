package com.mruraza.smartstudy.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mruraza.smartstudy.Utils.Priority
import com.mruraza.smartstudy.domain.model.Task

@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    task: Task,
    onCheckBoxClick:()->Unit,
    onClick:()->Unit
) {
    ElevatedCard(modifier = modifier.clickable { onClick()}) {
        Row(modifier = Modifier.fillMaxWidth()
            .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            TaskCheckBox(isCompleted = task.isCompleted,borderColor = Priority.fromInt(task.priority).color,onCheckBoxClick = onCheckBoxClick)
            Spacer(modifier = Modifier.width(10.dp))

            Column() {
                Text(
                    text = task.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    textDecoration = if(task.isCompleted) TextDecoration.LineThrough
                    else TextDecoration.None
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "${task.dueDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

    }
}