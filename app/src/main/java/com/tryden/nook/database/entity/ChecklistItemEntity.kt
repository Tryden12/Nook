package com.tryden.nook.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checklist_item_entity")
data class ChecklistItemEntity(
    @PrimaryKey val id: String = "",
    val title: String = "",
    val categoryId: String = "",
    val isChecked: Boolean = false
)