package com.bn.taipeizoo.data.repository

import com.bn.taipeizoo.data.remote.TaipeiZooApiService
import javax.inject.Inject

class TaipeiZooRepositoryImpl @Inject constructor(private val apiService: TaipeiZooApiService) : TaipeiZooRepository {
    override suspend fun getTaipeiZooAreas() = apiService.getTaipeiZooAreas().body()?.result?.areas
    override suspend fun getTaipeiZooAnimals() = apiService.getTaipeiZooAnimals().body()?.result?.animals
}