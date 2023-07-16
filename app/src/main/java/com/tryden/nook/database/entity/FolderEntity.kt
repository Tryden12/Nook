package com.tryden.nook.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folder_entity")
data class FolderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String = "",
    val type: String = "",
    val size: Int = 0
)