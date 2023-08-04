package com.tryden.nook.arch

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tryden.nook.database.entity.ChecklistItemEntity
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.database.entity.NoteEntity
import com.tryden.nook.database.entity.PriorityItemEntity
import com.tryden.nook.ui.epoxy.models.BottomSheetViewType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NookViewModel @Inject constructor(
    private val repository: NookRepository
) : ViewModel() {

    val transactionCompleteLiveData = MutableLiveData<Boolean>()
    val editMode = MutableLiveData<Boolean>()

    fun updateEditMode(isEditMode: Boolean) {
        editMode.value = isEditMode
        editMode.postValue(isEditMode)
    }

    // region Folders
    val foldersLiveData = MutableLiveData<List<FolderEntity>>()

    private val _currentSelectedFolderLiveData = MutableLiveData<FolderEntity>()
    val currentSelectedFolderLiveData: LiveData<FolderEntity>
        get() = _currentSelectedFolderLiveData

    fun collectAllFolders() {
        viewModelScope.launch {
            repository.getAllFolders().collect() { allFolders ->
                foldersLiveData.postValue(allFolders).let {
                    Log.d("ViewModel", "collectAllItems: getAllFolders" )
                }
            }
        }
    }

    fun updateCurrentFolderSelected(currentFolder: FolderEntity) {
        _currentSelectedFolderLiveData.value = currentFolder
        _currentSelectedFolderLiveData.postValue(currentFolder)
    }

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

    private val _currentSelectedPriorityItemLiveData = MutableLiveData<PriorityItemEntity>()
    val currentSelectedPriorityItemLiveData: LiveData<PriorityItemEntity>
        get() = _currentSelectedPriorityItemLiveData

    fun updateCurrentPriorityItemSelected(currentPriority: PriorityItemEntity) {
        _currentSelectedPriorityItemLiveData.value = currentPriority
        _currentSelectedPriorityItemLiveData.postValue(currentPriority)
    }

    fun collectAllPriorityEntities() {
        viewModelScope.launch {
            repository.getAllPriorityItems().collect() { list ->
                priorityItemEntitiesLiveData.postValue(list)
            }
        }
        Log.d("ViewModel", "collectAllItems: getAllPriorityItems" )
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

            transactionCompleteLiveData.postValue(true)
        }
    }
    // endregion Priority Items


    // region Checklist Items
    val checklistItemEntitiesLiveData = MutableLiveData<List<ChecklistItemEntity>>()

    private val _currentSelectedChecklistItemLiveData = MutableLiveData<ChecklistItemEntity>()
    val currentSelectedChecklistItemLiveData: LiveData<ChecklistItemEntity>
        get() = _currentSelectedChecklistItemLiveData

    fun updateCurrentChecklistItemSelected(currentChecklistItem: ChecklistItemEntity) {
        _currentSelectedChecklistItemLiveData.value = currentChecklistItem
        _currentSelectedChecklistItemLiveData.postValue(currentChecklistItem)
    }

    fun collectAllChecklistItems() {
        // checklist items
        viewModelScope.launch {
            repository.getAllChecklistItems().collect() { allChecklistEntities ->
                checklistItemEntitiesLiveData.postValue(allChecklistEntities)
            }
        }
        Log.d("ViewModel", "collectAllItems: getAllChecklistItems" )
    }

    fun getAllChecklistItemsByFolderName(folderName: String) {
        // checklist items
        viewModelScope.launch {
            repository.getAllChecklistItems().collect() { allChecklistItems ->
                val items = allChecklistItems.filter {
                    it.folderName == folderName
                }
                checklistItemEntitiesLiveData.postValue(items)
            }
        }
    }

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


    // region Note Items
    val noteEntitiesLiveData = MutableLiveData<List<NoteEntity>>()
    val noteEntitiesByFolderNameLiveData = MutableLiveData<List<NoteEntity>>()

    private val _currentNoteSelectedLiveData = MutableLiveData<NoteEntity>()
    val currentNoteSelectedLiveData: LiveData<NoteEntity>
        get() = _currentNoteSelectedLiveData


    fun collectAllNotes() {
        viewModelScope.launch {
            repository.getAllNoteEntities().collect() { list ->
                noteEntitiesLiveData.postValue(list)
            }
        }
        Log.d("ViewModel", "collectAllItems: getAllNoteItems" )

    }

    fun updateCurrentNoteSelected(currentNote: NoteEntity) {
        _currentNoteSelectedLiveData.value = currentNote
        _currentNoteSelectedLiveData.postValue(currentNote)
    }

    fun getAllNotesByFolder(folderName: String) {
        viewModelScope.launch {
            repository.getAllNoteEntities().collect() { allNotes ->
                val notes = allNotes.filter {
                    it.folderName == folderName
                }
                noteEntitiesByFolderNameLiveData.postValue(notes)
            }
        }
    }

    fun insertNoteEntity(noteEntity: NoteEntity) {
        viewModelScope.launch{
            repository.insertNoteEntity(noteEntity)
        }
    }

    fun updateNoteEntity(noteEntity: NoteEntity) {
        viewModelScope.launch{
            repository.updateNoteEntity(noteEntity)
        }
    }

    fun deleteNoteEntity(noteEntity: NoteEntity) {
        viewModelScope.launch{
            repository.deleteNoteEntity(noteEntity)
        }
    }
    // endregion Note Items


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