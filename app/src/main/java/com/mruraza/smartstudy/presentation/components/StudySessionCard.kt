package com.mruraza.smartstudy.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mruraza.smartstudy.domain.model.Sessions

@Composable
fun StudySessionCard(
    modifier: Modifier = Modifier,
    session: Sessions,
    onSessionDeleteButtonClick: ()-> Unit
) {
    ElevatedCard(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth()
            .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier=Modifier.padding(start = 8.dp)) {
                Text(
                    text = session.relatedToSubject,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "${session.date}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier=Modifier.weight(1f))
            Text(text = "${session.duration/3600}hrs", modifier = Modifier.padding(4.dp))
            IconButton(onClick = onSessionDeleteButtonClick){
                Icon(Icons.Default.Delete,contentDescription = null)
            }
        }

    }
}