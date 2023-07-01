package com.tryden.nook.arch

import com.tryden.nook.database.AppDatabase
import com.tryden.nook.database.entity.ChecklistItemEntity
import com.tryden.nook.database.entity.PriorityItemEntity
import kotlinx.coroutines.flow.Flow

class NookRepository(
    private val appDatabase: AppDatabase
){

    // region Priority Items
    fun getAllPriorityItems(): Flow<List<PriorityItemEntity>> {
        return appDatabase.priorityItemEntityDao().getAllPriorityItemEntities()
    }

    suspend fun insertPriorityItem(priorityItemEntity: PriorityItemEntity) {
        appDatabase.priorityItemEntityDao().insert(priorityItemEntity)
    }

    suspend fun deletePriorityItem(priorityItemEntity: PriorityItemEntity) {
        appDatabase.priorityItemEntityDao().delete(priorityItemEntity)
    }

    suspend fun updatePriorityItem(priorityItemEntity: PriorityItemEntity) {
        appDatabase.priorityItemEntityDao().update(priorityItemEntity)
    }
    // endregion Priority Items


    // region Checklist Items
    fun getAllChecklistItems(): Flow<List<ChecklistItemEntity>> {
        return appDatabase.checklistItemEntityDao().getAllChecklistItemEntities()
    }

    suspend fun insertChecklistItem(checklistItemEntity: ChecklistItemEntity) {
        appDatabase.checklistItemEntityDao().insert(checklistItemEntity)
    }

    suspend fun deleteChecklistItem(checklistItemEntity: ChecklistItemEntity) {
        appDatabase.checklistItemEntityDao().delete(checklistItemEntity)
    }

    suspend fun updateChecklistItem(checklistItemEntity: ChecklistItemEntity) {
        appDatabase.checklistItemEntityDao().update(checklistItemEntity)
    }
    // endregion Checklist Items

}