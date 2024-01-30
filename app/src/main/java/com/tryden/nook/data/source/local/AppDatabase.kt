package com.tryden.nook.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tryden.nook.data.source.local.dao.ChecklistItemEntityDao
import com.tryden.nook.data.source.local.dao.FolderEntityDao
import com.tryden.nook.data.source.local.dao.NoteEntityDao
import com.tryden.nook.data.source.local.dao.PriorityItemEntityDao
import com.tryden.nook.data.source.local.entity.ChecklistItemEntity
import com.tryden.nook.data.source.local.entity.FolderEntity
import com.tryden.nook.data.source.local.entity.NoteEntity
import com.tryden.nook.data.source.local.entity.PriorityItemEntity


/**
 * The Room Database that contains the tables:
 * Folder, Priority, Checklist, Note
 *
 * Note: exportSchema should be true in production databases.
 */
@Database(
    entities =
    [
        PriorityItemEntity::class, ChecklistItemEntity::class,
        FolderEntity::class, NoteEntity::class
    ],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun folderItemEntityDao(): FolderEntityDao
    abstract fun priorityItemEntityDao(): PriorityItemEntityDao
    abstract fun checklistItemEntityDao(): ChecklistItemEntityDao
    abstract fun noteItemEntityDao(): NoteEntityDao

}