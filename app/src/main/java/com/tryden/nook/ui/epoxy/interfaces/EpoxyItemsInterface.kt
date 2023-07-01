package com.tryden.nook.ui.epoxy.interfaces

import com.tryden.nook.database.entity.ChecklistItemEntity
import com.tryden.nook.database.entity.PriorityItemEntity

interface EpoxyItemsInterface {

    // Title
    data class HeaderSectionTitle(val title: String): EpoxyItemsInterface

    // Rounded Section Header
    object SectionHeaderRounded: EpoxyItemsInterface

    // Items
    data class PriorityItem(val item: PriorityItemEntity): EpoxyItemsInterface
    data class ChecklistItem(val item: ChecklistItemEntity): EpoxyItemsInterface

    // Divider
    object DividerItem: EpoxyItemsInterface

    // Rounded Section Footer
    object SectionFooterRounded: EpoxyItemsInterface

}