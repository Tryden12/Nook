package com.tryden.nook.ui.epoxy.interfaces

import com.tryden.nook.database.entity.PriorityItemEntity

interface PriorityItemEntityEpoxyItem {

    data class HeaderSectionTitle(val title: String): PriorityItemEntityEpoxyItem
    object SectionHeaderRounded: PriorityItemEntityEpoxyItem
    data class Item(val item: PriorityItemEntity): PriorityItemEntityEpoxyItem
    object DividerItem: PriorityItemEntityEpoxyItem
    object SectionFooterRounded: PriorityItemEntityEpoxyItem

}