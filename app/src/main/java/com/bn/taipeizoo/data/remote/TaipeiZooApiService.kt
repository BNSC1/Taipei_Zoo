package com.bn.taipeizoo.data.remote

import com.bn.taipeizoo.data.response.TaipeiZooAnimalsResponse
import com.bn.taipeizoo.data.response.TaipeiZooAreasResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TaipeiZooApiService {

    @GET("v1/dataset/5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a")
    suspend fun getTaipeiZooAreas(@Query("scope") scope: String): TaipeiZooAreasResponse

    @GET("v1/dataset/a3e2b221-75e0-45c1-8f97-75acbd43d613")
    suspend fun getTaipeiZooAnimals(@Query("scope") scope: String): TaipeiZooAnimalsResponse
}
