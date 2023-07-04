package com.tryden.nook.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checklist_item_entity")
data class ChecklistItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String = "",
    val categoryId: String = "",
    val folderName: String = "",
    val important: Boolean = false,
    val completed: Boolean = false
)