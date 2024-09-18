package com.mruraza.smartstudy.presentation.Subjects

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mruraza.smartstudy.domain.model.Subjects
import com.mruraza.smartstudy.domain.repository.SessionRepo
import com.mruraza.smartstudy.domain.repository.SubjectRepository
import com.mruraza.smartstudy.domain.repository.TaskRepo
import com.mruraza.smartstudy.presentation.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val sessionRepository: SessionRepo,
    private val taskRepo: TaskRepo,
    savedStateHandle:SavedStateHandle

):ViewModel() {

    private val navArgs : SubjectScreenNavArgs = savedStateHandle.navArgs()
    init {
        fetchSubject()
    }


    private val _state = MutableStateFlow(SubjectState())
    val state = combine(
        _state,
        taskRepo.getUpcomingTaskForSubjects(navArgs.subjectId),
        taskRepo.getCompletedTaskForSubjects(navArgs.subjectId),
        sessionRepository.getRecentTenSessionsForSubject(navArgs.subjectId),
        sessionRepository.getTotalSessionDurationBySubjectId(navArgs.subjectId)
    ){
            state, upcomingTasks, completedTask, recentSessions, totalSessionsDuration->
        state.copy(
            upcomingTasks = upcomingTasks,
            completedTasks = completedTask,
            recentSessions = recentSessions,
            studiedHours = totalSessionsDuration.toFloat()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = SubjectState()
    )



    fun onEvent(event: SubjectEvent){
        when(event){
            is SubjectEvent.OnSubjectCardColorChange -> {
                _state.update {
                    it.copy(subjectCardColors = event.color)
                }
            }
            is SubjectEvent.OnSubjectNameChange -> {
                _state.update {
                    it.copy(subjectName = event.name)
                }
            }
            is SubjectEvent.OnGoalStudyHoursChange -> {
                _state.update {
                    it.copy(goalStudyHours = event.hours)
                }
            }

            SubjectEvent.DeleteSubject -> deleteSubject()
            SubjectEvent.UpdateSubject -> updateSubject()

            SubjectEvent.UpdateProgress -> {
                val goalStudyHours = state.value.goalStudyHours.toFloatOrNull()?:1f
                _state.update {
                    it.copy(
                        progress = (state.value.studiedHours / goalStudyHours).coerceIn(0f, 1f)
                    )
                }
            }
            SubjectEvent.DeleteSession -> TODO()
            is SubjectEvent.OnDeleteSessionButtonClick -> TODO()
            is SubjectEvent.OnTaskIsCompleteChange -> TODO()
        }
    }

    private fun fetchSubject(){
        viewModelScope.launch {
            subjectRepository.getSubjectById(navArgs.subjectId)?.let {subject ->
                _state.update {
                    it.copy(
                        subjectName = subject.name,
                        goalStudyHours = subject.goalHours.toString(),
                        subjectCardColors = subject.colors.map { Color(it) },
                        currentSubjectId = subject.subjectId
                    )
                }
            }
        }
    }

    private fun updateSubject(){
        viewModelScope.launch {
            subjectRepository.upsertSubject(
                Subjects(
                    subjectId = state.value.currentSubjectId,
                    name = state.value.subjectName,
                    goalHours = state.value.goalStudyHours.toFloatOrNull() ?: 1f,
                    colors = state.value.subjectCardColors.map { it.toArgb()}
                )
            )
        }
    }

    private fun deleteSubject(){
        viewModelScope.launch {
            val currentSubId = state.value.currentSubjectId
            if(currentSubId!=null){
                withContext(Dispatchers.IO){
                    subjectRepository.deleteSubject(currentSubId)
                }
            }
        }
    }

}