package com.tryden.nook.arch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tryden.nook.database.AppDatabase
import com.tryden.nook.database.entity.PriorityItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NookViewModel() : ViewModel() {

    private lateinit var repository: NookRepository

    val priorityItemEntitiesLiveData = MutableLiveData<List<PriorityItemEntity>>()

    val transactionCompleteLiveData = MutableLiveData<Boolean>()

    fun init (appDatabase: AppDatabase) {
        repository = NookRepository(appDatabase)
        viewModelScope.launch{
            repository.getAllPriorityItems().collect { items ->
                priorityItemEntitiesLiveData.postValue(items)
            }
        }
    }

    fun insertPriorityItem(priorityItemEntity: PriorityItemEntity) {
        viewModelScope.launch {
            repository.insertPriorityItem(priorityItemEntity)

            transactionCompleteLiveData.postValue(true)
        }
    }

    fun deleteItem(priorityItemEntity: PriorityItemEntity) {
        viewModelScope.launch {
            repository.deletePriorityItem(priorityItemEntity)
        }
    }

    fun updatePriorityItem(priorityItemEntity: PriorityItemEntity) {
        viewModelScope.launch {
            repository.updatePriorityItem(priorityItemEntity)
        }
    }
}