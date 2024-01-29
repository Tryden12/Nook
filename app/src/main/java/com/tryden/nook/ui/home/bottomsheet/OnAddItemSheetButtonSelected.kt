package com.tryden.nook.ui.home.bottomsheet

import com.tryden.nook.data.database.entity.ChecklistItemEntity
import com.tryden.nook.data.database.entity.FolderEntity
import com.tryden.nook.data.database.entity.NoteEntity
import com.tryden.nook.data.database.entity.PriorityItemEntity
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface

/**
 * This interface provides the methods for saving an entity (item) via the AddItemSheet() bottom
 * sheet.
 */

interface OnAddItemSheetButtonSelected {

    fun onCancel()

    /**
     * @param [item] can be one of the following items:
     * Folder, Priority, Checklist, or Note
     *
     * At Fragment level, these methods will determine the type of item using a when statement.
     */
    fun onInsertItem(item: EpoxyItemsInterface)
    fun onUpdateItem(item: EpoxyItemsInterface)


}