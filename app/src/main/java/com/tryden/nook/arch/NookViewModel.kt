package com.tryden.nook.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tryden.nook.database.entity.ChecklistItemEntity
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.database.entity.PriorityItemEntity
import com.tryden.nook.ui.epoxy.models.BottomSheetViewType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NookViewModel @Inject constructor(
    private val repository: NookRepository
) : ViewModel() {

    val transactionCompleteLiveData = MutableLiveData<Boolean>()

    // region collect items
    fun collectAllItems() {
        // folders
        viewModelScope.launch {
            repository.getAllFolders().collect() { items ->
                foldersLiveData.postValue(items)
            }
        }
        // priority items
        viewModelScope.launch{
            repository.getAllPriorityItems().collect { items ->
                priorityItemEntitiesLiveData.postValue(items)
            }
        }
        // checklist items
        viewModelScope.launch {
            repository.getAllChecklistItems().collect() { items ->
                checklistItemEntitiesLiveData.postValue(items)
            }
        }
    }

    fun collectAllFolders() {
        viewModelScope.launch {
            repository.getAllFolders().collect() { items ->
                foldersLiveData.postValue(items)
            }
        }
    }
    // endregion collect items


    // region Folders
    val foldersLiveData = MutableLiveData<List<FolderEntity>>()

    fun insertFolder(folderEntity: FolderEntity) {
        viewModelScope.launch {
            repository.insertFolder(folderEntity)

            transactionCompleteLiveData.postValue(true)

        }
    }

    fun deleteFolder(folderEntity: FolderEntity) {
        viewModelScope.launch {
            repository.deleteFolder(folderEntity)
        }
    }

    fun updateFolder(folderEntity: FolderEntity) {
        viewModelScope.launch {
            repository.updateFolder(folderEntity)

            transactionCompleteLiveData.postValue(true)
        }
    }

    // endregion Folders

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


    // region Bottom Sheet
    private val _bottomSheetAddItemTypeLiveData = MutableLiveData<BottomSheetViewType.Type>()
    val bottomSheetAddItemTypeLiveData: LiveData<BottomSheetViewType.Type>
        get() = _bottomSheetAddItemTypeLiveData

    fun updateBottomSheetItemType(type: BottomSheetViewType.Type) {
        _bottomSheetAddItemTypeLiveData.value = type
        _bottomSheetAddItemTypeLiveData.postValue(type)
    }

    // endregion Bottom Sheet

}