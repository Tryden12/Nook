package com.tryden.nook.arch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tryden.nook.database.AppDatabase
import com.tryden.nook.database.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

class NookViewModel() : ViewModel() {

    private lateinit var repository: NookRepository

    fun init (appDatabase: AppDatabase) {
        repository = NookRepository(appDatabase)
    }

    val itemEntitiesLiveData = MutableLiveData<MutableLiveData<List<ItemEntity>>>()

    fun insertItem(itemEntity: ItemEntity) {
        repository.insertItem(itemEntity)
    }

    fun deleteItem(itemEntity: ItemEntity) {
        repository.deleteItem(itemEntity)
    }
}