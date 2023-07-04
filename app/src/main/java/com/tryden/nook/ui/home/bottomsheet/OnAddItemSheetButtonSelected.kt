package com.tryden.nook.ui.home.bottomsheet

import com.tryden.nook.database.entity.ChecklistItemEntity
import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.database.entity.PriorityItemEntity

interface OnAddItemSheetButtonSelected {

    fun onCancel()

    // Save item based on type
    fun onSaveFolder(item: FolderEntity)
    fun onSaveChecklistItem(item: ChecklistItemEntity)
    fun onSavePriorityItem(item: PriorityItemEntity)

}