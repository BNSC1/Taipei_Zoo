package com.bn.taipeizoo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bn.taipeizoo.data.model.TaipeiZooAnimal
import com.bn.taipeizoo.data.model.TaipeiZooArea
import com.bn.taipeizoo.data.repository.TaipeiZooRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaipeiZooViewModel @Inject constructor(private val repository: TaipeiZooRepository) : ViewModel() {
    private val _areas = MutableStateFlow(emptyList<TaipeiZooArea>())
    val areas = _areas.asStateFlow()

    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg = _errorMsg.asStateFlow()

    private var animals = emptyList<TaipeiZooAnimal>()

    init {
        getAreas()
        getAnimals()
    }

    private fun getAreas() = viewModelScope.launch {
        tryRun {
            repository.getTaipeiZooAreas()?.let {
                _areas.value = it
            }
        }
    }

    private fun getAnimals() = viewModelScope.launch {
        tryRun {
            repository.getTaipeiZooAnimals()?.let {
                animals = it
            }
        }
    }

    fun getAreaAnimals(area: String) = animals.filter {
        area.run {
            if (endsWith("）")) {
                replace("）", "")
                    .substringAfter('（')
            } else this
        } == it.location
    }

    private inline fun CoroutineScope.tryRun(action: () -> Unit) {
        runCatching {
            action()
        }.onFailure {
            it.message?.let { msg ->
                _errorMsg.value = msg
            }
        }
    }
}