package com.tryden.nook.arch

import com.tryden.nook.database.AppDatabase
import com.tryden.nook.database.entity.PriorityItemEntity
import kotlinx.coroutines.flow.Flow

class NookRepository(
    private val appDatabase: AppDatabase
){

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

}