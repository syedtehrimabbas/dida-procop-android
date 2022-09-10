package com.androidstarter.ui.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.androidstarter.data.RetroNetwork
import com.androidstarter.data.cart.ProcopDatabase
import com.androidstarter.data.cart.dao.CartProductDao
import com.androidstarter.data.sessions.SessionManager
import com.androidstarter.data.sessions.SharedPreferenceManager
import com.androidstarter.data.source.remote.RepositoryService
import com.androidstarter.ui.home.DatabaseHelper
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
        .setSiteUrl("https://papiersprocop.com/")
        .setApiVersion(Woocommerce.API_V3)
        .setConsumerKey("ck_c70e62bd7b8f6e2dce406a127f9ab6dd11d98d45")
        .setConsumerSecret("cs_6bb3e7ca80db9d9a87514dd37fdee88dbbd22034")
        .build()

    @Provides
    fun provideCartProductDao(database: ProcopDatabase) = database.getProductDao()

    @Provides
    fun provideDatabaseHelper(cartProductDao: CartProductDao): DatabaseHelper {
        return DatabaseHelper(cartProductDao)
    }

    @Provides
    @Singleton
    internal fun providesAppDatabase(@ApplicationContext context: Context): ProcopDatabase {
        return Room.databaseBuilder(context, ProcopDatabase::class.java, ProcopDatabase.DB_NAME)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
//                    debug("DataBasePath>>" + db.path)
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
//                    debug("DataBasePath>>" + db.path)
                }
            })
            .fallbackToDestructiveMigration().build()
    }
}