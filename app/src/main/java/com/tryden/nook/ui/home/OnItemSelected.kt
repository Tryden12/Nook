package com.tryden.nook.ui.home

import com.tryden.nook.database.entity.FolderEntity
import com.tryden.nook.database.entity.PriorityItemEntity
import com.tryden.nook.ui.epoxy.interfaces.EpoxyItemsInterface

interface OnItemSelected {

    // Folder selected
//    fun onFolderSelected(selectedFolder: FolderEntity)

    /**
     * Item could be one of the following:
     * Folder, Priority, Checklist, or Note
     */
    fun onItemSelected(itemType: EpoxyItemsInterface)

    /**
     * Bump priority when "!" on priority item is tapped.
     */
    fun onBumpPriority(priorityItemEntity: PriorityItemEntity)

}