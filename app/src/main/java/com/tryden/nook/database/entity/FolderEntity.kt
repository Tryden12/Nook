package com.tryden.nook.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folder_entity")
data class FolderEntity(
    @PrimaryKey val id: String = "",
    val title: String = "",
    val type: String = "",
    val size: String = ""
)