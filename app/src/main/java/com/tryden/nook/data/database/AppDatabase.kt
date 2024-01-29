package com.tryden.nook.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tryden.nook.data.database.dao.ChecklistItemEntityDao
import com.tryden.nook.data.database.dao.FolderEntityDao
import com.tryden.nook.data.database.dao.NoteEntityDao
import com.tryden.nook.data.database.dao.PriorityItemEntityDao
import com.tryden.nook.data.database.entity.ChecklistItemEntity
import com.tryden.nook.data.database.entity.FolderEntity
import com.tryden.nook.data.database.entity.NoteEntity
import com.tryden.nook.data.database.entity.PriorityItemEntity


@Database(
    entities =
    [
        PriorityItemEntity::class, ChecklistItemEntity::class,
        FolderEntity::class, NoteEntity::class
    ],
    version = 5
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun folderItemEntityDao(): FolderEntityDao
    abstract fun priorityItemEntityDao(): PriorityItemEntityDao
    abstract fun checklistItemEntityDao(): ChecklistItemEntityDao
    abstract fun noteItemEntityDao(): NoteEntityDao

}