package com.mruraza.smartstudy.presentation.dashboard

import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mruraza.smartstudy.domain.model.Sessions
import com.mruraza.smartstudy.domain.model.Subjects
import com.mruraza.smartstudy.domain.model.Task
import com.mruraza.smartstudy.domain.repository.SessionRepo
import com.mruraza.smartstudy.domain.repository.SubjectRepository
import com.mruraza.smartstudy.domain.repository.TaskRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val sessionRepository: SessionRepo,
    private val taskRepo: TaskRepo
):ViewModel() {
    private val _state = MutableStateFlow(DashBoardStates())
    // now combining all the flows into state
    // yo ma chai basically hami lea banako state data class ma value assign vaira ko xa hai
    val state = combine(
        _state,
        subjectRepository.getTotalSubjectCount(),
        subjectRepository.getTotalGoalHours(),
        subjectRepository.getAllSubjects(),
        sessionRepository.getTotalSessionDuration()
    ){
        state,totalSubjectCount,totalGoalHours,subjects,totalSessionDuration->
        state.copy(
            totalSubjectCount = totalSubjectCount,
            totalGoalStudyHour = totalGoalHours,
            subjects = subjects,
            totalStudiedHour = totalSessionDuration.toFloat()

        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashBoardStates()
    )

    val tasks:StateFlow<List<Task>> =  taskRepo.getAllUpcomingTask().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val sessions:StateFlow<List<Sessions>> = sessionRepository.getRecentFiveSessions().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onEvent(event: DashBoardEvents){
     when(event){
         is DashBoardEvents.onDeleteSessionButtonClick -> {
             _state.update {
                 it.copy(sessions = event.sessions)
             }
         }
         is DashBoardEvents.onGoalHourChange -> {
             _state.update {
                 it.copy(goalStudyHour = event.goalHour)
             }
         }
         is DashBoardEvents.onSubjectCardColorChange -> {
             _state.update {
                 it.copy(subjectCardColors = event.colors)
             }
         }
         is DashBoardEvents.onSubjectNameChange -> {
             _state.update {
                 it.copy(subjectName = event.subjectName)
             }
         }
         is DashBoardEvents.onTaskIsCompleteChange -> TODO()
         DashBoardEvents.DeleteSession -> TODO()
         DashBoardEvents.SaveSubject -> saveSubject()
     }
    }

    private fun saveSubject() {
        viewModelScope.launch {
            subjectRepository.upsertSubject(
                subjects = Subjects(
                    name = state.value.subjectName,
                    goalHours = state.value.goalStudyHour.toFloatOrNull() ?:1f,
                    colors = state.value.subjectCardColors.map { it.toArgb() }
                )
            )
            _state.update {
                it.copy(
                    subjectName = "",
                    goalStudyHour = "",
                    subjectCardColors = Subjects.subjectCardColors.random()

                )
            }
        }
    }

    private fun extracted() {
        TODO()
    }

}