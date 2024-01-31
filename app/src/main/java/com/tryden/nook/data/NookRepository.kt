package com.tryden.nook.data

import com.tryden.nook.data.source.local.dao.ChecklistItemDao
import com.tryden.nook.data.source.local.dao.FolderItemDao
import com.tryden.nook.data.source.local.dao.NoteItemDao
import com.tryden.nook.data.source.local.dao.PriorityItemDao
import com.tryden.nook.data.source.local.entity.ChecklistItemEntity
import com.tryden.nook.data.source.local.entity.FolderEntity
import com.tryden.nook.data.source.local.entity.NoteEntity
import com.tryden.nook.data.source.local.entity.PriorityItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NookRepository @Inject constructor(
    private val priorityDao: PriorityItemDao,
    private val checklistDao: ChecklistItemDao,
    private val folderDao: FolderItemDao,
    private val noteDao: NoteItemDao
){

    // region Folder Items
    fun getAllFolders(): Flow<List<FolderEntity>> {
        return folderDao.getAllFolderEntities()
    }

    suspend fun insertFolder(folderEntity: FolderEntity) {
        folderDao.insert(folderEntity)
    }

    suspend fun deleteFolder(folderEntity: FolderEntity) {
        folderDao.delete(folderEntity)
    }

    suspend fun updateFolder(folderEntity: FolderEntity) {
        folderDao.update(folderEntity)
    }
    // endregion Folder Items

    // region Priority Items
    fun getAllPriorityItems(): Flow<List<PriorityItemEntity>> {
        return priorityDao.getAllPriorityItemEntities()
    }

    suspend fun insertPriorityItem(priorityItemEntity: PriorityItemEntity) {
        priorityDao.insert(priorityItemEntity)
    }

    suspend fun deletePriorityItem(priorityItemEntity: PriorityItemEntity) {
        priorityDao.delete(priorityItemEntity)
    }

    suspend fun updatePriorityItem(priorityItemEntity: PriorityItemEntity) {
        priorityDao.update(priorityItemEntity)
    }
    // endregion Priority Items


    // region Checklist Items
    fun getAllChecklistItems(): Flow<List<ChecklistItemEntity>> {
        return checklistDao.getAllChecklistItemEntities()
    }

    suspend fun insertChecklistItem(checklistItemEntity: ChecklistItemEntity) {
        checklistDao.insert(checklistItemEntity)
    }

    suspend fun deleteChecklistItem(checklistItemEntity: ChecklistItemEntity) {
        checklistDao.delete(checklistItemEntity)
    }

    suspend fun updateChecklistItem(checklistItemEntity: ChecklistItemEntity) {
        checklistDao.update(checklistItemEntity)
    }
    // endregion Checklist Items

    // region Note Items
    fun getAllNoteEntities(): Flow<List<NoteEntity>> {
        return noteDao.getAllNoteEntities()
    }

    suspend fun insertNoteEntity(noteEntity: NoteEntity) {
        noteDao.insert(noteEntity)
    }

    suspend fun updateNoteEntity(noteEntity: NoteEntity) {
        noteDao.update(noteEntity)
    }

    suspend fun deleteNoteEntity(noteEntity: NoteEntity) {
        noteDao.delete(noteEntity)
    }

    // endregion Note Items


}