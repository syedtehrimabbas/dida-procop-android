package com.androidstarter.ui.di

import com.androidstarter.data.source.remote.IRepository
import com.androidstarter.data.source.remote.SearchCitiesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideRemoteRepository(remoteRepository: SearchCitiesRepository): IRepository
}