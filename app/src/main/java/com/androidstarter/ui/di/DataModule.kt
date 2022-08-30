package com.androidstarter.ui.di

import android.content.Context
import com.androidstarter.data.source.remote.AuthRepository
import com.androidstarter.data.source.remote.IAuthRepository
import com.androidstarter.ui.sessions.SessionManager
import com.androidstarter.ui.sessions.SharedPreferenceManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideRemoteRepository(remoteRepository: AuthRepository): IAuthRepository
}