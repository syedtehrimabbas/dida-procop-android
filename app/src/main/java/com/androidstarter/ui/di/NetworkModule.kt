package com.androidstarter.ui.di

import android.content.Context
import com.androidstarter.data.RetroNetwork
import com.androidstarter.data.source.remote.RepositoryService
import com.androidstarter.ui.sessions.SessionManager
import com.androidstarter.ui.sessions.SharedPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.gilo.woodroid.Woocommerce
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesReposService(woocommerce: Woocommerce): RepositoryService =
        RetroNetwork(woocommerce).createService(RepositoryService::class.java)
    @Singleton
    @Provides
    fun providesSessionManager(
        sharedPreferenceManager: SharedPreferenceManager
    ) = SessionManager(sharedPreferenceManager)

    @Singleton
    @Provides
    fun provideSharedPreferenceManager(@ApplicationContext context: Context) =
        SharedPreferenceManager(context)

    @Provides
    @Singleton
    fun provideWooCommerceApi(): Woocommerce = Woocommerce.Builder()
        .setSiteUrl("https://papiersprocop.com")
        .setApiVersion(Woocommerce.API_V3)
        .setConsumerKey("ck_ff581ce51144479e569332189f44a19c307e14f0")
        .setConsumerSecret("cs_c0908cc3898b9a93bbfb1c5cbe03f25bb3d72cfd")
        .build()
}