package com.mruraza.smartstudy.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mruraza.smartstudy.R
import com.mruraza.smartstudy.domain.model.Sessions

// this is the part of the lazy list
fun LazyListScope.StudySessionList(
    sectionTitle: String,
    session: List<Sessions>,
    onSessionDeleteButtonClick: (Sessions)->Unit
) {
    item {
        Text(
            text = sectionTitle,
            modifier = Modifier.padding(12.dp),
            style = MaterialTheme.typography.headlineSmall
        )
    }
    if (session.isEmpty()) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(120.dp).align(Alignment.CenterHorizontally),
                    painter = painterResource(R.drawable.lamp),
                    contentDescription = "please Add sessions"
                )
                Text(
                    text = "You don't have any recent study sessions.\n"+"                 start study now",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    } else {
        items(session) { it->
            StudySessionCard(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 4.dp),
                session = it,
                onSessionDeleteButtonClick = { onSessionDeleteButtonClick(it) }
               )
        }

    }

}