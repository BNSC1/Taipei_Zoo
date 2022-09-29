package com.bn.taipeizoo.data.repository

import com.bn.taipeizoo.data.model.TaipeiZooAnimal
import com.bn.taipeizoo.data.model.TaipeiZooArea

interface TaipeiZooRepository {
    suspend fun getTaipeiZooAreas(): List<TaipeiZooArea>?
    suspend fun getTaipeiZooAnimals(): List<TaipeiZooAnimal>?
}