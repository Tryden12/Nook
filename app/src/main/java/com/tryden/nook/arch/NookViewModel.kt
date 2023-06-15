package com.tryden.nook.arch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tryden.nook.database.AppDatabase
import com.tryden.nook.database.entity.PriorityItemEntity
import kotlinx.coroutines.launch

class NookViewModel() : ViewModel() {

    private lateinit var repository: NookRepository

    fun init (appDatabase: AppDatabase) {
        repository = NookRepository(appDatabase)
        viewModelScope.launch{
            val priorityItems = repository.getAllPriorityItems()
            priorityItemEntitiesLiveData.postValue(priorityItems)
        }
    }

    val priorityItemEntitiesLiveData = MutableLiveData<List<PriorityItemEntity>>()

    fun insertPriorityItem(priorityItemEntity: PriorityItemEntity) {
        repository.insertPriorityItem(priorityItemEntity)
    }

    fun deleteItem(priorityItemEntity: PriorityItemEntity) {
        repository.deletePriorityItem(priorityItemEntity)
    }

    fun updatePriorityItem(priorityItemEntity: PriorityItemEntity) {
        repository.updatePriorityItem(priorityItemEntity)
    }
}