package com.tryden.nook.arch

import com.tryden.nook.database.dao.ChecklistItemEntityDao
import com.tryden.nook.database.dao.FolderEntityDao
import com.tryden.nook.database.dao.PriorityItemEntityDao
import com.tryden.nook.database.entity.ChecklistItemEntity
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.database.entity.PriorityItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NookRepository @Inject constructor(
    private val priorityDao: PriorityItemEntityDao,
    private val checklistDao: ChecklistItemEntityDao,
    private val folderDao: FolderEntityDao
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

}