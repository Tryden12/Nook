package com.tryden.nook.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_entity")
data class ItemEntity(
    @PrimaryKey val id: String = "",
    val title: String = "",
    val description: String? = null,
    val createdAt: Long = 0L,
    val categoryId: String = "",
//    val priority: Int = 0
)