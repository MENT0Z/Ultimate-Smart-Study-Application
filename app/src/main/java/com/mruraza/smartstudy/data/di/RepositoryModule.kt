package com.mruraza.smartstudy.data.di

import com.mruraza.smartstudy.data.repository.SessionRepoImpl
import com.mruraza.smartstudy.data.repository.SubjectRepoImpl
import com.mruraza.smartstudy.data.repository.TaskRepoImpl
import com.mruraza.smartstudy.domain.repository.SessionRepo
import com.mruraza.smartstudy.domain.repository.SubjectRepository
import com.mruraza.smartstudy.domain.repository.TaskRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindSubjectRepository(
        imple : SubjectRepoImpl
    ):SubjectRepository


    @Singleton
    @Binds
    abstract fun bindSessionRepo(
        imple : SessionRepoImpl
    ):SessionRepo

    @Singleton
    @Binds
    abstract fun bindTaskRepo(
        imple: TaskRepoImpl
    ):TaskRepo

}