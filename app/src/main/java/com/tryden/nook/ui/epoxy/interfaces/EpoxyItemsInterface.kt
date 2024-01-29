package com.tryden.nook.ui.epoxy.interfaces

import com.tryden.nook.data.database.entity.ChecklistItemEntity
import com.tryden.nook.data.database.entity.FolderEntity
import com.tryden.nook.data.database.entity.NoteEntity
import com.tryden.nook.data.database.entity.PriorityItemEntity

interface EpoxyItemsInterface {

    // Title
    data class HeaderSectionTitle(val title: String): EpoxyItemsInterface

    // Rounded Section Header
    object SectionHeaderRounded: EpoxyItemsInterface

    // Items
    data class FolderItem(val item: FolderEntity): EpoxyItemsInterface
    data class PriorityItem(val item: PriorityItemEntity): EpoxyItemsInterface
    data class ChecklistItem(val item: ChecklistItemEntity): EpoxyItemsInterface
    data class NoteItem(val item: NoteEntity): EpoxyItemsInterface

    // Divider
    object DividerItem: EpoxyItemsInterface
    object DividerFolder: EpoxyItemsInterface


    // Rounded Section Footer
    object SectionFooterRounded: EpoxyItemsInterface

}