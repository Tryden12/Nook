package com.tryden.nook.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "priority_item_entity")
data class PriorityItemEntity(
    @PrimaryKey val id: String = "",
    val title: String = "",
    val description: String? = null,
    val lastModified: Long = 0L,
    val categoryId: String = "",
    val priority: Int = 0
)