package com.bn.taipeizoo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bn.taipeizoo.data.model.TaipeiZooAnimal
import com.bn.taipeizoo.data.model.TaipeiZooArea
import com.bn.taipeizoo.data.repository.TaipeiZooRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaipeiZooViewModel @Inject constructor(private val repository: TaipeiZooRepository) : ViewModel() {
    private val _areas = MutableStateFlow(emptyList<TaipeiZooArea>())
    var areas = _areas.asStateFlow()

    private var animals = emptyList<TaipeiZooAnimal>()

    init {
        getAreas()
        getAnimals()
    }

    private fun getAreas() = viewModelScope.launch {
        repository.getTaipeiZooAreas()?.let {
            _areas.value = it
        }
    }

    private fun getAnimals() = viewModelScope.launch {
        repository.getTaipeiZooAnimals()?.let {
            animals = it
        }
    }

    fun getAreaAnimals(area: String) = animals.filter { it.location == area }
}