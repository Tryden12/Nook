package com.tryden.nook.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tryden.nook.data.source.local.dao.ChecklistItemDao
import com.tryden.nook.data.source.local.dao.FolderItemDao
import com.tryden.nook.data.source.local.dao.NoteItemDao
import com.tryden.nook.data.source.local.dao.PriorityItemDao
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

    abstract fun folderItemEntityDao(): FolderItemDao
    abstract fun priorityItemEntityDao(): PriorityItemDao
    abstract fun checklistItemEntityDao(): ChecklistItemDao
    abstract fun noteItemEntityDao(): NoteItemDao

}