package com.tryden.nook.ui.home.bottomsheet

import com.tryden.nook.database.entity.ChecklistItemEntity
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.database.entity.NoteEntity
import com.tryden.nook.database.entity.PriorityItemEntity
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface

/**
 * This interface provides the methods for saving an entity (item) via the AddItemSheet() bottom
 * sheet.
 */

interface OnAddItemSheetButtonSelected {

    fun onCancel()

    // Either a priority, checklist, or note item
    fun onUpdateItem(item: EpoxyItemsInterface)

    // Save or update item based on type
    fun onSaveFolder(item: FolderEntity)

    fun onSaveChecklistItemEntity(item: ChecklistItemEntity)
    fun onSavePriorityItem(item: PriorityItemEntity, editMode: Boolean)
    fun onSaveNoteItemEntity(item: NoteEntity)


}