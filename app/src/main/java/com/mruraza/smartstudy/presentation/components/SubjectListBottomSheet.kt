package com.mruraza.smartstudy.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mruraza.smartstudy.domain.model.Subjects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectListBottomSheet(
    isOpen: Boolean,
    sheetState: SheetState,
    subjectList: List<Subjects>,
    bottomSheetTitle: String = "Related To Subject",
    onSubjectClick: (Subjects) -> Unit,
    onDismissRequest: () -> Unit
) {
    if (isOpen) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            dragHandle = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetDefaults.DragHandle()
                    Text(text = bottomSheetTitle)
                    spacer(height = 8)
                    Divider()
                }
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(subjectList) { subject ->
                    Box(modifier = Modifier.fillMaxWidth()
                        .clickable { onSubjectClick(subject) }
                        .padding(8.dp)) {
                        Text(text = subject.name)
                    }

                }
                if (subjectList.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(8.dp)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text(text = "No Subject Found\nPlease Add Subjects")
                        }
                    }
                }

            }
        }
    }
}