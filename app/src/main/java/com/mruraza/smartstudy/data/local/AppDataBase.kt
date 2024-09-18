package com.mruraza.smartstudy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mruraza.smartstudy.domain.model.Sessions
import com.mruraza.smartstudy.domain.model.Subjects
import com.mruraza.smartstudy.domain.model.Task


@Database(
    entities = [Subjects::class,Sessions::class,Task::class],
    version = 1
)
@TypeConverters(ColorListConverter::class)
abstract class AppDataBase:RoomDatabase() {

    abstract  fun subjectDAO():SubjectDAO
    abstract fun sessionDAO():SessionsDAO
    abstract fun taskDAO():TaskDAO
}