package com.mruraza.smartstudy.data.di

import android.app.Application
import androidx.room.Room
import com.mruraza.smartstudy.data.local.AppDataBase
import com.mruraza.smartstudy.data.local.SessionsDAO
import com.mruraza.smartstudy.data.local.SubjectDAO
import com.mruraza.smartstudy.data.local.TaskDAO
import com.mruraza.smartstudy.domain.model.Task
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ):AppDataBase{
        return Room.databaseBuilder(
            application,
            AppDataBase::class.java,
            "studysmart.db"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideSubjectDao(dataBase: AppDataBase):SubjectDAO{
        return dataBase.subjectDAO()
    }

    @Provides
    @Singleton
    fun provideSessionDao(dataBase: AppDataBase):SessionsDAO{
        return dataBase.sessionDAO()
    }

    @Provides
    @Singleton
    fun provideTaskDao(dataBase: AppDataBase):TaskDAO{
        return dataBase.taskDAO()
    }


}