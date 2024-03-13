package com.sd.passwordmanager.db

import android.content.Context
import androidx.room.Room
import com.sd.passwordmanager.dao.ItemDao
import com.sd.passwordmanager.dao.MasterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DbModule {
    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): AppDb {
        return Room.databaseBuilder(context, AppDb::class.java, "app.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideItemDao(
        appDb: AppDb
    ): ItemDao = appDb.itemDao()

    @Provides
    @Singleton
    fun provideMasterDao(
        appDb: AppDb
    ): MasterDao = appDb.masterDao()

}