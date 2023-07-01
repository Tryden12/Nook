package com.tryden.nook.arch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tryden.nook.database.AppDatabase
import com.tryden.nook.database.entity.ChecklistItemEntity
import com.tryden.nook.database.entity.PriorityItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NookViewModel() : ViewModel() {

    private lateinit var repository: NookRepository

    val transactionCompleteLiveData = MutableLiveData<Boolean>()

    fun init (appDatabase: AppDatabase) {
        repository = NookRepository(appDatabase)
        viewModelScope.launch{
            repository.getAllPriorityItems().collect { items ->
                priorityItemEntitiesLiveData.postValue(items)
            }
        }

        viewModelScope.launch {
            repository.getAllChecklistItems().collect() { items ->
                checklistItemEntitiesLiveData.postValue(items)
            }
        }
    }

    // region Priority Items
    val priorityItemEntitiesLiveData = MutableLiveData<List<PriorityItemEntity>>()

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

            transactionCompleteLiveData.postValue(true)
        }
    }
    // endregion Priority Items

    // region Checklist Items
    val checklistItemEntitiesLiveData = MutableLiveData<List<ChecklistItemEntity>>()

    fun insertChecklistItem(checklistItemEntity: ChecklistItemEntity) {
        viewModelScope.launch {
            repository.insertChecklistItem(checklistItemEntity)

            transactionCompleteLiveData.postValue(true)
        }
    }

    fun deleteChecklistItem(checklistItemEntity: ChecklistItemEntity) {
        viewModelScope.launch {
            repository.deleteChecklistItem(checklistItemEntity)
        }
    }

    fun updateChecklistItem(checklistItemEntity: ChecklistItemEntity) {
        viewModelScope.launch {
            repository.updateChecklistItem(checklistItemEntity)

            transactionCompleteLiveData.postValue(true)
        }
    }

    // endregion Checklist Items



}