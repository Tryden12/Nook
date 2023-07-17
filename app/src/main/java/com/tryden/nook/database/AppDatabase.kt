package com.tryden.nook.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tryden.nook.database.dao.ChecklistItemEntityDao
import com.tryden.nook.database.dao.FolderEntityDao
import com.tryden.nook.database.dao.NoteEntityDao
import com.tryden.nook.database.dao.PriorityItemEntityDao
import com.tryden.nook.database.entity.ChecklistItemEntity
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.database.entity.NoteEntity
import com.tryden.nook.database.entity.PriorityItemEntity


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