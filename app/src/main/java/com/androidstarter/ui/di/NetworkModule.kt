package com.androidstarter.ui.di

import com.androidstarter.data.RetroNetwork
import com.androidstarter.data.source.remote.RepositoryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesReposService(): RepositoryService =
        RetroNetwork().createService(RepositoryService::class.java)
}