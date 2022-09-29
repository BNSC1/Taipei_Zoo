package com.bn.taipeizoo.di

import com.bn.taipeizoo.data.repository.TaipeiZooRepository
import com.bn.taipeizoo.data.repository.TaipeiZooRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideTaipeiZooRepository(repository: TaipeiZooRepositoryImpl): TaipeiZooRepository = repository
}